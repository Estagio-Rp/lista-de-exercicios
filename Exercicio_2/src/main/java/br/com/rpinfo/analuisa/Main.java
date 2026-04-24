package br.com.rpinfo.analuisa;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Map;


public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        HashMap<String, Double> catalogo = new HashMap<>();

        int opcao;

        do {
            System.out.println("CATÁLOGO DE PRODUTOS");
            System.out.println("1- Adicionar produto");
            System.out.println("2- Consultar preço de um produto");
            System.out.println("3- Exibir todos os produtos");
            System.out.println("4- Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("digite o nome do produto: ");
                    String nomeProduto = scanner.nextLine();

                    System.out.println("digite o preço do protuto: ");
                    double precoProduto = scanner.nextDouble();
                    scanner.nextLine();
                    catalogo.put(nomeProduto, precoProduto);
                    System.out.println("Produto adicionado com sucesso!");
                    break;

                case 2:
                    System.out.print("Digite o nome do produto que deseja consultar: ");
                    String produtoConsulta = scanner.nextLine();

                    if (catalogo.containsKey(produtoConsulta)) {
                        double preco = catalogo.get(produtoConsulta);
                        System.out.println("Preço do produto " + produtoConsulta + ": R$ " + preco);
                    } else {
                        System.out.println("Produto não encontrado no catálogo.");
                    }
                    break;

                case 3:
                    if (catalogo.isEmpty()) {
                        System.out.println("Nenhum produto cadastrado.");
                    } else {
                        System.out.println("Produtos cadastrados");

                        for (Map.Entry<String, Double> produto : catalogo.entrySet()) {
                            System.out.println("Produto: " + produto.getKey() + " | Preço: R$ " + produto.getValue());
                        }
                    }
                    break;

                case 4:
                    System.out.println("Saindo do programa..");

                default:
                    System.out.println("opção incorreta, tente novamente");
            }

        } while (opcao != 4);

        scanner.close();
    }
}
