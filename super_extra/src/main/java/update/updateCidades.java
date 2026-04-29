package update;

import br.com.rpinfo.analuisa.Conexao;

import java.io.PipedReader;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class updateCidades {

    public void atualizarNome(int id, String nome) {
        String sql = "UPDATE cidades SET nome = ? WHERE id = ?";

        try (Connection connect = Conexao.conectar();
             PreparedStatement stmt = connect.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.setString(2, nome);

            stmt.executeUpdate();


            System.out.println("Nome da cidade atualizado com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao atualizar nome da cidade" + e.getMessage());
        }
    }

    public void atualizarUf(int id, String uf) {
        String sql = "UPDATE cidades set uf = ? WHERE id = ?";

        try (Connection connect = Conexao.conectar();
             PreparedStatement stmt = connect.prepareStatement(sql)){

            stmt.setInt(1, id);
            stmt.setString(2, uf);

            stmt.executeUpdate();

            System.out.println("UF atualiziado com sucesso!");

        }catch (Exception e){
            System.out.println("Erro ao atualizar UF" + e.getMessage());
        }
    }
}
