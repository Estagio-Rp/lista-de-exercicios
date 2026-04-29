package update;

import br.com.rpinfo.analuisa.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class updateEnderecos {

    public void atualizarRua(int id, String rua) {
        String sql = "UPDATE enderecos SET rua = ? WHERE id = ?";

        try (Connection connect = Conexao.conectar();
             PreparedStatement stmt = connect.prepareStatement(sql)) {

            stmt.setString(1, rua);
            stmt.setInt(2, id);

            stmt.executeUpdate();

            System.out.println("Rua atualizada com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao atualizar rua: " + e.getMessage());
        }
    }

    public void atualizarNumero(int id, int numero) {
        String sql = "UPDATE enderecos SET numero = ? WHERE id = ?";

        try (Connection connect = Conexao.conectar();
             PreparedStatement stmt = connect.prepareStatement(sql)) {

            stmt.setInt(1, numero);
            stmt.setInt(2, id);

            stmt.executeUpdate();

            System.out.println("Número atualizado com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao atualizar número: " + e.getMessage());
        }
    }

    public void atualizarComplemento(int id, String complemento) {
        String sql = "UPDATE enderecos SET complemento = ? WHERE id = ?";

        try (Connection connect = Conexao.conectar();
             PreparedStatement stmt = connect.prepareStatement(sql)) {

            stmt.setString(1, complemento);
            stmt.setInt(2, id);

            stmt.executeUpdate();

            System.out.println("Complemento atualizado com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao atualizar complemento: " + e.getMessage());
        }
    }

    public void atualizarBairro(int id, String bairro) {
        String sql = "UPDATE enderecos SET bairro = ? WHERE id = ?";

        try (Connection connect = Conexao.conectar();
             PreparedStatement stmt = connect.prepareStatement(sql)) {

            stmt.setString(1, bairro);
            stmt.setInt(2, id);

            stmt.executeUpdate();

            System.out.println("Bairro atualizado com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao atualizar bairro: " + e.getMessage());
        }
    }
}