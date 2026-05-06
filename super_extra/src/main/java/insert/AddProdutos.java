package insert;

import br.com.rpinfo.analuisa.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class AddProdutos {

    public void cadastrarProduto(String nome, double preco, String categoria, int estoque) {
        String sql = "INSERT INTO produtos (nome, preco, categoria, estoque) VALUES (?, ?, ?, ?)";

        try (Connection connect = Conexao.conectar();
             PreparedStatement stmt = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, nome);
            stmt.setDouble(2, preco);
            stmt.setString(3, categoria);
            stmt.setInt(4, estoque);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        System.out.println("\nProduto cadastrado com sucesso! ID gerado: " + rs.getInt(1));
                    } else {
                        System.out.println("\nProduto cadastrado com sucesso!");
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("\nErro ao cadastrar produto: " + e.getMessage());
        }
    }
}