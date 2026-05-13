package br.com.rpinfo.analuisa.domain.repositories.produtos;

import br.com.rpinfo.analuisa.domain.model.entity.Produto;
import br.com.rpinfo.analuisa.domain.repositories.DAOImpl;
import br.com.rpinfo.analuisa.shared.SequenceUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutosDAOImpl extends DAOImpl implements ProdutosDAO {

    public ProdutosDAOImpl(Connection connection) {
        super(connection);
    }

    @Override
    public void cadastrar(Produto produto) {
        String sql = "INSERT INTO produtos (nome, preco, categoria, estoque) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {

            stmt.setString(1, produto.getNome());
            stmt.setBigDecimal(2, produto.getPreco());
            stmt.setString(3, produto.getCategoria());
            stmt.setInt(4, produto.getEstoque());

            stmt.executeUpdate();

            SequenceUtils.reorganizarIdsProdutos(getConnection());

        } catch (Exception e) {
            throw new RuntimeException("Erro ao cadastrar produto: " + e.getMessage());
        }
    }

    @Override
    public List<Produto> listarTodos() {
        SequenceUtils.reorganizarIdsProdutos(getConnection());

        String sql = "SELECT id, nome, preco, categoria, estoque FROM produtos ORDER BY id";

        List<Produto> produtos = new ArrayList<>();

        try (PreparedStatement stmt = getConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                produtos.add(mapearProduto(rs));
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar produtos: " + e.getMessage());
        }

        return produtos;
    }

    @Override
    public Produto buscarPorId(Integer id) {
        String sql = "SELECT id, nome, preco, categoria, estoque FROM produtos WHERE id = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearProduto(rs);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar produto: " + e.getMessage());
        }

        return null;
    }

    @Override
    public boolean existePorId(Integer id) {
        return buscarPorId(id) != null;
    }

    @Override
    public void atualizar(Produto produto) {
        String sql = "UPDATE produtos SET nome = ?, preco = ?, categoria = ?, estoque = ? WHERE id = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {

            stmt.setString(1, produto.getNome());
            stmt.setBigDecimal(2, produto.getPreco());
            stmt.setString(3, produto.getCategoria());
            stmt.setInt(4, produto.getEstoque());
            stmt.setInt(5, produto.getId());

            stmt.executeUpdate();

            SequenceUtils.reorganizarIdsProdutos(getConnection());

        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar produto: " + e.getMessage());
        }
    }

    @Override
    public void deletar(Integer id) {
        String sql = "DELETE FROM produtos WHERE id = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

            SequenceUtils.reorganizarIdsProdutos(getConnection());

        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar produto: " + e.getMessage());
        }
    }

    private Produto mapearProduto(ResultSet rs) throws SQLException {
        return new Produto(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getBigDecimal("preco"),
                rs.getString("categoria"),
                rs.getInt("estoque")
        );
    }
}