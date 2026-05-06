package br.com.rpinfo.analuisa;

import delete.DeleteCidades;
import delete.DeleteClientes;
import delete.DeleteEnderecos;
import delete.DeleteProdutos;
import insert.AddCidades;
import insert.AddClientes;
import insert.AddEnderecos;
import insert.AddProdutos;
import update.UpdateCidades;
import update.UpdateClientes;
import update.UpdateEnderecos;
import update.UpdateProdutos;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ConsultaProdutos consultaProdutos = new ConsultaProdutos();

        AddProdutos addProdutos = new AddProdutos();
        AddCidades addCidades = new AddCidades();
        AddClientes addClientes = new AddClientes();
        AddEnderecos addEnderecos = new AddEnderecos();

        UpdateProdutos updateProdutos = new UpdateProdutos();
        UpdateClientes updateClientes = new UpdateClientes();
        UpdateEnderecos updateEnderecos = new UpdateEnderecos();
        UpdateCidades updateCidades = new UpdateCidades();

        DeleteProdutos deleteProdutos = new DeleteProdutos();
        DeleteClientes deleteClientes = new DeleteClientes();
        DeleteEnderecos deleteEnderecos = new DeleteEnderecos();
        DeleteCidades deleteCidades = new DeleteCidades();

        int opcao;

        do {
            System.out.println("\n==========================");
            System.out.println("       MENU PRINCIPAL      ");
            System.out.println("==========================");
            System.out.println("1 - Consultar registros");
            System.out.println("2 - Cadastrar registros");
            System.out.println("3 - Atualizar registros");
            System.out.println("4 - Deletar registros");
            System.out.println("0 - Sair");

            opcao = lerInteiro(scanner, "\nEscolha uma opção: ");

            switch (opcao) {
                case 1:
                    menuConsultas(scanner, consultaProdutos);
                    break;

                case 2:
                    menuCadastros(scanner, consultaProdutos, addProdutos, addCidades, addClientes, addEnderecos);
                    break;

                case 3:
                    menuAtualizacoes(scanner, consultaProdutos, updateProdutos, updateClientes, updateEnderecos, updateCidades);
                    break;

                case 4:
                    menuDeletes(scanner, consultaProdutos, deleteProdutos, deleteClientes, deleteEnderecos, deleteCidades);
                    break;

                case 0:
                    System.out.println("\nEncerrando o sistema...");
                    break;

                default:
                    System.out.println("\nOpção inválida!");
                    break;
            }

        } while (opcao != 0);

        scanner.close();
    }

    public static void menuConsultas(Scanner scanner, ConsultaProdutos consultaProdutos) {
        int opcao;

        do {
            System.out.println("\n==============================");
            System.out.println("       Menu de Consultas       ");
            System.out.println("==============================");
            System.out.println("1  - Listar todos os produtos");
            System.out.println("2  - Listar nome e preço dos produtos");
            System.out.println("3  - Listar produtos de limpeza");
            System.out.println("4  - Listar produtos com estoque maior que 10");
            System.out.println("5  - Listar produtos com preço entre R$100 e R$1000");
            System.out.println("6  - Listar produtos com nome Gamer");
            System.out.println("7  - Listar 3 produtos mais baratos");
            System.out.println("8  - Contar produtos da categoria Armazenamento");
            System.out.println("9  - Média de preços por categoria");
            System.out.println("10 - Listar cidades");
            System.out.println("11 - Listar endereços");
            System.out.println("12 - Listar clientes");
            System.out.println("13 - Listar todos os registros");
            System.out.println("0  - Voltar");

            opcao = lerInteiro(scanner, "\nEscolha uma opção: ");

            switch (opcao) {
                case 1:
                    consultaProdutos.listarTodosProdutos();
                    break;

                case 2:
                    consultaProdutos.listarNomeEPreco();
                    break;

                case 3:
                    consultaProdutos.listarProdutosLimpeza();
                    break;

                case 4:
                    consultaProdutos.listarProdutosEstoqueMaiorQue10();
                    break;

                case 5:
                    consultaProdutos.listarProdutosPrecoEntre100E1000();
                    break;

                case 6:
                    consultaProdutos.listarProdutosComNomeGamer();
                    break;

                case 7:
                    consultaProdutos.listarTresProdutosMaisBaratos();
                    break;

                case 8:
                    consultaProdutos.contarProdutosArmazenamento();
                    break;

                case 9:
                    consultaProdutos.agruparCategoriaMediaPrecos();
                    break;

                case 10:
                    consultaProdutos.listarTodasCidades();
                    break;

                case 11:
                    consultaProdutos.listarTodosEnderecos();
                    break;

                case 12:
                    consultaProdutos.listarTodosClientes();
                    break;

                case 13:
                    consultaProdutos.listarTodosRegistros();
                    break;

                case 0:
                    System.out.println("\nVoltando ao menu principal...");
                    break;

                default:
                    System.out.println("\nOpção inválida!");
                    break;
            }

        } while (opcao != 0);
    }

    public static void menuCadastros(
            Scanner scanner,
            ConsultaProdutos consultaProdutos,
            AddProdutos addProdutos,
            AddCidades addCidades,
            AddClientes addClientes,
            AddEnderecos addEnderecos
    ) {
        int opcao;

        do {
            System.out.println("\n==============================");
            System.out.println("       Menu de Cadastros       ");
            System.out.println("==============================");
            System.out.println("1 - Cadastrar cidade");
            System.out.println("2 - Cadastrar endereço");
            System.out.println("3 - Cadastrar cliente");
            System.out.println("4 - Cadastrar produto");
            System.out.println("0 - Voltar");

            opcao = lerInteiro(scanner, "\nEscolha uma opção: ");

            switch (opcao) {
                case 1:
                    cadastrarCidade(scanner, addCidades);
                    break;

                case 2:
                    cadastrarEndereco(scanner, consultaProdutos, addEnderecos);
                    break;

                case 3:
                    cadastrarCliente(scanner, consultaProdutos, addClientes);
                    break;

                case 4:
                    cadastrarProduto(scanner, addProdutos);
                    break;

                case 0:
                    System.out.println("\nVoltando ao menu principal...");
                    break;

                default:
                    System.out.println("\nOpção inválida!");
                    break;
            }

        } while (opcao != 0);
    }

    public static void cadastrarCidade(Scanner scanner, AddCidades addCidades) {
        System.out.println("\n--- Cadastro de cidade ---");
        System.out.println("O ID será gerado automaticamente.");

        String nome = lerTextoObrigatorio(scanner, "Nome da cidade: ");

        String uf = lerTextoComPadrao(
                scanner,
                "UF: ",
                "[A-Za-z]{2}",
                "Erro: a UF deve ter exatamente 2 letras. Exemplo: PR."
        ).toUpperCase();

        addCidades.cadastrarCidade(nome, uf);
    }

    public static void cadastrarEndereco(Scanner scanner, ConsultaProdutos consultaProdutos, AddEnderecos addEnderecos) {
        System.out.println("\n--- Cadastro de endereço ---");

        if (!BancoUtils.temRegistros("cidades")) {
            System.out.println("\nAntes de cadastrar um endereço, cadastre uma cidade.");
            return;
        }

        System.out.println("\nCidades cadastradas:");
        consultaProdutos.listarTodasCidades();

        String cep = lerTextoComPadrao(
                scanner,
                "CEP: ",
                "\\d{8}|\\d{5}-\\d{3}",
                "Erro: o CEP deve ter 8 números. Exemplo: 85501266 ou 85501-266."
        ).replaceAll("\\D", "");

        String rua = lerTextoObrigatorio(scanner, "Rua: ");
        int numero = lerInteiroMinimo(scanner, "Número: ", 1);
        String complemento = lerTextoOpcional(scanner, "Complemento: ");
        String bairro = lerTextoObrigatorio(scanner, "Bairro: ");

        int cidaId = lerIdExistente(scanner, "ID da cidade: ", "cidades", "cidade");

        addEnderecos.cadastrarEndereco(cep, rua, numero, complemento, bairro, cidaId);
    }

    public static void cadastrarCliente(Scanner scanner, ConsultaProdutos consultaProdutos, AddClientes addClientes) {
        System.out.println("\n--- Cadastro de cliente ---");

        if (!BancoUtils.temRegistros("enderecos")) {
            System.out.println("\nAntes de cadastrar um cliente, cadastre um endereço.");
            return;
        }

        System.out.println("\nEndereços cadastrados:");
        consultaProdutos.listarTodosEnderecos();

        String nome = lerTextoObrigatorio(scanner, "Nome: ");

        String email = lerTextoComPadrao(
                scanner,
                "Email: ",
                "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$",
                "Erro: digite um email válido. Exemplo: nome@email.com."
        ).toLowerCase();

        String cpf = lerTextoComPadrao(
                scanner,
                "CPF: ",
                "\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}",
                "Erro: o CPF deve ter 11 números. Exemplo: 12345678909."
        ).replaceAll("\\D", "");

        String telefone = lerTextoComPadrao(
                scanner,
                "Telefone: ",
                "\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}",
                "Erro: telefone inválido. Use DDD + número."
        ).replaceAll("\\D", "");

        int endeId = lerIdExistente(scanner, "ID do endereço: ", "enderecos", "endereço");

        addClientes.cadastrarCliente(nome, email, cpf, telefone, endeId);
    }

    public static void cadastrarProduto(Scanner scanner, AddProdutos addProdutos) {
        System.out.println("\n--- Cadastro de produto ---");

        String nome = lerTextoObrigatorio(scanner, "Nome: ");
        double preco = lerDoubleMinimo(scanner, "Preço: ", 0);
        String categoria = lerTextoObrigatorio(scanner, "Categoria: ").toLowerCase();
        int estoque = lerInteiroMinimo(scanner, "Estoque: ", 0);

        addProdutos.cadastrarProduto(nome, preco, categoria, estoque);
    }

    public static void menuAtualizacoes(
            Scanner scanner,
            ConsultaProdutos consultaProdutos,
            UpdateProdutos updateProdutos,
            UpdateClientes updateClientes,
            UpdateEnderecos updateEnderecos,
            UpdateCidades updateCidades
    ) {
        int opcao;

        do {
            System.out.println("\n==============================");
            System.out.println("      Menu de Atualização      ");
            System.out.println("==============================");
            System.out.println("1  - Atualizar categoria do produto");
            System.out.println("2  - Atualizar estoque do produto");
            System.out.println("3  - Atualizar preço do produto");
            System.out.println("4  - Atualizar nome do cliente");
            System.out.println("5  - Atualizar email do cliente");
            System.out.println("6  - Atualizar telefone do cliente");
            System.out.println("7  - Atualizar endereço do cliente");
            System.out.println("8  - Atualizar rua do endereço");
            System.out.println("9  - Atualizar número do endereço");
            System.out.println("10 - Atualizar complemento do endereço");
            System.out.println("11 - Atualizar bairro do endereço");
            System.out.println("12 - Atualizar nome da cidade");
            System.out.println("13 - Atualizar UF da cidade");
            System.out.println("0  - Voltar");

            opcao = lerInteiro(scanner, "\nEscolha uma opção: ");

            switch (opcao) {
                case 1:
                    if (consultaProdutos.listarTodosProdutos()) {
                        int id = lerIdExistente(scanner, "ID do produto: ", "produtos", "produto");
                        String categoria = lerTextoObrigatorio(scanner, "Nova categoria: ").toLowerCase();

                        updateProdutos.atualizarCategoria(id, categoria);
                    }
                    break;

                case 2:
                    if (consultaProdutos.listarTodosProdutos()) {
                        int id = lerIdExistente(scanner, "ID do produto: ", "produtos", "produto");
                        int estoque = lerInteiroMinimo(scanner, "Novo estoque: ", 0);

                        updateProdutos.atualizarEstoque(id, estoque);
                    }
                    break;

                case 3:
                    if (consultaProdutos.listarTodosProdutos()) {
                        int id = lerIdExistente(scanner, "ID do produto: ", "produtos", "produto");
                        double preco = lerDoubleMinimo(scanner, "Novo preço: ", 0);

                        updateProdutos.atualizarPreco(id, preco);
                    }
                    break;

                case 4:
                    if (consultaProdutos.listarTodosClientes()) {
                        int id = lerIdExistente(scanner, "ID do cliente: ", "clientes", "cliente");
                        String nome = lerTextoObrigatorio(scanner, "Novo nome: ");

                        updateClientes.atualizarNome(id, nome);
                    }
                    break;

                case 5:
                    if (consultaProdutos.listarTodosClientes()) {
                        int id = lerIdExistente(scanner, "ID do cliente: ", "clientes", "cliente");

                        String email = lerTextoComPadrao(
                                scanner,
                                "Novo email: ",
                                "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$",
                                "Erro: digite um email válido."
                        ).toLowerCase();

                        updateClientes.atualizarEmail(id, email);
                    }
                    break;

                case 6:
                    if (consultaProdutos.listarTodosClientes()) {
                        int id = lerIdExistente(scanner, "ID do cliente: ", "clientes", "cliente");

                        String telefone = lerTextoComPadrao(
                                scanner,
                                "Novo telefone: ",
                                "\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}",
                                "Erro: telefone inválido."
                        ).replaceAll("\\D", "");

                        updateClientes.atualizarTelefone(id, telefone);
                    }
                    break;

                case 7:
                    if (consultaProdutos.listarTodosClientes() && consultaProdutos.listarTodosEnderecos()) {
                        int id = lerIdExistente(scanner, "ID do cliente: ", "clientes", "cliente");
                        int endeId = lerIdExistente(scanner, "Novo ID do endereço: ", "enderecos", "endereço");

                        updateClientes.atualizarEnderecoId(id, endeId);
                    }
                    break;

                case 8:
                    if (consultaProdutos.listarTodosEnderecos()) {
                        int id = lerIdExistente(scanner, "ID do endereço: ", "enderecos", "endereço");
                        String rua = lerTextoObrigatorio(scanner, "Nova rua: ");

                        updateEnderecos.atualizarRua(id, rua);
                    }
                    break;

                case 9:
                    if (consultaProdutos.listarTodosEnderecos()) {
                        int id = lerIdExistente(scanner, "ID do endereço: ", "enderecos", "endereço");
                        int numero = lerInteiroMinimo(scanner, "Novo número: ", 1);

                        updateEnderecos.atualizarNumero(id, numero);
                    }
                    break;

                case 10:
                    if (consultaProdutos.listarTodosEnderecos()) {
                        int id = lerIdExistente(scanner, "ID do endereço: ", "enderecos", "endereço");
                        String complemento = lerTextoOpcional(scanner, "Novo complemento: ");

                        updateEnderecos.atualizarComplemento(id, complemento);
                    }
                    break;

                case 11:
                    if (consultaProdutos.listarTodosEnderecos()) {
                        int id = lerIdExistente(scanner, "ID do endereço: ", "enderecos", "endereço");
                        String bairro = lerTextoObrigatorio(scanner, "Novo bairro: ");

                        updateEnderecos.atualizarBairro(id, bairro);
                    }
                    break;

                case 12:
                    if (consultaProdutos.listarTodasCidades()) {
                        int id = lerIdExistente(scanner, "ID da cidade: ", "cidades", "cidade");
                        String nome = lerTextoObrigatorio(scanner, "Novo nome da cidade: ");

                        updateCidades.atualizarNome(id, nome);
                    }
                    break;

                case 13:
                    if (consultaProdutos.listarTodasCidades()) {
                        int id = lerIdExistente(scanner, "ID da cidade: ", "cidades", "cidade");

                        String uf = lerTextoComPadrao(
                                scanner,
                                "Nova UF: ",
                                "[A-Za-z]{2}",
                                "Erro: a UF deve ter 2 letras."
                        ).toUpperCase();

                        updateCidades.atualizarUf(id, uf);
                    }
                    break;

                case 0:
                    System.out.println("\nVoltando ao menu principal...");
                    break;

                default:
                    System.out.println("\nOpção inválida!");
                    break;
            }

        } while (opcao != 0);
    }

    public static void menuDeletes(
            Scanner scanner,
            ConsultaProdutos consultaProdutos,
            DeleteProdutos deleteProdutos,
            DeleteClientes deleteClientes,
            DeleteEnderecos deleteEnderecos,
            DeleteCidades deleteCidades
    ) {
        int opcao;

        do {
            System.out.println("\n==============================");
            System.out.println("        Menu de Remoção        ");
            System.out.println("==============================");
            System.out.println("1 - Deletar produto");
            System.out.println("2 - Deletar cliente");
            System.out.println("3 - Deletar endereço");
            System.out.println("4 - Deletar cidade");
            System.out.println("0 - Voltar");

            opcao = lerInteiro(scanner, "\nEscolha uma opção: ");

            switch (opcao) {
                case 1:
                    if (consultaProdutos.listarTodosProdutos()) {
                        int id = lerIdExistente(scanner, "ID do produto: ", "produtos", "produto");
                        deleteProdutos.deletarProduto(id);
                    }
                    break;

                case 2:
                    if (consultaProdutos.listarTodosClientes()) {
                        int id = lerIdExistente(scanner, "ID do cliente: ", "clientes", "cliente");
                        deleteClientes.deletarCliente(id);
                    }
                    break;

                case 3:
                    if (consultaProdutos.listarTodosEnderecos()) {
                        System.out.println("\nAtenção: clientes vinculados a esse endereço ficarão sem endereço.");
                        int id = lerIdExistente(scanner, "ID do endereço: ", "enderecos", "endereço");
                        deleteEnderecos.deletarEndereco(id);
                    }
                    break;

                case 4:
                    if (consultaProdutos.listarTodasCidades()) {
                        System.out.println("\nAtenção: endereços vinculados a essa cidade ficarão sem cidade.");
                        int id = lerIdExistente(scanner, "ID da cidade: ", "cidades", "cidade");
                        deleteCidades.deletarCidade(id);
                    }
                    break;

                case 0:
                    System.out.println("\nVoltando ao menu principal...");
                    break;

                default:
                    System.out.println("\nOpção inválida!");
                    break;
            }

        } while (opcao != 0);
    }

    private static String lerTextoObrigatorio(Scanner scanner, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String texto = scanner.nextLine().trim();

            if (!texto.isEmpty()) {
                return texto;
            }

            System.out.println("Erro: esse campo não pode ficar vazio.");
        }
    }

    private static String lerTextoOpcional(Scanner scanner, String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine().trim();
    }

    private static String lerTextoComPadrao(Scanner scanner, String mensagem, String regex, String erro) {
        while (true) {
            String texto = lerTextoObrigatorio(scanner, mensagem);

            if (texto.matches(regex)) {
                return texto;
            }

            System.out.println(erro);
        }
    }

    private static int lerInteiro(Scanner scanner, String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                int valor = Integer.parseInt(scanner.nextLine().trim());
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Erro: digite um número inteiro válido.");
            }
        }
    }

    private static int lerInteiroMinimo(Scanner scanner, String mensagem, int minimo) {
        while (true) {
            int valor = lerInteiro(scanner, mensagem);

            if (valor >= minimo) {
                return valor;
            }

            System.out.println("Erro: o valor deve ser maior ou igual a " + minimo + ".");
        }
    }

    private static double lerDoubleMinimo(Scanner scanner, String mensagem, double minimo) {
        while (true) {
            try {
                System.out.println(mensagem);
                double valor = Double.parseDouble(scanner.nextLine().trim().replace(",", "."));

                if (valor >= minimo) {
                    return valor;
                }

                System.out.println("Erro: o valor deve ser maior ou igual a " + minimo + ".");

            } catch (NumberFormatException e) {
                System.out.println("Erro: digite um valor numérico válido");
            }
        }

    }

    private static int lerIdExistente(Scanner scanner, String mensagem, String tabela, String nomeRegistro) {
        while (true) {
            int id = lerInteiroMinimo(scanner, mensagem, 1);

            if (BancoUtils.existeRegistro(tabela, id)) {
                return id;
            }

            System.out.println("Erro: não existe " + nomeRegistro + " com esse ID.");
        }

    }
}