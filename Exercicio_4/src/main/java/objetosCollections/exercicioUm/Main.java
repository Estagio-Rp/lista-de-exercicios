package objetosCollections.exercicioUm;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Turma turma = new Turma();

        int opcao;

        do {
            System.out.println("\n--- CONTROLE DE ALUNOS ---");
            System.out.println("1- Adicionar aluno");
            System.out.println("2- Listar alunos");
            System.out.println("3- Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Digite o nome do aluno: ");
                    String nome = scanner.nextLine();

                    System.out.print("Digite a idade do aluno: ");
                    int idade = scanner.nextInt();
                    scanner.nextLine();

                    Aluno aluno = new Aluno(nome, idade);
                    turma.adicionarAluno(aluno);

                    System.out.println("Aluno adicionado com sucesso!");
                    break;

                case 2:
                    turma.listarAlunos();
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