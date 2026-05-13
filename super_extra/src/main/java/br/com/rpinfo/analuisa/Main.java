package br.com.rpinfo.analuisa;

import br.com.rpinfo.analuisa.adapter.rest.controller.CidadesController;
import br.com.rpinfo.analuisa.adapter.rest.controller.ProdutosController;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        CidadesController cidadesController = new CidadesController();
        ProdutosController produtosController = new ProdutosController();

        int opcao = -1;

        while (opcao != 0) {
            exibirMenuPrincipal();

            try {
                opcao = Integer.parseInt(teclado.nextLine().trim());

                switch (opcao) {
                    case 1:
                        produtosController.menuProdutos();
                        break;

                    case 2:
                        cidadesController.menuCidades();
                        break;

                    case 0:
                        System.out.println("Encerrando aplicação...");
                        break;

                    default:
                        System.out.println("Opção inválida!");
                        break;
                }

            } catch (NumberFormatException e) {
                System.out.println("Erro: digite uma opção numérica válida.");
            }
        }
        teclado.close();
    }

    private static void exibirMenuPrincipal() {
        System.out.println("\n=== LOJA - MENU PRINCIPAL ===");
        System.out.println("1. Gerenciar Produtos");
        System.out.println("2. Gerenciar Cidades");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }
}