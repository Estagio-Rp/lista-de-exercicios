package delete;

import br.com.rpinfo.analuisa.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class DeleteProdutos {

    public void deletarProduto(int id) {
        String sql = "DELETE FROM produtos WHERE id = ?";

        try (Connection connect = Conexao.conectar();
             PreparedStatement stmt = connect.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("\nProduto deletado com sucesso!");
            } else {
                System.out.println("\nNenhum produto encontrado com esse ID.");
            }

        } catch (Exception e) {
            System.out.println("\nErro ao deletar produto: " + e.getMessage());
        }
    }
}