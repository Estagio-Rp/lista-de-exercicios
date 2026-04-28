package insert;

import br.com.rpinfo.analuisa.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddProdutos {

    public void cadastrarProduto(String nome, double preco, String categoria, int estoque) {
        String sql = "INSERT INTO produtos (nome, preco, categoria, estoque) VALUES (?, ?, ?, ?)";

        try (Connection connect = Conexao.conectar();
             PreparedStatement stmt = connect.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setDouble(2, preco);
            stmt.setString(3, categoria);
            stmt.setInt(4, estoque);

            stmt.executeUpdate();

            System.out.println("\nProduto cadastrado com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao cadastrar produto: " + e.getMessage());
        }
    }
}