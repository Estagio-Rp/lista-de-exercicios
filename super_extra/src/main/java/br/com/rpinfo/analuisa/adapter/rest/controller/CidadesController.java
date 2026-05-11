package br.com.rpinfo.analuisa.adapter.rest.controller;

import br.com.rpinfo.analuisa.application.dto.cidades.CidadesDTO;
import br.com.rpinfo.analuisa.application.usecase.CidadesUseCase;
import br.com.rpinfo.analuisa.shared.LeitorConsole;

import java.util.List;

public class CidadesController {

    private final CidadesUseCase cidadesUseCase;
    private final LeitorConsole leitor;

    public CidadesController(CidadesUseCase cidadesUseCase, LeitorConsole leitor) {
        this.cidadesUseCase = cidadesUseCase;
        this.leitor = leitor;
    }

    public void menu() {
        int opcao;

        do {
            System.out.println("\n=== CIDADES ===");
            System.out.println("1. Adicionar Cidade");
            System.out.println("2. Listar Cidades");
            System.out.println("3. Editar Cidade");
            System.out.println("4. Excluir Cidade");
            System.out.println("0. Voltar ao Menu Principal");

            opcao = leitor.lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    adicionar();
                    break;
                case 2:
                    listar();
                    break;
                case 3:
                    editar();
                    break;
                case 4:
                    excluir();
                    break;

                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;

                default:
                    System.out.println("Opção inválida!");
                    break;
            }

        } while (opcao != 0);
    }

    public boolean listar() {
        try {
            List<CidadesDTO> cidades = cidadesUseCase.listarTodos();
            System.out.println("\n--- LISTA DE CIDADES ---");

            if (cidades.isEmpty()) {
                System.out.println("Nenhum cidade cadastrada!");
                return false;
            }

            for (CidadesDTO cidade : cidades) {
                System.out.println("\nID: " + cidade.getId());
                System.out.println("Nome: " + cidade.getNome());
                System.out.println("UF: " + cidade.getUf());
            }
            return true;
        } catch (RuntimeException e) {
            System.out.println("Erro ao listar os cidades!" + e.getMessage());
            return false;
        }
    }

    private void adicionar() {
        try {
            System.out.println("\n--- CADASTRO DE CIDADE ---");

            CidadesDTO cidade = lerDadosCidade(null);

            cidadesUseCase.cadastrar(cidade);

            System.out.println("\nCidade cadastrada com sucesso!");

        } catch (RuntimeException e) {
            System.out.println("\nErro ao cadastrar cidade: " + e.getMessage());
        }
    }

    private void editar() {
        try {
            if (!listar()) {
                return;
            }

            int id = leitor.lerInteiroMinimo("\nID da cidade que deseja editar: ", 1);

            CidadesDTO cidade = lerDadosCidade(id);

            cidadesUseCase.atualizar(id, cidade);

            System.out.println("\nCidade atualizada com sucesso!");

        } catch (RuntimeException e) {
            System.out.println("\nErro ao editar cidade: " + e.getMessage());
        }
    }

    private void excluir() {
        try {
            if (!listar()) {
                return;
            }

            System.out.println("\nAtenção: endereços vinculados a essa cidade ficarão sem cidade.");

            int id = leitor.lerInteiroMinimo("ID da cidade que deseja excluir: ", 1);

            cidadesUseCase.deletar(id);

            System.out.println("\nCidade excluída com sucesso!");

        } catch (RuntimeException e) {
            System.out.println("\nErro ao excluir cidade: " + e.getMessage());
        }
    }

    private CidadesDTO lerDadosCidade(Integer id) {
        String nome = leitor.lerTextoObrigatorio("Nome da cidade: ");

        String uf = leitor.lerTextoComPadrao(
                "UF: ",
                "[A-Za-z]{2}",
                "Erro: a UF deve ter exatamente 2 letras. Exemplo: PR."
        ).toUpperCase();

        return new CidadesDTO(id, nome, uf);
    }
}



