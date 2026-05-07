package update;

import br.com.rpinfo.analuisa.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UpdateEnderecos {

    public void atualizarRua(int id, String rua) {
        String sql = "UPDATE enderecos SET rua = ? WHERE id = ?";
        executarUpdateTexto(sql, rua, id, "Rua atualizada com sucesso!", "Erro ao atualizar rua");
    }

    public void atualizarNumero(int id, int numero) {
        String sql = "UPDATE enderecos SET numero = ? WHERE id = ?";

        try (Connection connect = Conexao.conectar();
             PreparedStatement stmt = connect.prepareStatement(sql)) {

            stmt.setInt(1, numero);
            stmt.setInt(2, id);

            int linhasAfetadas = stmt.executeUpdate();
            mostrarResultado(linhasAfetadas, "Número atualizado com sucesso!");

        } catch (Exception e) {
            System.out.println("\nErro ao atualizar número: " + e.getMessage());
        }
    }

    public void atualizarComplemento(int id, String complemento) {
        String sql = "UPDATE enderecos SET complemento = ? WHERE id = ?";
        executarUpdateTexto(sql, complemento, id, "Complemento atualizado com sucesso!", "Erro ao atualizar complemento");
    }

    public void atualizarBairro(int id, String bairro) {
        String sql = "UPDATE enderecos SET bairro = ? WHERE id = ?";
        executarUpdateTexto(sql, bairro, id, "Bairro atualizado com sucesso!", "Erro ao atualizar bairro");
    }

    private void executarUpdateTexto(String sql, String valor, int id, String mensagemSucesso, String mensagemErro) {
        try (Connection connect = Conexao.conectar();
             PreparedStatement stmt = connect.prepareStatement(sql)) {

            stmt.setString(1, valor);
            stmt.setInt(2, id);

            int linhasAfetadas = stmt.executeUpdate();
            mostrarResultado(linhasAfetadas, mensagemSucesso);

        } catch (Exception e) {
            System.out.println("\n" + mensagemErro + ": " + e.getMessage());
        }
    }

    private void mostrarResultado(int linhasAfetadas, String mensagemSucesso) {
        if (linhasAfetadas > 0) {
            System.out.println("\n" + mensagemSucesso);
        } else {
            System.out.println("\nNenhum endereço encontrado com esse ID.");
        }
    }
}