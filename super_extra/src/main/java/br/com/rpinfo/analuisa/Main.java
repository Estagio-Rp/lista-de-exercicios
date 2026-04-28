package br.com.rpinfo.analuisa;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {

        try {
            Connection connect = conectar();

            listarTodosProdutos(connect);
            listarNomeEPreco(connect);
            listarProdutosLimpeza(connect);
            listarProdutosEstoqueMaiorQue10(connect);
            listarProdutosPrecoEntre100E1000(connect);
            listarProdutosComNomeGamer(connect);
            listarTresProdutosMaisBaratos(connect);
            contarProdutosArmazenameto(connect);
            agruparCategoriaMediaPrecos(connect);

            connect.close();

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public static Connection conectar() throws Exception {
        Properties properties = new Properties();

        InputStream input = Main.class
                .getClassLoader()
                .getResourceAsStream("application.properties");

        if (input == null) {
            throw new Exception("application.properties file not found.");
        }

        properties.load(input);

        String url = properties.getProperty("spring.datasource.url");
        String usuario = properties.getProperty("spring.datasource.username");
        String senha = properties.getProperty("spring.datasource.password");

        return DriverManager.getConnection(url, usuario, senha);
    }

    public static void listarTodosProdutos(Connection connect) throws Exception {
        String query = "SELECT id, nome, preco, categoria, estoque FROM produtos";

        System.out.println("todos os produtos");

        try (Statement stmt = connect.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Nome: " + rs.getString("nome"));
                System.out.println("Preço: " + rs.getDouble("preco"));
                System.out.println("Categoria: " + rs.getString("categoria"));
                System.out.println("Estoque: " + rs.getInt("estoque"));
                System.out.println("-------------------------");
            }

        }
    }

    public static void listarNomeEPreco(Connection connect) throws Exception {
        String query = "SELECT nome, preco FROM produtos";

        System.out.println("Nome e preço dos produtos");

        try (Statement stmt = connect.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                System.out.println("Nome: " + rs.getString("nome"));
                System.out.println("Preço: R$ " + rs.getDouble("preco"));
                System.out.println("-------------------------");
            }
        }
    }

    public static void listarProdutosLimpeza(Connection connect) throws Exception {
        String query = "SELECT id, nome, preco, categoria, estoque FROM produtos WHERE categoria = 'Limpeza'";

        System.out.println("Produtos de limpeza");

        try (Statement stmt = connect.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Nome: " + rs.getString("nome"));
                System.out.println("Preço: R$ " + rs.getDouble("preco"));
                System.out.println("Categoria: " + rs.getString("categoria"));
                System.out.println("Estoque: " + rs.getInt("estoque"));
                System.out.println("-------------------------");
            }
        }
    }

    public static void listarProdutosEstoqueMaiorQue10(Connection connect) throws Exception {
        String query = "SELECT id, nome, preco, categoria, estoque FROM produtos WHERE estoque > 10";

        System.out.println("Produtos de estoque");
        try (Statement stmt = connect.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Nome: " + rs.getString("nome"));
                System.out.println("Preço: R$ " + rs.getDouble("preco"));
                System.out.println("Categoria: " + rs.getString("categoria"));
                System.out.println("Estoque: " + rs.getInt("estoque"));
                System.out.println("-------------------------");
            }
        }
    }

    public static void listarProdutosPrecoEntre100E1000(Connection connect) throws Exception {
        String query = "SELECT id, nome, preco, categoria, estoque FROM produtos WHERE preco BETWEEN 100 AND 1000";

        System.out.println("Produtos de preco entre R$100 e R$1000");

        try (Statement stmt = connect.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Nome: " + rs.getString("nome"));
                System.out.println("Preço: R$ " + rs.getDouble("preco"));
                System.out.println("Categoria: " + rs.getString("categoria"));
                System.out.println("Estoque: " + rs.getInt("estoque"));
                System.out.println("-------------------------");
            }
        }
    }

    public static void listarProdutosComNomeGamer(Connection connect) throws Exception {
        String query = "SELECT id, nome, preco, categoria, estoque FROM produtos WHERE nome ILIKE '%Gamer%'";

        System.out.println("Produtos com nome '%Gamer%'");

        try (Statement stmt = connect.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Nome: " + rs.getString("nome"));
                System.out.println("Preço : R$ " + rs.getDouble("preco"));
                System.out.println("Categoria: " + rs.getString("categoria"));
                System.out.println("Estoque: " + rs.getInt("estoque"));
                System.out.println("-------------------------");

            }
        }
    }

    public static void listarTresProdutosMaisBaratos(Connection connect) throws Exception {
        String query = "SELECT id, nome, preco, categoria, estoque FROM produtos ORDER BY preco ASC LIMIT 3";

        System.out.println("3 produtos mais baratos");

        try (Statement stmt = connect.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Nome: " + rs.getString("nome"));
                System.out.println("Preço: R$ " + rs.getDouble("preco"));
                System.out.println("Categoria: " + rs.getString("categoria"));
                System.out.println("Estoque: " + rs.getInt("estoque"));
                System.out.println("-------------------------");

            }
        }
    }

    public static void contarProdutosArmazenameto(Connection connect) throws Exception {
        String query = "SELECT COUNT(*) AS total FROM produtos WHERE categoria = 'Armazenamento'";

        System.out.println("Total de produtos da categoria armazenameto");

        try (Statement stmt = connect.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                System.out.println("Total: " + rs.getInt("total"));
                System.out.println("-------------------------");
            }
        }
    }

    public static void agruparCategoriaMediaPrecos(Connection connect) throws Exception {
        String query = "SELECT categoria, AVG(preco) AS media_preco FROM produtos GROUP BY categoria";

        System.out.println("Média de preços por categoria");

        try (Statement stmt = connect.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                System.out.println("Categoria: " + rs.getString("categoria"));
                System.out.println("Média de preço: R$" + rs.getDouble("media_preco"));
            }
        }
    }
}
