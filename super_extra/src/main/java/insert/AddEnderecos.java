package insert;

import br.com.rpinfo.analuisa.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class AddEnderecos {

    public void cadastrarEndereco(String cep, String rua, int numero, String complemento, String bairro, int cidaId) {
        String sql = "INSERT INTO enderecos (cep, rua, numero, complemento, bairro, cida_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connect = Conexao.conectar();
             PreparedStatement stmt = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, cep);
            stmt.setString(2, rua);
            stmt.setInt(3, numero);
            stmt.setString(4, complemento);
            stmt.setString(5, bairro);
            stmt.setInt(6, cidaId);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        System.out.println("\nEndereço cadastrado com sucesso! ID gerado: " + rs.getInt(1));
                    } else {
                        System.out.println("\nEndereço cadastrado com sucesso!");
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("\nErro ao cadastrar endereço: " + e.getMessage());
        }
    }
}