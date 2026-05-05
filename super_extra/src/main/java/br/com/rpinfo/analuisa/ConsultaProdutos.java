package br.com.rpinfo.analuisa;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConsultaProdutos {

    public boolean listarTodosProdutos() {
        String query = "SELECT id, nome, preco, categoria, estoque FROM produtos ORDER BY id";

        System.out.println("\n--- Todos os produtos ---");

        try (Connection connect = Conexao.conectar();
             Statement stmt = connect.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            boolean encontrou = false;

            while (rs.next()) {
                encontrou = true;
                mostrarProdutoCompleto(rs);
            }

            mostrarMensagemSeVazio(encontrou, "Nenhum produto cadastrado.");
            return encontrou;

        } catch (Exception e) {
            System.out.println("Erro ao listar todos os produtos: " + e.getMessage());
            return false;
        }
    }

    public boolean listarNomeEPreco() {
        String query = "SELECT nome, preco FROM produtos ORDER BY nome";

        System.out.println("\n--- Nome e preço dos produtos ---");

        try (Connection connect = Conexao.conectar();
             Statement stmt = connect.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            boolean encontrou = false;

            while (rs.next()) {
                encontrou = true;
                System.out.println("\n-------------------------");
                System.out.println("Nome: " + rs.getString("nome"));
                System.out.println("Preço: R$ " + rs.getDouble("preco"));
            }

            mostrarMensagemSeVazio(encontrou, "Nenhum produto cadastrado.");
            return encontrou;

        } catch (Exception e) {
            System.out.println("Erro ao listar nome e preço: " + e.getMessage());
            return false;
        }
    }

    public boolean listarProdutosLimpeza() {
        String query = "SELECT id, nome, preco, categoria, estoque FROM produtos WHERE categoria ILIKE 'Limpeza' ORDER BY id";

        System.out.println("\n--- Produtos de limpeza ---");

        try (Connection connect = Conexao.conectar();
             Statement stmt = connect.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            boolean encontrou = false;

            while (rs.next()) {
                encontrou = true;
                mostrarProdutoCompleto(rs);
            }

            mostrarMensagemSeVazio(encontrou, "Nenhum produto de limpeza encontrado.");
            return encontrou;

        } catch (Exception e) {
            System.out.println("Erro ao listar produtos de limpeza: " + e.getMessage());
            return false;
        }
    }

    public boolean listarProdutosEstoqueMaiorQue10() {
        String query = "SELECT id, nome, preco, categoria, estoque FROM produtos WHERE estoque > 10 ORDER BY id";

        System.out.println("\n--- Produtos com estoque maior que 10 ---");

        try (Connection connect = Conexao.conectar();
             Statement stmt = connect.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            boolean encontrou = false;

            while (rs.next()) {
                encontrou = true;
                mostrarProdutoCompleto(rs);
            }

            mostrarMensagemSeVazio(encontrou, "Nenhum produto com estoque maior que 10 encontrado.");
            return encontrou;

        } catch (Exception e) {
            System.out.println("Erro ao listar produtos com estoque maior que 10: " + e.getMessage());
            return false;
        }
    }

    public boolean listarProdutosPrecoEntre100E1000() {
        String query = "SELECT id, nome, preco, categoria, estoque FROM produtos WHERE preco BETWEEN 100 AND 1000 ORDER BY preco";

        System.out.println("\n--- Produtos com preço entre R$100 e R$1000 ---");

        try (Connection connect = Conexao.conectar();
             Statement stmt = connect.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            boolean encontrou = false;

            while (rs.next()) {
                encontrou = true;
                mostrarProdutoCompleto(rs);
            }

            mostrarMensagemSeVazio(encontrou, "Nenhum produto nessa faixa de preço encontrado.");
            return encontrou;

        } catch (Exception e) {
            System.out.println("Erro ao listar produtos por faixa de preço: " + e.getMessage());
            return false;
        }
    }

    public boolean listarProdutosComNomeGamer() {
        String query = "SELECT id, nome, preco, categoria, estoque FROM produtos WHERE nome ILIKE '%Gamer%' ORDER BY id";

        System.out.println("\n--- Produtos com nome Gamer ---");

        try (Connection connect = Conexao.conectar();
             Statement stmt = connect.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            boolean encontrou = false;

            while (rs.next()) {
                encontrou = true;
                mostrarProdutoCompleto(rs);
            }

            mostrarMensagemSeVazio(encontrou, "Nenhum produto com nome Gamer encontrado.");
            return encontrou;

        } catch (Exception e) {
            System.out.println("Erro ao listar produtos com nome Gamer: " + e.getMessage());
            return false;
        }
    }

    public boolean listarTresProdutosMaisBaratos() {
        String query = "SELECT id, nome, preco, categoria, estoque FROM produtos ORDER BY preco ASC LIMIT 3";

        System.out.println("\n--- 3 produtos mais baratos ---");

        try (Connection connect = Conexao.conectar();
             Statement stmt = connect.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            boolean encontrou = false;

            while (rs.next()) {
                encontrou = true;
                mostrarProdutoCompleto(rs);
            }

            mostrarMensagemSeVazio(encontrou, "Nenhum produto cadastrado.");
            return encontrou;

        } catch (Exception e) {
            System.out.println("Erro ao listar os 3 produtos mais baratos: " + e.getMessage());
            return false;
        }
    }

    public boolean contarProdutosArmazenamento() {
        String query = "SELECT COUNT(*) AS total FROM produtos WHERE categoria ILIKE 'Armazenamento'";

        System.out.println("\n--- Total de produtos da categoria armazenamento ---");

        try (Connection connect = Conexao.conectar();
             Statement stmt = connect.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                System.out.println("\nTotal: " + rs.getInt("total"));
                System.out.println("-------------------------");
                return true;
            }

            return false;

        } catch (Exception e) {
            System.out.println("Erro ao contar produtos de armazenamento: " + e.getMessage());
            return false;
        }
    }

    public boolean agruparCategoriaMediaPrecos() {
        String query = "SELECT categoria, AVG(preco) AS media_preco FROM produtos GROUP BY categoria ORDER BY categoria";

        System.out.println("\n--- Média de preços por categoria ---");

        try (Connection connect = Conexao.conectar();
             Statement stmt = connect.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            boolean encontrou = false;

            while (rs.next()) {
                encontrou = true;
                System.out.println("\n-------------------------");
                System.out.println("Categoria: " + rs.getString("categoria"));
                System.out.println("Média de preço: R$ " + rs.getDouble("media_preco"));
            }

            mostrarMensagemSeVazio(encontrou, "Nenhuma categoria encontrada.");
            return encontrou;

        } catch (Exception e) {
            System.out.println("Erro ao agrupar média de preços por categoria: " + e.getMessage());
            return false;
        }
    }

    public boolean listarTodasCidades() {
        String query = "SELECT id, nome, uf FROM cidades ORDER BY id";

        System.out.println("\n--- Todas as cidades ---");

        try (Connection connect = Conexao.conectar();
             Statement stmt = connect.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            boolean encontrou = false;

            while (rs.next()) {
                encontrou = true;
                mostrarCidade(rs);
            }

            mostrarMensagemSeVazio(encontrou, "Nenhuma cidade cadastrada.");
            return encontrou;

        } catch (Exception e) {
            System.out.println("Erro ao listar cidades: " + e.getMessage());
            return false;
        }
    }

    public boolean listarTodosEnderecos() {
        String query = "SELECT id, cep, rua, numero, complemento, bairro, cida_id FROM enderecos ORDER BY id";

        System.out.println("\n--- Todos os endereços ---");

        try (Connection connect = Conexao.conectar();
             Statement stmt = connect.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            boolean encontrou = false;

            while (rs.next()) {
                encontrou = true;
                mostrarEndereco(rs);
            }

            mostrarMensagemSeVazio(encontrou, "Nenhum endereço cadastrado.");
            return encontrou;

        } catch (Exception e) {
            System.out.println("Erro ao listar endereços: " + e.getMessage());
            return false;
        }
    }

    public boolean listarTodosClientes() {
        String query = "SELECT id, nome, email, cpf, telefone, data_cadastro, ende_id FROM clientes ORDER BY id";

        System.out.println("\n--- Todos os clientes ---");

        try (Connection connect = Conexao.conectar();
             Statement stmt = connect.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            boolean encontrou = false;

            while (rs.next()) {
                encontrou = true;
                mostrarCliente(rs);
            }

            mostrarMensagemSeVazio(encontrou, "Nenhum cliente cadastrado.");
            return encontrou;

        } catch (Exception e) {
            System.out.println("Erro ao listar clientes: " + e.getMessage());
            return false;
        }
    }

    public void listarTodosRegistros() {
        listarTodosProdutos();
        listarTodasCidades();
        listarTodosEnderecos();
        listarTodosClientes();
    }

    private void mostrarProdutoCompleto(ResultSet rs) throws Exception {
        System.out.println("\n-------------------------");
        System.out.println("ID: " + rs.getInt("id"));
        System.out.println("Nome: " + rs.getString("nome"));
        System.out.println("Preço: R$ " + rs.getDouble("preco"));
        System.out.println("Categoria: " + rs.getString("categoria"));
        System.out.println("Estoque: " + rs.getInt("estoque"));
    }

    private void mostrarCidade(ResultSet rs) throws Exception {
        System.out.println("\n-------------------------");
        System.out.println("ID: " + rs.getInt("id"));
        System.out.println("Cidade: " + rs.getString("nome"));
        System.out.println("UF: " + rs.getString("uf"));
    }

    private void mostrarEndereco(ResultSet rs) throws Exception {
        System.out.println("\n-------------------------");
        System.out.println("ID: " + rs.getInt("id"));
        System.out.println("CEP: " + rs.getString("cep"));
        System.out.println("Rua: " + rs.getString("rua"));
        System.out.println("Número: " + rs.getInt("numero"));
        System.out.println("Complemento: " + rs.getString("complemento"));
        System.out.println("Bairro: " + rs.getString("bairro"));
        System.out.println("ID da cidade: " + rs.getObject("cida_id"));
    }

    private void mostrarCliente(ResultSet rs) throws Exception {
        System.out.println("\n-------------------------");
        System.out.println("ID: " + rs.getInt("id"));
        System.out.println("Nome: " + rs.getString("nome"));
        System.out.println("Email: " + rs.getString("email"));
        System.out.println("CPF: " + rs.getString("cpf"));
        System.out.println("Telefone: " + rs.getString("telefone"));
        System.out.println("Data de cadastro: " + rs.getTimestamp("data_cadastro"));
        System.out.println("ID do endereço: " + rs.getObject("ende_id"));
    }

    private void mostrarMensagemSeVazio(boolean encontrou, String mensagem) {
        if (!encontrou) {
            System.out.println("\n" + mensagem);
        }

        System.out.println("-------------------------");
    }
}