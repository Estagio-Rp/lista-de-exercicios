package br.com.rpinfo.analuisa.domain.repositories.enderecos;

import br.com.rpinfo.analuisa.Conexao;
import br.com.rpinfo.analuisa.domain.model.entity.Endereco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EnderecosDAOImpl implements EnderecosDAO {

    @Override
    public void cadastrar(Endereco endereco) {
        String sql = "INSERT INTO enderecos(cep, rua, numero, complemento, bairro, cida_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connect = Conexao.conectar();
             PreparedStatement stmt = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, endereco.getCep());
            stmt.setString(2, endereco.getRua());
            stmt.setObject(3, endereco.getNumero());
            stmt.setString(4, endereco.getComplemento());
            stmt.setString(5, endereco.getBairro());
            stmt.setObject(6, endereco.getCidadeId());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    endereco.setId(rs.getInt(1));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao cadastrar endereço: " + e.getMessage(), e);
        }

    }

    @Override
    public List<Endereco> listarTodos() {
        String sql = "SELECT id, cep, rua, numero, complemento, bairro, cida_id FROM enderecos ORDER BY id";

        List<Endereco> enderecos = new ArrayList<>();

        try (Connection connect = Conexao.conectar();
             PreparedStatement stmt = connect.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Endereco endereco = new Endereco(
                        rs.getInt("id"),
                        rs.getString("cep"),
                        rs.getString("rua"),
                        rs.getObject("numero", Integer.class),
                        rs.getString("complemento"),
                        rs.getString("bairro"),
                        rs.getObject("cida_id", Integer.class)
                );
                enderecos.add(endereco);
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar endereços: " + e.getMessage(), e);
        }
        return enderecos;
    }

    @Override
    public Endereco buscarPorId(Integer id) {
        String sql = "SELECT id, cep, rua, numero, complemento, bairro, cida_id FROM enderecos WHERE id = ?";

        try (Connection connect = Conexao.conectar();
             PreparedStatement stmt = connect.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Endereco(
                            rs.getInt("id"),
                            rs.getString("cep"),
                            rs.getString("rua"),
                            rs.getObject("numero", Integer.class),
                            rs.getString("complemento"),
                            rs.getString("bairro"),
                            rs.getObject("cida_id", Integer.class)
                    );
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar endereço por ID: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public boolean existePorId(Integer id) {
        return buscarPorId(id) != null;
    }

    @Override
    public void atualizar(Endereco endereco) {
        String sql = "UPDATE enderecos SET cep = ?, rua = ?, numero = ?, complemento = ?, bairro = ?, cida_id = ? WHERE id = ?";

        try (Connection connect = Conexao.conectar();
             PreparedStatement stmt = connect.prepareStatement(sql)) {

            stmt.setString(1, endereco.getCep());
            stmt.setString(2, endereco.getRua());
            stmt.setObject(3, endereco.getNumero());
            stmt.setString(4, endereco.getComplemento());
            stmt.setString(5, endereco.getBairro());
            stmt.setObject(6, endereco.getCidadeId());
            stmt.setInt(7, endereco.getId());

            stmt.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar endereço: " + e.getMessage());
        }
    }

    @Override
    public void deletar(Integer id) {
        String desvincularClientes = "UPDATE clientes SET ende_id = NULL WHERE ende_id = ?";
        String deletarEndereco = "DELETE FROM enderecos WHERE id = ?";

        try (Connection connect = Conexao.conectar()) {
            connect.setAutoCommit(false);

            try (PreparedStatement stmtDesvincular = connect.prepareStatement(desvincularClientes);
                 PreparedStatement stmtDeletar = connect.prepareStatement(deletarEndereco)) {

                stmtDesvincular.setInt(1, id);
                stmtDesvincular.executeUpdate();

                stmtDeletar.setInt(1, id);
                stmtDeletar.executeUpdate();

                connect.commit();

            }catch (Exception e) {
                connect.rollback();
                throw e;
            }

        }catch (Exception e) {
            throw new RuntimeException("Erro ao deletar endereço: " + e.getMessage());
        }
    }
}
