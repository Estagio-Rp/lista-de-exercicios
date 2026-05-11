package br.com.rpinfo.analuisa.domain.repositories.clientes;

import br.com.rpinfo.analuisa.Conexao;
import br.com.rpinfo.analuisa.domain.model.entity.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ClientesDAOImpl implements ClientesDAO {

    @Override
    public void cadastrar(Cliente cliente) {
        String sql = "INSERT INTO clientes (nome, email, cpf, telefone, data_cadastro, ende_id) " +
                "VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP, ?)";

        try (Connection connect = Conexao.conectar();
             PreparedStatement stmt = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getCpf());
            stmt.setString(4, cliente.getTelefone());
            stmt.setObject(5, cliente.getEnderecoId());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    cliente.setId(rs.getInt(1));
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    @Override
    public List<Cliente> listarTodos() {
        String sql = "SELECT id, nome, email, cpf, telefone, data_cadastro, ende_id FROM clientes ORDER BY id";

        List<Cliente> clientes = new ArrayList<>();

        try (Connection connect = Conexao.conectar();
             PreparedStatement stmt = connect.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                clientes.add(mapearCliente(rs));
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar clientes: " + e.getMessage());
        }

        return clientes;
    }

    @Override
    public Cliente buscarPorId(Integer id) {
        String sql = "SELECT id, nome, email, cpf, telefone, data_cadastro, ende_id FROM clientes WHERE id = ?";

        try (Connection connect = Conexao.conectar();
             PreparedStatement stmt = connect.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearCliente(rs);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar cliente: " + e.getMessage());
        }

        return null;
    }

    @Override
    public boolean existePorId(Integer id) {
        return buscarPorId(id) != null;
    }

    @Override
    public void atualizar(Cliente cliente) {
        String sql = "UPDATE clientes SET nome = ?, email = ?, cpf = ?, telefone = ?, ende_id = ? WHERE id = ?";

        try (Connection connect = Conexao.conectar();
             PreparedStatement stmt = connect.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getCpf());
            stmt.setString(4, cliente.getTelefone());
            stmt.setObject(5, cliente.getEnderecoId());
            stmt.setInt(6, cliente.getId());

            stmt.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar cliente: " + e.getMessage());
        }
    }

    @Override
    public void deletar(Integer id) {
        String sql = "DELETE FROM clientes WHERE id = ?";

        try (Connection connect = Conexao.conectar();
             PreparedStatement stmt = connect.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar cliente: " + e.getMessage());
        }
    }

    private Cliente mapearCliente(ResultSet rs) throws Exception {
        Timestamp dataCadastro = rs.getTimestamp("data_cadastro");

        return new Cliente(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("email"),
                rs.getString("cpf"),
                rs.getString("telefone"),
                dataCadastro == null ? null : dataCadastro.toLocalDateTime(),
                rs.getObject("ende_id", Integer.class)
        );
    }
}
