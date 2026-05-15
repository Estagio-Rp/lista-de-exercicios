package br.com.rpinfo.analuisa.adapter.rest.controller;

import br.com.rpinfo.analuisa.application.dto.produtos.ProdutosDTO;
import br.com.rpinfo.analuisa.application.usecase.ProdutosUseCase;
import br.com.rpinfo.analuisa.domain.model.entity.Produto;
import br.com.rpinfo.analuisa.shared.LeitorConsole;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class ProdutosController {

    private final LeitorConsole leitor;

    public ProdutosController() {
        Scanner scanner = new Scanner(System.in);
        this.leitor = new LeitorConsole(scanner);
    }

    public void menuProdutos() {
        int opcao;

        do {
            System.out.println("\n=== PRODUTOS ===");
            System.out.println("1. Adicionar Produto");
            System.out.println("2. Listar Produtos");
            System.out.println("3. Editar Produto");
            System.out.println("4. Excluir Produto");
            System.out.println("0. Voltar ao Menu Principal");

            opcao = leitor.lerInteiro("Escolha uma opcao: ");

            switch (opcao) {
                case 1:
                    adicionarProduto();
                    break;
                case 2:
                    listarProdutos();
                    break;
                case 3:
                    editarProduto();
                    break;
                case 4:
                    excluirProduto();
                    break;

                case 0:
                    System.out.println("Voltando ao menu principal");
                    break;

                default:
                    System.out.println("Opcao invalida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    public void adicionarProduto() {
        try {
            ProdutosDTO produto = lerDadosProduto(null);

            ProdutosUseCase.inserirProduto(produto);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean listarProdutos() {
        try {
            List<ProdutosDTO> produtos = ProdutosUseCase.listarProdutos();

            System.out.println("\n--- LISTA DE PRODUTOS ---");

            if (produtos.isEmpty()) {
                System.out.println("Nenhum produto cadastrado.");
                return false;
            }

            for (ProdutosDTO produto : produtos) {
                System.out.println("\nID: " + produto.getId());
                System.out.println("Nome: " + produto.getNome());
                System.out.println("Preço: R$ " + produto.getPreco());
                System.out.println("Categoria: " + produto.getCategoria());
                System.out.println("Estoque: " + produto.getEstoque());
            }

            return true;

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private void editarProduto() {
        try {
            if (!listarProdutos()) {
                return;
            }
            Integer id = leitor.lerInteiroMinimo("\nID do produto que deseja editar: ", 1);

            ProdutosDTO produto = lerDadosProduto(id);

            ProdutosUseCase.atualizarProduto(id, produto);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

    }

    private void excluirProduto() {
        try {
            if (!listarProdutos()) {
                return;
            }

            Integer id = leitor.lerInteiroMinimo("\nID do produto que deseja excluir: ", 1);

            ProdutosUseCase.deletarProduto(id);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private ProdutosDTO lerDadosProduto(Integer id) {
        String nome = leitor.lerTextoNaoNumerico(
                "Nome do produto: ",
                "Erro: o nome do produto não pode ser apenas numérico."
        );

        String precoTexto = leitor.lerTextoComPadrao(
                "Preço: ",
                "\\d+(\\.\\d{1,2})?|\\d+(,\\d{1,2})?",
                "Erro: digite um preço válido. Exemplo: 99.90"
        ).replace(",", ".");

        BigDecimal preco = new BigDecimal(precoTexto);

        String categoria = leitor.lerTextoNaoNumerico(
                "Categoria: ",
                "Erro: a categoria do produto não pode ser numérica."
        ).toLowerCase();

        Integer estoque = leitor.lerInteiroMinimo("Estoque: ", 0);

        return new ProdutosDTO(id, nome, preco, categoria, estoque);
    }
}
