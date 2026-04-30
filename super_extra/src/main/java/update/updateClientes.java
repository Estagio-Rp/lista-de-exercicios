package update;

import br.com.rpinfo.analuisa.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class updateClientes {

    public void atualizarEmail(int id, String email) {
        String sql = "UPDATE clientes SET email = ? WHERE id = ?";

        try (Connection connect = Conexao.conectar();
             PreparedStatement stmt = connect.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setInt(2, id);

            stmt.executeUpdate();

            System.out.println("Email atualizado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar email:" + e.getMessage());
        }
    }


    public void atualizarTelefone(int id, String telefone) {
        String sql = "UPDATE clientes SET telefone = ? WHERE id = ?";

        try (Connection connect = Conexao.conectar();
             PreparedStatement stmt = connect.prepareStatement(sql)) {

            stmt.setString(1, telefone);
            stmt.setInt(2, id);

            stmt.executeUpdate();

            System.out.println("Telefone atualizado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar telefone:" + e.getMessage());
        }
    }


    public void atualizarEnderecoId(int id, int endeId) {
        String sql = "UPDATE clientes SET ende_id = ? WHERE id = ?";

        try (Connection connect = Conexao.conectar();
             PreparedStatement stmt = connect.prepareStatement(sql)) {

            stmt.setInt(1, endeId);
            stmt.setInt(2, id);

            stmt.executeUpdate();

            System.out.println("Endereco atualizado com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao atualizar endereco:" + e.getMessage());
        }
    }
}

