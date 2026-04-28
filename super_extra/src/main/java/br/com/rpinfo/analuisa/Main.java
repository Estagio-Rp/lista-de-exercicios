package br.com.rpinfo.analuisa;

import insert.AddProdutos;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        ConsultaProdutos consultaProdutos = new ConsultaProdutos();
        AddProdutos addProdutos = new AddProdutos();

        int opcao;

        do {
            System.out.println("Menu principal");
            System.out.println("==========================");
            System.out.println("1 - Consultar produtos");
            System.out.println("2 - Cadastrar novo produto");
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
}