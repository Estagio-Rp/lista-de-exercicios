package br.com.rpinfo.analuisa;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite a quantidade de linhas da pirâmide: ");
        int quantidadeLinhas = scanner.nextInt();

        for (int linhaAtual = 1; linhaAtual <= quantidadeLinhas; linhaAtual++) {

            for (int quantidadeEspacos = linhaAtual; quantidadeEspacos < quantidadeLinhas; quantidadeEspacos++) {
                System.out.print(" ");
            }

            for (int quantidadeNumeros = 1; quantidadeNumeros <= (2 * linhaAtual - 1); quantidadeNumeros++) {
                System.out.print(linhaAtual);
            }

            System.out.println();
        }

        scanner.close();
    }
}