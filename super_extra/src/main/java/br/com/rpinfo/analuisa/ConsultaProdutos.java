package br.com.rpinfo.analuisa;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConsultaProdutos {

    public void listarTodosProdutos() {
        String query = "SELECT id, nome, preco, categoria, estoque FROM produtos";

        System.out.println("--- Todos os produtos ---");

        try (Connection connect = Conexao.conectar();
             Statement stmt = connect.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                mostrarProdutoCompleto(rs);
            }

        } catch (Exception e) {
            System.out.println("Erro ao listar todos os produtos: " + e.getMessage());
        }
    }

    public void listarNomeEPreco() {
        String query = "SELECT nome, preco FROM produtos";

        System.out.println("--- Nome e preço dos produtos ---");

        try (Connection connect = Conexao.conectar();
             Statement stmt = connect.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                System.out.println("-------------------------");
                System.out.println("Nome: " + rs.getString("nome"));
                System.out.println("Preço: R$ " + rs.getDouble("preco"));
            }

            System.out.println("-------------------------");

        } catch (Exception e) {
            System.out.println("Erro ao listar nome e preço: " + e.getMessage());
        }
    }

    public void listarProdutosLimpeza() {
        String query = "SELECT id, nome, preco, categoria, estoque FROM produtos WHERE categoria = 'Limpeza'";

        System.out.println("--- Produtos de limpeza ---");

        try (Connection connect = Conexao.conectar();
             Statement stmt = connect.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                mostrarProdutoCompleto(rs);
            }

        } catch (Exception e) {
            System.out.println("Erro ao listar produtos de limpeza: " + e.getMessage());
        }
    }

    public void listarProdutosEstoqueMaiorQue10() {
        String query = "SELECT id, nome, preco, categoria, estoque FROM produtos WHERE estoque > 10";

        System.out.println("--- Produtos com estoque maior que 10 ---");

        try (Connection connect = Conexao.conectar();
             Statement stmt = connect.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                mostrarProdutoCompleto(rs);
            }

        } catch (Exception e) {
            System.out.println("Erro ao listar produtos com estoque maior que 10: " + e.getMessage());
        }
    }

    public void listarProdutosPrecoEntre100E1000() {
        String query = "SELECT id, nome, preco, categoria, estoque FROM produtos WHERE preco BETWEEN 100 AND 1000";

        System.out.println("\n--- Produtos com preços entre R$100 E R$1000 ---");

        try (Connection connect = Conexao.conectar();
             Statement stmt = connect.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                mostrarProdutoCompleto(rs);
            }

        } catch (Exception e) {
            System.out.println("Erro ao listar produtos por faixa de preço: " + e.getMessage());
        }
    }

    public void listarProdutosComNomeGamer() {
        String query = "SELECT id, nome, preco, categoria, estoque FROM produtos WHERE nome ILIKE '%Gamer%'";

        System.out.println("--- Produtos com nome GAMER ---");

        try (Connection connect = Conexao.conectar();
             Statement stmt = connect.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                mostrarProdutoCompleto(rs);
            }

        } catch (Exception e) {
            System.out.println("Erro ao listar produtos com nome Gamer: " + e.getMessage());
        }
    }

    public void listarTresProdutosMaisBaratos() {
        String query = "SELECT id, nome, preco, categoria, estoque FROM produtos ORDER BY preco ASC LIMIT 3";

        System.out.println("--- 3 Produtos mais baratos ---");

        try (Connection connect = Conexao.conectar();
             Statement stmt = connect.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                mostrarProdutoCompleto(rs);
            }

        } catch (Exception e) {
            System.out.println("Erro ao listar os 3 produtos mais baratos: " + e.getMessage());
        }
    }

    public void contarProdutosArmazenamento() {
        String query = "SELECT COUNT(*) AS total FROM produtos WHERE categoria = 'Armazenamento'";

        System.out.println("--- Total de produtos da categoria ARMAZENAMENTO ---");

        try (Connection connect = Conexao.conectar();
             Statement stmt = connect.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                System.out.println("Total: " + rs.getInt("total"));
                System.out.println("-------------------------");
            }

        } catch (Exception e) {
            System.out.println("Erro ao contar produtos de armazenamento: " + e.getMessage());
        }
    }

    public void agruparCategoriaMediaPrecos() {
        String query = "SELECT categoria, AVG(preco) AS media_preco FROM produtos GROUP BY categoria";

        System.out.println("--- Média de preços por categoria ---");

        try (Connection connect = Conexao.conectar();
             Statement stmt = connect.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                System.out.println("-------------------------");
                System.out.println("Categoria: " + rs.getString("categoria"));
                System.out.println("Média de preço: R$ " + rs.getDouble("media_preco"));
            }

            System.out.println("-------------------------");

        } catch (Exception e) {
            System.out.println("Erro ao agrupar média de preços por categoria: " + e.getMessage());
        }
    }

    private void mostrarProdutoCompleto(ResultSet rs) throws Exception {
        System.out.println("-------------------------");
        System.out.println("ID: " + rs.getInt("id"));
        System.out.println("Nome: " + rs.getString("nome"));
        System.out.println("Preço: R$ " + rs.getDouble("preco"));
        System.out.println("Categoria: " + rs.getString("categoria"));
        System.out.println("Estoque: " + rs.getInt("estoque"));
    }
}