package insert;

import br.com.rpinfo.analuisa.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddClientes {

    public void cadastrarCliente(int id, String nome, String email, String cpf, String telefone, int endeId) {

        String sql = "INSERT INTO clientes (id, nome, email, cpf, telefone, data_cadastro, ende_id) VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?)";

        try (Connection connect = Conexao.conectar();
             PreparedStatement stmt = connect.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.setString(2, nome);
            stmt.setString(3, email);
            stmt.setString(4, cpf);
            stmt.setString(5, telefone);
            stmt.setInt(6, endeId);

            stmt.executeUpdate();

            System.out.println("Cliente cadastrado com sucesso!");
        }catch (Exception e){
            System.out.println("Erro ao cadastrar cliente!" + e.getMessage());
        }
    }
}
