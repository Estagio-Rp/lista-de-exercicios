package orientacaoObjetos.encapsulamento;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Criação da conta bancária");

        System.out.print("Digite o nome do titular: ");
        String titular = scanner.nextLine();

        System.out.print("Digite o saldo inicial");
        double saldoInicial = scanner.nextDouble();

        ContaBancaria conta = new ContaBancaria(titular, saldoInicial);

        int opcao;

        do {
            System.out.println("\n--- MENU BANCÁRIO ---");
            System.out.println("1 - Depositar");
            System.out.println("2 - Sacar");
            System.out.println("3 - Exibir dados da conta");
            System.out.println("4 - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    System.out.print("Digite o valor para depósito: ");
                    double valorDeposito = scanner.nextDouble();

                    conta.depositar(valorDeposito);
                    break;

                case 2:
                    System.out.print("Digite o valor para saque: ");
                    double valorSaque = scanner.nextDouble();

                    conta.sacar(valorSaque);
                    break;

                case 3:
                    conta.exibirDados();
                    break;

                case 4:
                    System.out.println("Encerrando o sistema bancário...");
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }

        }while (opcao != 4);

        scanner.close();
    }
}