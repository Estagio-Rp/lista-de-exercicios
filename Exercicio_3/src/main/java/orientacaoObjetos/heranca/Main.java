package orientacaoObjetos.heranca;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int opcao = 0;

        do {
            System.out.println("\n--- SISTEMA DE RH ---");
            System.out.println("1 - Cadastrar Gerente");
            System.out.println("2 - Cadastrar Desenvolvedor");
            System.out.println("3 - Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        System.out.print("Digite o nome do gerente: ");
                        String nomeGerente = scanner.nextLine();

                        double salarioGerente = lerSalarioValido(scanner, "Digite o salário base do gerente: ");

                        Gerente gerente = new Gerente(nomeGerente, salarioGerente);
                        gerente.exibirDados();
                        break;

                    case 2:
                        System.out.print("Digite o nome do desenvolvedor: ");
                        String nomeDesenvolvedor = scanner.nextLine();

                        double salarioDesenvolvedor = lerSalarioValido(scanner, "Digite o salário base do desenvolvedor: ");

                        Desenvolvedor desenvolvedor = new Desenvolvedor(nomeDesenvolvedor, salarioDesenvolvedor);
                        desenvolvedor.exibirDados();
                        break;

                    case 3:
                        System.out.println("Finalizando o programa...");
                        break;

                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }

            } catch (InputMismatchException erro) {
                System.out.println("Erro: digite apenas números para a opção do menu.");
                scanner.nextLine();
            }

        } while (opcao != 3);

        scanner.close();
    }

    public static double lerSalarioValido(Scanner scanner, String mensagem) {
        double salario = -1;

        while (salario < 0) {
            try {
                System.out.print(mensagem);
                salario = scanner.nextDouble();
                scanner.nextLine();

                if (salario < 0) {
                    System.out.println("Erro: o salário base não pode ser negativo.");
                }

            } catch (InputMismatchException erro) {
                System.out.println("Erro: digite um valor numérico para o salário.");
                scanner.nextLine();
            }
        }

        return salario;
    }
}