package insert;

import br.com.rpinfo.analuisa.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddCidades {

    public void cadastrarCidade(int id, String nome, String uf) {
        String sql = "INSERT INTO cidades (id, nome, uf) VALUES (?, ?, ?)";

        try (Connection connect = Conexao.conectar();
             PreparedStatement stmt = connect.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.setString(2, nome);
            stmt.setString(3, uf);

            stmt.executeUpdate();

            System.out.println("Cidade cadastrada com sucesso!");
        }catch (Exception e){
            System.out.println("Erro ao cadastrar cidade!" + e.getMessage());
        }


    }
}
