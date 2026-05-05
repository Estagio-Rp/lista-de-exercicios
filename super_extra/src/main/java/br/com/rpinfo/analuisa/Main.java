package br.com.rpinfo.analuisa;

import delete.DeleteCidades;
import delete.DeleteClientes;
import delete.DeleteEnderecos;
import delete.DeleteProdutos;
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
        UpdateProdutos updateProdutos = new UpdateProdutos();
        UpdateClientes updateClientes = new UpdateClientes();
        UpdateEnderecos updateEnderecos = new UpdateEnderecos();
        UpdateCidades updateCidades = new UpdateCidades();
        DeleteProdutos deleteProdutos = new DeleteProdutos();
        DeleteCidades deleteCidades = new DeleteCidades();
        DeleteClientes deleteClientes = new DeleteClientes();
        DeleteEnderecos deleteEnderecos = new DeleteEnderecos();

        int opcao;

        do {
            System.out.println("Menu principal");
            System.out.println("==========================");
            System.out.println("1 - Consultar produtos");
            System.out.println("2 - Cadastrar novo produto");
            System.out.println("3 - Atualizar registros");
            System.out.println("4 - Deletar registros");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    menuConsultas(scanner, consultaProdutos);
                    break;

                case 2:
                    cadastrarProduto(scanner, addProdutos);
                    break;

                case 3:
                    menuAtualizacoes(scanner, updateProdutos, updateClientes, updateEnderecos, updateCidades);
                    break;

                case 4:
                    menuDeletes(scanner, deleteProdutos, deleteClientes, deleteEnderecos, deleteCidades);

                case 0:
                    System.out.println("Encerrando o sistema...");
                    break;

                default:
                    System.out.println("Opção inválida!");
                    break;
            }

        } while (opcao != 0);

        scanner.close();
    }

    public static void menuConsultas(Scanner scanner, ConsultaProdutos consultaProdutos) {
        int opcaoConsulta;

        do {
            System.out.println("Menu de consultas");
            System.out.println("==================================================");
            System.out.println("1 - Listar todos os produtos");
            System.out.println("2 - Listar nome e preço dos produtos");
            System.out.println("3 - Listar produtos de limpeza");
            System.out.println("4 - Listar produtos com estoque maior que 10");
            System.out.println("5 - Listar produtos com preço entre R$100 e R$1000");
            System.out.println("6 - Listar produtos com nome Gamer");
            System.out.println("7 - Listar 3 produtos mais baratos");
            System.out.println("8 - Contar produtos da categoria Armazenamento");
            System.out.println("9 - Média de preços por categoria");
            System.out.println("0 - Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");

            opcaoConsulta = scanner.nextInt();
            scanner.nextLine();

            switch (opcaoConsulta) {
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

                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;

                default:
                    System.out.println("Opção inválida!");
                    break;
            }

        } while (opcaoConsulta != 0);
    }

    public static void cadastrarProduto(Scanner scanner, AddProdutos addProdutos) {
        System.out.println("Cadastro de produtos");
        System.out.println("=========================");

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Preço: ");
        double preco = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Categoria: ");
        String categoria = scanner.nextLine();

        System.out.print("Estoque: ");
        int estoque = scanner.nextInt();
        scanner.nextLine();

        addProdutos.cadastrarProduto(nome, preco, categoria, estoque);
    }

    public static void menuAtualizacoes(
            Scanner scanner,
            UpdateProdutos updateProdutos,
            UpdateClientes updateClientes,
            UpdateEnderecos updateEnderecos,
            UpdateCidades updateCidades
    ) {
        int opcaoUpdate;

        do {
            System.out.println("\nMenu de atualizações");
            System.out.println("==================================================");
            System.out.println("1 - Atualizar categoria do produto");
            System.out.println("2 - Atualizar estoque do produto");
            System.out.println("3 - Atualizar preço do produto");
            System.out.println("4 - Atualizar email do cliente");
            System.out.println("5 - Atualizar telefone do cliente");
            System.out.println("6 - Atualizar endereço ID do cliente");
            System.out.println("7 - Atualizar rua do endereço");
            System.out.println("8 - Atualizar número do endereço");
            System.out.println("9 - Atualizar complemento do endereço");
            System.out.println("10 - Atualizar bairro do endereço");
            System.out.println("11 - Atualizar nome da cidade");
            System.out.println("12 - Atualizar UF da cidade");
            System.out.println("0 - Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");

            opcaoUpdate = scanner.nextInt();
            scanner.nextLine();

            int id;

            switch (opcaoUpdate) {
                case 1:
                    System.out.print("ID do produto: ");
                    id = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Nova categoria: ");
                    String categoria = scanner.nextLine();

                    updateProdutos.atualizarCategoria(id, categoria);
                    break;

                case 2:
                    System.out.print("ID do produto: ");
                    id = scanner.nextInt();

                    System.out.print("Novo estoque: ");
                    int estoque = scanner.nextInt();
                    scanner.nextLine();

                    updateProdutos.atualizarEstoque(id, estoque);
                    break;

                case 3:
                    System.out.print("ID do produto: ");
                    id = scanner.nextInt();

                    System.out.print("Novo preço: ");
                    double preco = scanner.nextDouble();
                    scanner.nextLine();


                    updateProdutos.atualizarPreco(id, preco);
                    break;

                case 4:
                    System.out.print("ID do cliente: ");
                    id = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Novo email: ");
                    String email = scanner.nextLine();

                    updateClientes.atualizarEmail(id, email);
                    break;

                case 5:
                    System.out.print("ID do cliente: ");
                    id = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Novo telefone: ");
                    String telefone = scanner.nextLine();

                    updateClientes.atualizarTelefone(id, telefone);
                    break;

                case 6:
                    System.out.print("ID do cliente: ");
                    id = scanner.nextInt();

                    System.out.print("Novo ID do endereço: ");
                    int endeId = scanner.nextInt();
                    scanner.nextLine();

                    updateClientes.atualizarEnderecoId(id, endeId);
                    break;

                case 7:
                    System.out.print("ID do endereço: ");
                    id = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Nova rua: ");
                    String rua = scanner.nextLine();

                    updateEnderecos.atualizarRua(id, rua);
                    break;

                case 8:
                    System.out.print("ID do endereço: ");
                    id = scanner.nextInt();

                    System.out.print("Novo número: ");
                    int numero = scanner.nextInt();
                    scanner.nextLine();

                    updateEnderecos.atualizarNumero(id, numero);
                    break;

                case 9:
                    System.out.print("ID do endereço: ");
                    id = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Novo complemento: ");
                    String complemento = scanner.nextLine();

                    updateEnderecos.atualizarComplemento(id, complemento);
                    break;

                case 10:
                    System.out.print("ID do endereço: ");
                    id = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Novo bairro: ");
                    String bairro = scanner.nextLine();

                    updateEnderecos.atualizarBairro(id, bairro);

                case 11:
                    System.out.println("ID da cidade: ");
                    id = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("novo nome da cidade");
                    String nome = scanner.nextLine();

                    updateCidades.atualizarNome(id, nome);
                    break;

                case 12:
                    System.out.println("ID da cidade: ");
                    id = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("Nova UF: ");
                    String UF = scanner.nextLine();

                    updateCidades.atualizarUf(id, UF);
                    break;

                case 0:
                    System.out.println("Voltando ao menu principal..");
                    break;

                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        } while (opcaoUpdate != 0);
    }

    public static void menuDeletes(
            Scanner scanner,
            DeleteProdutos deleteProdutos,
            DeleteClientes deleteClientes,
            DeleteEnderecos deleteEnderecos,
            DeleteCidades deleteCidades
    ) {
        int opcaoDelete;

        do {
            System.out.println("\nMenu de remoção");
            System.out.println("==================================================");
            System.out.println("1 - deletar produto");
            System.out.println("2 - deletar cliente");
            System.out.println("3 - deletar endereço");
            System.out.println("4 - deletar cidade");
            System.out.println("0 - voltar ao menu principal..");
            System.out.print("Escolha uma opção: ");

            opcaoDelete = scanner.nextInt();
            scanner.nextLine();

            int id;

            switch (opcaoDelete) {
                case 1:
                    System.out.print("ID do produto: ");
                    id = scanner.nextInt();
                    scanner.nextLine();

                    deleteProdutos.deletarProduto(id);
                    break;

                case 2:
                    System.out.print("ID do cliente: ");
                    id = scanner.nextInt();
                    scanner.nextLine();

                    deleteClientes.deletarCliente(id);
                    break;

                case 3:
                    System.out.println("ID do endereço: ");
                    id = scanner.nextInt();
                    scanner.nextLine();

                    deleteEnderecos.deletarEndereco(id);

                case 4:
                    System.out.println("ID da cidade: ");
                    id = scanner.nextInt();
                    scanner.nextLine();

                    deleteCidades.deletarCidade(id);
                    break;

                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }while (opcaoDelete != 0);
    }
}
