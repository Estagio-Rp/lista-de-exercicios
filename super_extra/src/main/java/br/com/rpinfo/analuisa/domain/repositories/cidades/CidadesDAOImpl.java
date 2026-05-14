package br.com.rpinfo.analuisa.domain.repositories.cidades;

import br.com.rpinfo.analuisa.domain.model.entity.Cidade;
import br.com.rpinfo.analuisa.domain.repositories.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CidadesDAOImpl extends DAOImpl implements CidadesDAO {

    public CidadesDAOImpl(Connection connection) {
        super(connection);
    }

    @Override
    public void cadastrar(Cidade cidade) {
        String sql = "INSERT INTO cidades (nome, uf) VALUES (?, ?)";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {

            stmt.setString(1, cidade.getNome());
            stmt.setString(2, cidade.getUf());

            stmt.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao cadastrar cidade: " + e.getMessage());
        }
    }

    @Override
    public List<Cidade> listarTodos() {

        String sql = "SELECT id, nome, uf FROM cidades ORDER BY id";

        List<Cidade> cidades = new ArrayList<>();

        try (PreparedStatement stmt = getConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cidade cidade = new Cidade(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("uf")
                );

                cidades.add(cidade);
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar cidades: " + e.getMessage());
        }

        return cidades;
    }

    @Override
    public Cidade buscarPorId(Integer id) {
        String sql = "SELECT id, nome, uf FROM cidades WHERE id = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Cidade(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("uf")
                    );
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar cidade: " + e.getMessage());
        }

        return null;
    }

    @Override
    public boolean existePorId(Integer id) {
        return buscarPorId(id) != null;
    }

    @Override
    public void atualizar(Cidade cidade) {
        String sql = "UPDATE cidades SET nome = ?, uf = ? WHERE id = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {

            stmt.setString(1, cidade.getNome());
            stmt.setString(2, cidade.getUf());
            stmt.setInt(3, cidade.getId());

            stmt.executeUpdate();


        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar cidade: " + e.getMessage());
        }
    }

    @Override
    public void deletar(Integer id) {
        String desvincularEnderecos = "UPDATE enderecos SET cida_id = NULL WHERE cida_id = ?";
        String deletarCidade = "DELETE FROM cidades WHERE id = ?";

        try (PreparedStatement stmtDesvincular = getConnection().prepareStatement(desvincularEnderecos);
             PreparedStatement stmtDeletar = getConnection().prepareStatement(deletarCidade)) {

            stmtDesvincular.setInt(1, id);
            stmtDesvincular.executeUpdate();

            stmtDeletar.setInt(1, id);
            stmtDeletar.executeUpdate();


        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar cidade: " + e.getMessage());
        }
    }
}