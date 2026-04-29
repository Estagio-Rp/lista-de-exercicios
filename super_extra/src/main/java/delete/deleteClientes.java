package delete;

import br.com.rpinfo.analuisa.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class deleteClientes {

    public void deletarCliente(int id) {
        String sql = "DELETE FROM clientes WHERE id = ?";

        try (Connection connect = Conexao.conectar();
             PreparedStatement stmt = connect.prepareStatement(sql)) {

            stmt.setInt(1, id);

            stmt.executeUpdate();

            System.out.println("Cliente deletado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao deletar cliente!" + e.getMessage());
        }
    }
}
