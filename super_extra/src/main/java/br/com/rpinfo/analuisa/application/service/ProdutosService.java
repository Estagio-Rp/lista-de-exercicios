package br.com.rpinfo.analuisa.application.service;

import br.com.rpinfo.analuisa.domain.exceptions.CampoInvalidoException;
import br.com.rpinfo.analuisa.domain.exceptions.RegistroNaoEncontradoException;
import br.com.rpinfo.analuisa.domain.model.entity.Produto;
import br.com.rpinfo.analuisa.domain.repositories.produtos.ProdutosDAO;
import br.com.rpinfo.analuisa.domain.repositories.produtos.ProdutosDAOImpl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

public class ProdutosService extends ServiceBase {

    private final ProdutosDAO dao;

    public ProdutosService(Connection connection) {
        super(connection);
        this.dao = new ProdutosDAOImpl(connection);
    }

    public void cadastrarProduto(Produto produto) {
        validarProduto(produto);

        this.dao.cadastrar(produto);

        System.out.println("Produto cadastrado com sucesso!");
    }

    public List<Produto> listarProdutos() {
        return this.dao.listarTodos();
    }

    public void atualizarProduto(Produto produto) {
        validarId(produto.getId());

        if (!this.dao.existePorId(produto.getId())) {
            throw new RegistroNaoEncontradoException("Produto não encontrado.");
        }

        validarProduto(produto);
        this.dao.atualizar(produto);

        System.out.println("Produto atualizado com sucesso!");
    }

    public void deletarProduto(Integer id) {
        validarId(id);

        if (!this.dao.existePorId(id)) {
            throw new RegistroNaoEncontradoException("Produto não encontrado.");
        }

        this.dao.deletar(id);

        System.out.println("Produto deletado com sucesso!");
    }

    private void validarProduto(Produto produto) {
        if (produto.getNome() == null || produto.getNome().isBlank()) {
            throw new CampoInvalidoException("O nome do produto é obrigatório.");
        }

        String nome = produto.getNome().trim();

        if (nome.matches("\\d+")) {
            throw new CampoInvalidoException("O nome do produto não pode ser apenas numérico.");
        }
        String categoria = produto.getCategoria().trim();

        if (categoria.matches("\\d+")) {
            throw new CampoInvalidoException("A categoria do produto não pode ser numérica.");
        }

        if (produto.getPreco() == null || produto.getPreco().compareTo(BigDecimal.ZERO) < 0) {
            throw new CampoInvalidoException("O preço do produto não pode ser negativo.");
        }

        if (produto.getCategoria() == null || produto.getCategoria().isBlank()) {
            throw new CampoInvalidoException("A categoria do produto é obrigatória.");
        }

        if (produto.getEstoque() == null || produto.getEstoque() < 0) {
            throw new CampoInvalidoException("O estoque do produto não pode ser negativo.");
        }
    }

    private void validarId(Integer id) {
        if (id == null || id <= 0) {
            throw new CampoInvalidoException("ID inválido.");
        }
    }
}




