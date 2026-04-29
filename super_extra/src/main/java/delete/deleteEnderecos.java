package delete;

import br.com.rpinfo.analuisa.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class deleteEnderecos {

    public void deletarEndereco(int id) {
        String sql = "DELETE FROM enderecos WHERE id = ?";

        try (Connection connect = Conexao.conectar();
             PreparedStatement stmt = connect.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

            System.out.println("Endereço deletado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao deletar endereco!" + e.getMessage());
        }
    }
}
