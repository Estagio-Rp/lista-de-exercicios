package br.com.rpinfo.analuisa.adapter.rest.controller;

import br.com.rpinfo.analuisa.application.dto.cidades.CidadesDTO;
import br.com.rpinfo.analuisa.application.dto.enderecos.EnderecosDTO;
import br.com.rpinfo.analuisa.application.usecase.CidadesUseCase;
import br.com.rpinfo.analuisa.application.usecase.EnderecosUseCase;
import br.com.rpinfo.analuisa.shared.LeitorConsole;

import java.util.List;
import java.util.Scanner;

public class EnderecosController {

    private final LeitorConsole leitor;

    public EnderecosController() {
        Scanner scanner = new Scanner(System.in);
        this.leitor = new LeitorConsole(scanner);
    }

    public void menuEnderecos() {
        int opcao;

        do {
            System.out.println("\n=== ENDEREÇOS ===");
            System.out.println("1. Adicionar Endereço");
            System.out.println("2. Listar Endereços");
            System.out.println("3. Editar Endereços");
            System.out.println("4. Excluir Endereços");
            System.out.println("0. Voltar ao Menu Principal");

            opcao = leitor.lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    adicionarEndereco();
                    break;
                case 2:
                    listarEnderecos();
                    break;
                case 3:
                    editarEndereco();
                    break;
                case 4:
                    excluirEndereco();
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void adicionarEndereco() {
        try {
            if (!listarCidadesDisponiveis()) {
                System.out.println("Cadasre uma cidade antes de cadastrar endereço: ");
                return;
            }

            EnderecosDTO endereco = lerDadosEndereco(null);

            EnderecosUseCase.inserirEnderecos(endereco);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean listarEnderecos() {
        try {
            List<EnderecosDTO> enderecos = EnderecosUseCase.listarEnderecos();

            System.out.println("\n--- LISTA DE ENDEREÇOS ---");

            if (enderecos.isEmpty()) {
                System.out.println("Nenhum endereço cadastrado.");
                return false;
            }

            for (EnderecosDTO endereco : enderecos) {
                System.out.println("\nID: " + endereco.getId());
                System.out.println("CEP: " + endereco.getCep());
                System.out.println("Rua: " + endereco.getRua());
                System.out.println("Número: " + endereco.getNumero());
                System.out.println("Complemento: " + endereco.getComplemento());
                System.out.println("Bairro: " + endereco.getBairro());
                System.out.println("ID da cidade: " + endereco.getCidadeId());
            }

            return true;

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private void editarEndereco() {
        try {
            if (!listarEnderecos()) {
                return;
            }

            Integer id = leitor.lerInteiroMinimo("Digite o ID do endereço que deseja editar: ", 1);

            if (!listarCidadesDisponiveis()) {
                System.out.println("Cadastre uma cidade antes de editar endereço.");
                return;
            }

            EnderecosDTO endereco = lerDadosEndereco(id);

            EnderecosUseCase.atualizarEnderecos(id, endereco);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private void excluirEndereco() {
        try {
            if (!listarEnderecos()) {
                return;
            }
            System.out.println("\nAtenção: clientes vinculados a esse endereço ficarão sem endereço.");

            Integer id = leitor.lerInteiroMinimo("ID do endereço que deseja excluir: ", 1);

            EnderecosUseCase.deletarEnderecos(id);

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private EnderecosDTO lerDadosEndereco(Integer id) {
        String cep = leitor.lerTextoComPadrao(
                "CEP: ",
                "\\d{8}|\\d{5}-\\d{3}",
                "Erro: o CEP deve ter 8 números. Exemplo: 85501266 ou 85501-266."
        ).replaceAll("\\D", "");

        String rua = leitor.lerTextoNaoNumerico(
                "Rua: ",
                "Erro: a rua não pode ser apenas numérica."
        );

        Integer numero = leitor.lerInteiroMinimo("Número: ", 1);

        String complemento = leitor.lerTextoOpcionalNaoNumerico(
                "Complemento: ",
                "Erro: o complemento não pode ser apenas numérico."
        );

        String bairro = leitor.lerTextoNaoNumerico(
                "Bairro: ",
                "Erro: o bairro não pode ser apenas numérico."
        );

        Integer cidadeId = leitor.lerInteiroMinimo("ID da cidade: ", 1);

        return new EnderecosDTO(
                id,
                cep,
                rua,
                numero,
                complemento,
                bairro,
                cidadeId
        );
    }

    private boolean listarCidadesDisponiveis() {
        try {
            List<CidadesDTO> cidades = CidadesUseCase.listarCidades();

            System.out.println("\n--- CIDADES DISPONIVEIS ---");

            if (cidades.isEmpty()) {
                System.out.println("Nenhuma cidade cadastrada.");
                return false;
            }

            for (CidadesDTO cidade : cidades) {
                System.out.println("ID: " + cidade.getId());
                System.out.println("Nome: " + cidade.getNome());
                System.out.println("UF: " + cidade.getUf());
            }
            return true;
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}

