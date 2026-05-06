package insert;

import br.com.rpinfo.analuisa.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class AddClientes {

    public void cadastrarCliente(String nome, String email, String cpf, String telefone, int endeId) {
        String sql = "INSERT INTO clientes (nome, email, cpf, telefone, data_cadastro, ende_id) VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP, ?)";

        try (Connection connect = Conexao.conectar();
             PreparedStatement stmt = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.setString(3, cpf);
            stmt.setString(4, telefone);
            stmt.setInt(5, endeId);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        System.out.println("\nCliente cadastrado com sucesso! ID gerado: " + rs.getInt(1));
                    } else {
                        System.out.println("\nCliente cadastrado com sucesso!");
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("\nErro ao cadastrar cliente: " + e.getMessage());
        }
    }
}