package update;

import br.com.rpinfo.analuisa.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UpdateCidades {

    public void atualizarNome(int id, String nome) {
        String sql = "UPDATE cidades SET nome = ? WHERE id = ?";

        try (Connection connect = Conexao.conectar();
             PreparedStatement stmt = connect.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setInt(2, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("\nNome da cidade atualizado com sucesso!");
            } else {
                System.out.println("\nNenhuma cidade encontrada com esse ID.");
            }

        } catch (Exception e) {
            System.out.println("\nErro ao atualizar nome da cidade: " + e.getMessage());
        }
    }

    public void atualizarUf(int id, String uf) {
        String sql = "UPDATE cidades SET uf =?  WHERE id = ?";

        try (Connection connect = Conexao.conectar();
             PreparedStatement stmt = connect.prepareStatement(sql)) {
            stmt.setString(1, uf);
            stmt.setInt(2, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("\nUF atualizada com sucesso!");
            } else {
                System.out.println("\nNenhuma UF atualizada com esse ID.");
            }

        } catch (Exception e) {
            System.out.println("\nErro ao atualizar UF: " + e.getMessage());
        }
    }
}