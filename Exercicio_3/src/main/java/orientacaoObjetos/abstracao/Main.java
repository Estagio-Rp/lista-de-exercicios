package orientacaoObjetos.abstracao;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int opcao;

        do {
            System.out.println("\n--- SISTEMA DE PAGAMENTOS ---");
            System.out.println("1 - Pagamento com Cartão de Crédito");
            System.out.println("2 - Pagamento com Boleto Bancário");
            System.out.println("3 - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Digite o valor do pagamento: ");
                    double valorCartao = scanner.nextDouble();
                    scanner.nextLine();

                    System.out.print("Digite o número do cartão com 16 dígitos: ");
                    String numeroCartao = scanner.nextLine();

                    System.out.print("Digite o nome do titular: ");
                    String nomeTitular = scanner.nextLine();

                    System.out.print("Digite o CVV com 3 dígitos: ");
                    String cvv = scanner.nextLine();

                    CartaoCredito cartaoCredito = new CartaoCredito(
                            valorCartao,
                            numeroCartao,
                            nomeTitular,
                            cvv
                    );

                    cartaoCredito.processarPagamento();
                    break;

                case 2:
                    System.out.print("Digite o valor do pagamento: ");
                    double valorBoleto = scanner.nextDouble();
                    scanner.nextLine();

                    System.out.print("Digite o código do boleto: ");
                    String codigoBoleto = scanner.nextLine();

                    Boleto boleto = new Boleto(valorBoleto, codigoBoleto);

                    boleto.processarPagamento();
                    break;

                case 3:
                    System.out.println("Encerrando o sistema...");
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }

        } while (opcao != 3);

        scanner.close();
    }
}