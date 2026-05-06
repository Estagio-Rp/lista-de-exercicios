package insert;

import br.com.rpinfo.analuisa.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class AddCidades {

    public void cadastrarCidade(String nome, String uf) {
        String sql = "INSERT INTO cidades (nome, uf) VALUES (?, ?)";

        try (Connection connect = Conexao.conectar();
             PreparedStatement stmt = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, nome);
            stmt.setString(2, uf);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        System.out.println("\nCidade cadastrada com sucesso! ID gerado: " + rs.getInt(1));
                    } else {
                        System.out.println("\nCidade cadastrada com sucesso!");
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("\nErro ao cadastrar cidade: " + e.getMessage());
        }
    }
}