package br.com.rpinfo.analuisa;

//package br.com.rpinfo.analuisa;
//import br.com.rpinfo.analuisa.adapter.rest.controller.CidadesController;
//import br.com.rpinfo.analuisa.adapter.rest.controller.ClientesController;
//import br.com.rpinfo.analuisa.adapter.rest.controller.EnderecosController;
//import br.com.rpinfo.analuisa.adapter.rest.controller.ProdutosController;
//
//import java.util.Scanner;
//
//public class Main {
//
//    public static void main(String[] args) {
//        Scanner teclado = new Scanner(System.in);
//
//        CidadesController cidadesController = new CidadesController();
//        ProdutosController produtosController = new ProdutosController();
//        EnderecosController enderecosController = new EnderecosController();
//        ClientesController clientesController = new ClientesController();
//
//        int opcao = -1;
//
//        while (opcao != 0) {
//            exibirMenuPrincipal();
//
//            try {
//                opcao = Integer.parseInt(teclado.nextLine().trim());
//
//                switch (opcao) {
//                    case 1:
//                        produtosController.menuProdutos();
//                        break;
//
//                    case 2:
//                        cidadesController.menuCidades();
//                        break;
//
//                    case 3:
//                        enderecosController.menuEnderecos();
//
//                    case 4:
//                        clientesController.menuClientes();
//
//                    case 0:
//                        System.out.println("Encerrando aplicação...");
//                        break;
//
//                    default:
//                        System.out.println("Opção inválida!");
//                        break;
//                }
//
//            } catch (NumberFormatException e) {
//                System.out.println("Erro: digite uma opção numérica válida.");
//            }
//        }
//        teclado.close();
//    }
//
//    private static void exibirMenuPrincipal() {
//        System.out.println("\n=== LOJA - MENU PRINCIPAL ===");
//        System.out.println("1. Gerenciar Produtos");
//        System.out.println("2. Gerenciar Cidades");
//        System.out.println("3. Gerenciar Enderecos");
//        System.out.println("4. Gerenciar Clientes");
//        System.out.println("0. Sair");
//        System.out.print("Escolha uma opção: ");
//    }
//}



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
