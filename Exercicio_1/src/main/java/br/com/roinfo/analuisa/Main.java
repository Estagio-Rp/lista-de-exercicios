package br.com.roinfo.analuisa;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> tarefas = new ArrayList<>();
        int opcao = 0;

        while (opcao != 4) {
            System.out.println("---MENU DE TAREFAS---");
            System.out.println("1 - Adicionar tarefa");
            System.out.println("2 - Remover tarefa");
            System.out.println("3 - Exibir tarefas");
            System.out.println("4 - sair");
            System.out.println("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("Digite o nome do tarefa: ");
                    String tarefa = scanner.nextLine();
                    tarefas.add(tarefa);
                    System.out.println("Tarefa adicionada com sucesso!!");
                    break;

                case 2:
                    if (tarefas.isEmpty()) {
                        System.out.println("Não há tarefas para remover");
                    } else {
                        System.out.println("---tarefas---");
                        for (int i = 0; i < tarefas.size(); i++) {
                            System.out.println((i + 1) + " - " + tarefas.get(i));
                        }

                        System.out.println("digite o numero da tarefa que precisa remover: ");
                        int indice = scanner.nextInt();
                        scanner.nextLine();

                        if (indice >= 1 && indice <= tarefas.size()) {
                            String removida = tarefas.remove(indice - 1);
                            System.out.println("Tarefa removida: " + removida);

                        } else {
                            System.out.println("Número inválido");
                        }

                    }
                    break;

                case 3:
                    if (tarefas.isEmpty()) {
                        System.out.println("Nenhuma tarefa cadastrada");
                    } else {
                        System.out.println("---Lista de tarefas--");
                        for (int i = 0; i < tarefas.size(); i++) {
                            System.out.println((i + 1) + " - " + tarefas.get(i));
                        }
                    }
                    break;

                case 4:
                    System.out.println("saindo..");

                    break;

                default:
                    System.out.println("opção inválida");

            }
        }

        scanner.close();
    }
}



