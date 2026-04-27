package br.com.rpinfo.analuisa;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ArrayList<Veiculo> veiculos = new ArrayList<>();

        int opcao;
        do {
            System.out.println("--- CONTROLE DE VEÍCULOS ---");
            System.out.println("1 - Cadastrar carro");
            System.out.println("2 - Cadastrar moto");
            System.out.println("3 - Listar veículos");
            System.out.println("4 - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Digite a marca do carro: ");
                    String marcaCarro = scanner.nextLine();

                    System.out.print("Digite o modelo do carro: ");
                    String modeloCarro = scanner.nextLine();

                    System.out.print("Digite o tipo de combustível: ");
                    String tipoCombustivel = scanner.nextLine();

                    Carro carro = new Carro(marcaCarro, modeloCarro, tipoCombustivel);
                    veiculos.add(carro);

                    System.out.println("Carro cadastrado com sucesso!");
                    break;

                case 2:
                    System.out.print("Digite a marca da moto: ");
                    String marcaMoto = scanner.nextLine();

                    System.out.print("Digite o modelo da moto: ");
                    String modeloMoto = scanner.nextLine();

                    System.out.print("Digite as cilindradas da moto: ");
                    int cilindradas = scanner.nextInt();
                    scanner.nextLine();

                    Moto moto = new Moto(marcaMoto, modeloMoto, cilindradas);
                    veiculos.add(moto);

                    System.out.println("Moto cadastrada com sucesso!");
                    break;

                case 3:
                    if (veiculos.isEmpty()) {
                        System.out.println("Nenhum veículo cadastrado.");
                    } else {
                        System.out.println("\n--- VEÍCULOS CADASTRADOS ---");

                        for (Veiculo veiculo : veiculos) {
                            veiculo.exibirInformacoes();
                        }
                    }
                    break;

                case 4:
                    System.out.println("Encerrando o sistema...");
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }

        } while (opcao != 4);

        scanner.close();
    }
}