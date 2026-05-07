package update;

import br.com.rpinfo.analuisa.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UpdateProdutos {

    public void atualizarCategoria(int id, String categoria) {
        String sql = "UPDATE produtos SET categoria = ? WHERE id = ?";
        executarUpdateTexto(sql, categoria, id, "Categoria atualizada com sucesso!", "Erro ao atualizar categoria");
    }

    public void atualizarEstoque(int id, int estoque) {
        String sql = "UPDATE produtos SET estoque = ? WHERE id = ?";

        try (Connection connect = Conexao.conectar();
             PreparedStatement stmt = connect.prepareStatement(sql)) {

            stmt.setInt(1, estoque);
            stmt.setInt(2, id);

            int linhasAfetadas = stmt.executeUpdate();
            mostrarResultado(linhasAfetadas, "Estoque atualizado com sucesso!");

        } catch (Exception e) {
            System.out.println("\nErro ao atualizar estoque: " + e.getMessage());
        }
    }

    public void atualizarPreco(int id, double preco) {
        String sql = "UPDATE produtos SET preco = ? WHERE id = ?";

        try (Connection connect = Conexao.conectar();
             PreparedStatement stmt = connect.prepareStatement(sql)) {

            stmt.setDouble(1, preco);
            stmt.setInt(2, id);

            int linhasAfetadas = stmt.executeUpdate();
            mostrarResultado(linhasAfetadas, "Preço atualizado com sucesso!");

        } catch (Exception e) {
            System.out.println("\nErro ao atualizar preço: " + e.getMessage());
        }
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
            System.out.println("\nNenhum produto encontrado com esse ID.");
        }
    }
}