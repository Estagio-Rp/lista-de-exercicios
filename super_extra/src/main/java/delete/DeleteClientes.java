package delete;

import br.com.rpinfo.analuisa.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class DeleteClientes {

    public void deletarCliente(int id) {
        String sql = "DELETE FROM clientes WHERE id = ?";

        try (Connection connect = Conexao.conectar();
             PreparedStatement stmt = connect.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("\nCliente deletado com sucesso!");
            } else {
                System.out.println("\nNenhum cliente encontrado com esse ID.");
            }

        } catch (Exception e) {
            System.out.println("\nErro ao deletar cliente: " + e.getMessage());
        }
    }
}