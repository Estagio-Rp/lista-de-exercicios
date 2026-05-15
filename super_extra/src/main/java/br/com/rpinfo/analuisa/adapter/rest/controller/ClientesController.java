package br.com.rpinfo.analuisa.adapter.rest.controller;

import br.com.rpinfo.analuisa.application.dto.clientes.ClientesDTO;
import br.com.rpinfo.analuisa.application.dto.enderecos.EnderecosDTO;
import br.com.rpinfo.analuisa.application.usecase.ClientesUseCase;
import br.com.rpinfo.analuisa.application.usecase.EnderecosUseCase;
import br.com.rpinfo.analuisa.shared.LeitorConsole;

import java.util.List;
import java.util.Scanner;

public class ClientesController {

    private final LeitorConsole leitor;

    public ClientesController() {
        Scanner scanner = new Scanner(System.in);
        this.leitor = new LeitorConsole(scanner);
    }

    public void menuClientes() {
        int opcao;

        do {
            System.out.println("\n=== CLIENTES ===");
            System.out.println("1. Adicionar Cliente");
            System.out.println("2. Listar Clientes");
            System.out.println("3. Editar Cliente");
            System.out.println("4. Excluir Cliente");
            System.out.println("0. Voltar ao Menu Principal");

            opcao = leitor.lerInteiro("Escolha uma opcao: ");

            switch (opcao) {
                case 1:
                    adicionarCliente();
                    break;
                case 2:
                    listarClientes();
                    break;
                case 3:
                    editarCliente();
                    break;
                case 4:
                    excluirCliente();
                    break;

                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;

                default:
                    System.out.println("Opcao invalida! Tente novamente.");
                    break;
            }
        } while (opcao != 0);
    }

    private void adicionarCliente() {
        try {
            if (!listarEnderecosDisponiveis()) {
                System.out.println("Cadastrar um endereço antes de cadastrar cliente.");
                return;
            }

            ClientesDTO cliente = lerDadosCliente(null);

            ClientesUseCase.inserirCliente(cliente);

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean listarClientes() {
        try {
            List<ClientesDTO> clientes = ClientesUseCase.listarClientes();

            System.out.println("\n--- LISTA DE CLIENTES ---");

            if (clientes.isEmpty()) {
                System.out.println("Nenhum cliente cadastrado.");
                return false;
            }

            for (ClientesDTO cliente : clientes) {
                System.out.println("\nID: " + cliente.getId());
                System.out.println("Nome: " + cliente.getNome());
                System.out.println("Email: " + cliente.getEmail());
                System.out.println("CPF: " + cliente.getCpf());
                System.out.println("Telefone: " + cliente.getTelefone());
                System.out.println("Data de cadastro: " + cliente.getDataCadastro());
                System.out.println("ID do endereço: " + cliente.getEnderecoId());
            }
            return true;

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private void editarCliente() {
        try {
            if (!listarClientes()) {
                return;
            }

            Integer id = leitor.lerInteiroMinimo("\nID do cliente que deseja editar: ", 1);

            if (!listarEnderecosDisponiveis()) {
                System.out.println("Cadastre um endereço antes de editar cliente.");
                return;
            }

            ClientesDTO cliente = lerDadosCliente(id);

            ClientesUseCase.atualizarCliente(id, cliente);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private void excluirCliente() {
        try {
            if (!listarClientes()) {
                return;
            }

            Integer id = leitor.lerInteiroMinimo("\nID do cliente que deseja excluir: ", 1);

            ClientesUseCase.deletarCliente(id);

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private ClientesDTO lerDadosCliente(Integer id) {
        String nome = leitor.lerTextoNaoNumerico(
                "Nome do cliente: ",
                "Erro: o nome do cliente não pode ser apenas numérico."
        );

        String email = leitor.lerTextoComPadrao(
                "Email: ",
                "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$",
                "Erro: digite um email válido. Exemplo: nome@email.com."
        ).toLowerCase();

        String cpf = leitor.lerTextoComPadrao(
                "CPF: ",
                "\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}",
                "Erro: o CPF deve ter 11 números. Exemplo: 12345678909."
        ).replaceAll("\\D", "");

        String telefone = leitor.lerTextoComPadrao(
                "Telefone: ",
                "\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}",
                "Erro: telefone inválido. Use DDD + número. Exemplo: 46999998888."
        ).replaceAll("\\D", "");

        Integer enderecoId = lerEnderecoIdValido();

        return new ClientesDTO(
                id,
                nome,
                email,
                cpf,
                telefone,
                null,
                enderecoId
        );
    }

    private boolean listarEnderecosDisponiveis() {
        try {
            List<EnderecosDTO> enderecos = EnderecosUseCase.listarEnderecos();

            System.out.println("\n--- ENDEREÇOS DISPONÍVEIS ---");

            if (enderecos.isEmpty()) {
                System.out.println("Nenhum endereço cadastrado.");
                return false;
            }

            for (EnderecosDTO endereco : enderecos) {
                System.out.println("\nID: " + endereco.getId());
                System.out.println("CEP: " + endereco.getCep());
                System.out.println("Rua: " + endereco.getRua());
                System.out.println("Número: " + endereco.getNumero());
                System.out.println("Bairro: " + endereco.getBairro());
                System.out.println("ID da cidade: " + endereco.getCidadeId());
            }
            return true;

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private Integer lerEnderecoIdValido() {
        while (true) {
            Integer enderecoId = leitor.lerInteiroMinimo("ID do endereço: ", 1);

            if (enderecoExiste(enderecoId)) {
                return enderecoId;
            }

            System.out.println("Erro: o endereço informado não existe. Digite um ID de endereço válido.");
        }
    }

    private boolean enderecoExiste(Integer id) {
        List<EnderecosDTO> enderecos = EnderecosUseCase.listarEnderecos();

        for (EnderecosDTO endereco : enderecos) {
            if (endereco.getId().equals(id)) {
                return true;
            }
        }

        return false;
    }
}

