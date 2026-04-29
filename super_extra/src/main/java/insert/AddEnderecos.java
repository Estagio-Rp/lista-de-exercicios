package insert;

import br.com.rpinfo.analuisa.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddEnderecos {

    public void cadastrarEndereco(int id, String cep, String rua, int numero, String complemento, String bairro, int cidaId) {
        String sql = "INSERT INTO enderecos (id, cep, rua, numero, complemento, bairro, cida_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connect = Conexao.conectar();
             PreparedStatement stmt = connect.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.setString(2, cep);
            stmt.setString(3, rua);
            stmt.setInt(4, numero);
            stmt.setString(5, complemento);
            stmt.setString(6, bairro);
            stmt.setInt(7, cidaId);

            stmt.executeUpdate();

            System.out.println("Endereço cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar endereço!" + e.getMessage());
        }
    }
}
