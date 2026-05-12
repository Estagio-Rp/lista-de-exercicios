package br.com.rpinfo.analuisa.adapter.rest.controller;

import br.com.rpinfo.analuisa.application.dto.cidades.CidadesDTO;
import br.com.rpinfo.analuisa.application.usecase.CidadesUseCase;
import br.com.rpinfo.analuisa.shared.LeitorConsole;

import java.util.List;
import java.util.Scanner;

public class CidadesController {

    private final LeitorConsole leitor;

    public CidadesController() {
        Scanner scanner = new Scanner(System.in);
        this.leitor = new LeitorConsole(scanner);
    }

    public void menuCidades() {
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
                    adicionarCidade();
                    break;

                case 2:
                    listarCidades();
                    break;

                case 3:
                    editarCidade();
                    break;

                case 4:
                    excluirCidade();
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

    private void adicionarCidade() {
        try {
            CidadesDTO cidade = lerDadosCidade(null);

            CidadesUseCase.inserirCidade(cidade);

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean listarCidades() {
        try {
            List<CidadesDTO> cidades = CidadesUseCase.listarCidades();

            System.out.println("\n--- LISTA DE CIDADES ---");

            if (cidades.isEmpty()) {
                System.out.println("Nenhuma cidade cadastrada.");
                return false;
            }

            for (CidadesDTO cidade : cidades) {
                System.out.println("\nID: " + cidade.getId());
                System.out.println("Nome: " + cidade.getNome());
                System.out.println("UF: " + cidade.getUf());
            }

            return true;

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private void editarCidade() {
        try {
            if (!listarCidades()) {
                return;
            }

            Integer id = leitor.lerInteiroMinimo("\nID da cidade que deseja editar: ", 1);

            CidadesDTO cidade = lerDadosCidade(id);

            CidadesUseCase.atualizarCidade(id, cidade);

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private void excluirCidade() {
        try {
            if (!listarCidades()) {
                return;
            }

            System.out.println("\nAtenção: endereços vinculados a essa cidade ficarão sem cidade.");

            Integer id = leitor.lerInteiroMinimo("ID da cidade que deseja excluir: ", 1);

            CidadesUseCase.deletarCidade(id);

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
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