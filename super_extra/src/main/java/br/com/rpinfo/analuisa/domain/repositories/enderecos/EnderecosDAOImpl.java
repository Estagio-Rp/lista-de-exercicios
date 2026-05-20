//package br.com.rpinfo.analuisa.domain.repositories.enderecos;
//
//import br.com.rpinfo.analuisa.domain.model.entity.Endereco;
//import br.com.rpinfo.analuisa.domain.repositories.DAOImpl;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class EnderecosDAOImpl extends DAOImpl implements EnderecosDAO {
//
//    public EnderecosDAOImpl(Connection connection) {
//        super(connection);
//    }
//
//    @Override
//    public void cadastrar(Endereco endereco) {
//        String sql = "INSERT INTO enderecos (cep, rua, numero, complemento, bairro, cida_id) VALUES (?, ?, ?, ?, ?, ?)";
//
//        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
//
//            stmt.setString(1, endereco.getCep());
//            stmt.setString(2, endereco.getRua());
//            stmt.setInt(3, endereco.getNumero());
//            stmt.setString(4, endereco.getComplemento());
//            stmt.setString(5, endereco.getBairro());
//            stmt.setObject(6, endereco.getCidadeId());
//
//            stmt.executeUpdate();
//
//
//        } catch (Exception e) {
//            throw new RuntimeException("Erro ao cadastrar endereço: " + e.getMessage());
//        }
//    }
//
//    @Override
//    public List<Endereco> listarTodos() {
//
//        String sql = "SELECT id, cep, rua, numero, complemento, bairro, cida_id FROM enderecos ORDER BY id";
//
//        List<Endereco> enderecos = new ArrayList<>();
//
//        try (PreparedStatement stmt = getConnection().prepareStatement(sql);
//             ResultSet rs = stmt.executeQuery()) {
//
//            while (rs.next()) {
//                enderecos.add(mapearEndereco(rs));
//            }
//
//        } catch (Exception e) {
//            throw new RuntimeException("Erro ao listar endereços: " + e.getMessage());
//        }
//
//        return enderecos;
//    }
//
//    @Override
//    public Endereco buscarPorId(Integer id) {
//        String sql = "SELECT id, cep, rua, numero, complemento, bairro, cida_id FROM enderecos WHERE id = ?";
//
//        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
//
//            stmt.setInt(1, id);
//
//            try (ResultSet rs = stmt.executeQuery()) {
//                if (rs.next()) {
//                    return mapearEndereco(rs);
//                }
//            }
//
//        } catch (Exception e) {
//            throw new RuntimeException("Erro ao buscar endereço: " + e.getMessage());
//        }
//
//        return null;
//    }
//
//    @Override
//    public boolean existePorId(Integer id) {
//        return buscarPorId(id) != null;
//    }
//
//    @Override
//    public void atualizar(Endereco endereco) {
//        String sql = "UPDATE enderecos SET cep = ?, rua = ?, numero = ?, complemento = ?, bairro = ?, cida_id = ? WHERE id = ?";
//
//        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
//
//            stmt.setString(1, endereco.getCep());
//            stmt.setString(2, endereco.getRua());
//            stmt.setInt(3, endereco.getNumero());
//            stmt.setString(4, endereco.getComplemento());
//            stmt.setString(5, endereco.getBairro());
//            stmt.setObject(6, endereco.getCidadeId());
//            stmt.setInt(7, endereco.getId());
//
//            stmt.executeUpdate();
//
//
//        } catch (Exception e) {
//            throw new RuntimeException("Erro ao atualizar endereço: " + e.getMessage());
//        }
//    }
//
//    @Override
//    public void deletar(Integer id) {
//        String desvincularClientes = "UPDATE clientes SET ende_id = NULL WHERE ende_id = ?";
//        String deletarEndereco = "DELETE FROM enderecos WHERE id = ?";
//
//        try (PreparedStatement stmtDesvincular = getConnection().prepareStatement(desvincularClientes);
//             PreparedStatement stmtDeletar = getConnection().prepareStatement(deletarEndereco)) {
//
//            stmtDesvincular.setInt(1, id);
//            stmtDesvincular.executeUpdate();
//
//            stmtDeletar.setInt(1, id);
//            stmtDeletar.executeUpdate();
//
//
//        } catch (Exception e) {
//            throw new RuntimeException("Erro ao deletar endereço: " + e.getMessage());
//        }
//    }
//
//    private Endereco mapearEndereco(ResultSet rs) throws SQLException {
//        return new Endereco(
//                rs.getInt("id"),
//                rs.getString("cep"),
//                rs.getString("rua"),
//                rs.getInt("numero"),
//                rs.getString("complemento"),
//                rs.getString("bairro"),
//                rs.getObject("cida_id", Integer.class)
//        );
//    }
//}