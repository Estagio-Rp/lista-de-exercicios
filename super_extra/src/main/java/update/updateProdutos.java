package update;

import br.com.rpinfo.analuisa.Conexao;
import org.postgresql.core.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class updateProdutos {
    public void atualizarCategoria(int id, String categoria) {

        String sql = "UPDATE produtos SET categoria = ? WHERE id = ?";

        try (Connection connect = Conexao.conectar();
             PreparedStatement stmt = connect.prepareStatement(sql)) {

            stmt.setString(1, categoria);
            stmt.setInt(2, id);

            stmt.executeUpdate();

            System.out.println("Categoria atualizado com sucesso!");
        }catch (Exception e){
            System.out.println("Erro ao atualizar categoria:" + e.getMessage());
        }

    }


    public void atualizarEstoque(int id, int estoque) {
        String sql = "UPDATE produtos SET estoque = ? WHERE id = ?";

        try (Connection connect = Conexao.conectar();
        PreparedStatement stmt = connect.prepareStatement(sql)){

            stmt.setInt(1, estoque);
            stmt.setInt(2, id);

            stmt.executeUpdate();

            System.out.println("Estoque atualizado com sucesso!");

        }catch (Exception e){
            System.out.println("Erro ao atualizar estoque:" + e.getMessage());
        }
    }


    public void atualizarPreco(int id, double preco) {

        String sql = "UPDATE produtos SET preco = ? WHERE id = ?";

        try(Connection connect = Conexao.conectar();
        PreparedStatement stmt = connect.prepareStatement(sql)){

            stmt.setDouble(1, preco);
            stmt.setInt(2, id);

            stmt.executeUpdate();
            System.out.println("Preco atualizado com sucesso!");

        }catch (Exception e){
            System.out.println("Erro ao atualizar preco:" + e.getMessage());
        }
    }
}
