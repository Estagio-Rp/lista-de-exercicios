package update;

import br.com.rpinfo.analuisa.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UpdateClientes {

    public void atualizarNome(int id, String nome) {
        String sql = "UPDATE clientes SET nome = ? WHERE id = ?";

        try (Connection connect = Conexao.conectar();
             PreparedStatement stmt = connect.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setInt(2, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("\nNome do cliente atualizado com sucesso!");
            } else {
                System.out.println("\nNenhum cliente encontrado com esse ID.");
            }

        } catch (Exception e) {
            System.out.println("\nErro ao atualizar nome do cliente: " + e.getMessage());
        }
    }

    public void atualizarEmail(int id, String email) {
        String sql = "UPDATE clientes SET email = ? WHERE id = ?";

        try (Connection connect = Conexao.conectar();
             PreparedStatement stmt = connect.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setInt(2, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("\nEmail atualizado com sucesso!");
            } else {
                System.out.println("\nNenhum cliente encontrado com esse ID.");
            }

        } catch (Exception e) {
            System.out.println("\nErro ao atualizar email: " + e.getMessage());
        }
    }

    public void atualizarTelefone(int id, String telefone) {
        String sql = "UPDATE clientes SET telefone = ? WHERE id = ?";

        try (Connection connect = Conexao.conectar();
             PreparedStatement stmt = connect.prepareStatement(sql)) {

            stmt.setString(1, telefone);
            stmt.setInt(2, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("\nTelefone atualizado com sucesso!");
            } else {
                System.out.println("\nNenhum cliente encontrado com esse ID.");
            }

        } catch (Exception e) {
            System.out.println("\nErro ao atualizar telefone: " + e.getMessage());
        }
    }

    public void atualizarEnderecoId(int id, int endeId) {
        String sql = "UPDATE clientes SET ende_id = ? WHERE id = ?";

        try (Connection connect = Conexao.conectar();
             PreparedStatement stmt = connect.prepareStatement(sql)) {

            stmt.setInt(1, endeId);
            stmt.setInt(2, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("\nEndereço do cliente atualizado com sucesso!");
            } else {
                System.out.println("\nNenhum cliente encontrado com esse ID.");
            }

        } catch (Exception e) {
            System.out.println("\nErro ao atualizar endereço do cliente: " + e.getMessage());
        }
    }
}