package br.com.rpinfo.analuisa.shared;

import java.util.Scanner;

public class LeitorConsole {

    private final Scanner scanner;

    public LeitorConsole(Scanner scanner) {
        this.scanner = scanner;
    }

    public int lerInteiro(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Erro: digite um número inteiro válido.");
            }
        }
    }

    public int lerInteiroMinimo(String mensagem, int minimo) {
        while (true) {
            int valor = lerInteiro(mensagem);

            if (valor >= minimo) {
                return valor;
            }

            System.out.println("Erro: o valor deve ser maior ou igual a " + minimo + ".");
        }
    }

    public String lerTextoObrigatorio(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String texto = scanner.nextLine().trim();

            if (!texto.isEmpty()) {
                return texto;
            }
            System.out.println("Erro: esse campo não pode ficar vazio.");
        }
    }

    public String lerTextoComPadrao(String mensagem, String regex, String erro) {
        while (true) {
            String texto = lerTextoObrigatorio(mensagem);

            if (texto.matches(regex)) {
                return texto;
            }

            System.out.println(erro);
        }
    }
}