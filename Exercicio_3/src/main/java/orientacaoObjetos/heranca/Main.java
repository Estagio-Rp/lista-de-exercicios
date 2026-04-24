package orientacaoObjetos.heranca;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int opcao;

        do {
            System.out.println("\n--- SISTEMA DE RH ---");
            System.out.println("1 - Cadastrar Gerente");
            System.out.println("2 - Cadastrar Desenvolvedor");
            System.out.println("3 - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Digite o nome do gerente: ");
                    String nomeGerente = scanner.nextLine();

                    System.out.print("Digite o salário base do gerente: ");
                    double salarioGerente = scanner.nextDouble();

                    Gerente gerente = new Gerente(nomeGerente, salarioGerente);

                    gerente.exibirDados();
                    break;

                case 2:
                    System.out.print("Digite o nome do desenvolvedor: ");
                    String nomeDesenvolvedor = scanner.nextLine();

                    System.out.print("Digite o salário base do desenvolvedor: ");
                    double salarioDesenvolvedor = scanner.nextDouble();

                    Desenvolvedor desenvolvedor = new Desenvolvedor(nomeDesenvolvedor, salarioDesenvolvedor);

                    desenvolvedor.exibirDados();
                    break;

                case 3:
                    System.out.println("finalizando o programa..");
                    break;

                default:
                    System.out.print("opçao inválida. Tente novamente");

            }
        } while (opcao != 3);

        scanner.close();
    }
}

