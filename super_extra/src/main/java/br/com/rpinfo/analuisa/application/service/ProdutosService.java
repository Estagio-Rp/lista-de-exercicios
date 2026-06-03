package br.com.rpinfo.analuisa.application.service;

import br.com.rpinfo.analuisa.application.dto.produtos.ProdutosDTO;
import br.com.rpinfo.analuisa.domain.exceptions.CampoInvalidoException;
import br.com.rpinfo.analuisa.domain.exceptions.RegistroNaoEncontradoException;
import br.com.rpinfo.analuisa.domain.model.entity.Produto;
import br.com.rpinfo.analuisa.domain.repositories.produtos.ProdutosDAO;
import br.com.rpinfo.analuisa.shared.DocumentoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutosService {

    private final ProdutosDAO produtosDAO;

    @Autowired
    private DocumentoUtils documentoUtils;

    public ProdutosService(ProdutosDAO produtosDAO) {
        this.produtosDAO = produtosDAO;
    }

    @Transactional
    public boolean inserirProduto(ProdutosDTO dto) {
        Produto produto = dto.toEntity();

        validarProduto(produto);

        Produto produtoSalvo = produtosDAO.save(produto);

        documentoUtils.gravaLog(1, "Cadastro de novo produto");

        return produtoSalvo.getId() != null;
    }

    public List<ProdutosDTO> listarProdutos() {
        List<ProdutosDTO> produtos = produtosDAO.findAllByOrderByIdAsc()
                .stream()
                .map(ProdutosDTO::fromEntity)
                .collect(Collectors.toList());

        documentoUtils.gravaLog(2, "Consulta de todos os produtos");

        return produtos;
    }

    public ProdutosDTO buscarPorId(Integer id) {
        validarId(id);

        Produto produto = produtosDAO.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Produto não encontrado."));

        documentoUtils.gravaLog(2, "Consulta de produto específico por ID: " + id);

        return ProdutosDTO.fromEntity(produto);
    }

    @Transactional
    public ProdutosDTO atualizarProduto(Integer id, ProdutosDTO dto) {
        validarId(id);

        Produto produtoAtual = produtosDAO.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Produto não encontrado."));

        if (dto.getProdNome() != null) {
            if (dto.getProdNome().isBlank()) {
                throw new CampoInvalidoException("O nome do produto é obrigatório.");
            }

            String nome = dto.getProdNome().trim();

            if (nome.matches("\\d+")) {
                throw new CampoInvalidoException("O nome do produto não pode ser apenas numérico.");
            }

            produtoAtual.setNome(nome);
        }

        if (dto.getProdPreco() != null) {
            if (dto.getProdPreco().compareTo(BigDecimal.ZERO) < 0) {
                throw new CampoInvalidoException("O preço do produto não pode ser negativo.");
            }

            produtoAtual.setPreco(dto.getProdPreco());
        }

        if (dto.getProdCategoria() != null) {
            if (dto.getProdCategoria().isBlank()) {
                throw new CampoInvalidoException("A categoria do produto é obrigatória.");
            }

            String categoria = dto.getProdCategoria().trim();

            if (categoria.matches("\\d+")) {
                throw new CampoInvalidoException("A categoria do produto não pode ser apenas numérica.");
            }

            produtoAtual.setCategoria(categoria);
        }

        if (dto.getProdEstoque() != null) {
            if (dto.getProdEstoque() < 0) {
                throw new CampoInvalidoException("O estoque do produto não pode ser negativo.");
            }

            produtoAtual.setEstoque(dto.getProdEstoque());
        }

        Produto produtoSalvo = produtosDAO.save(produtoAtual);

        documentoUtils.gravaLog(3, "Atualização de produto específico por ID: " + id);

        return ProdutosDTO.fromEntity(produtoSalvo);
    }

    @Transactional
    public boolean deletarProduto(Integer id) {
        validarId(id);

        if (!produtosDAO.existsById(id)) {
            throw new RegistroNaoEncontradoException("Produto não encontrado.");
        }

        produtosDAO.deleteById(id);

        documentoUtils.gravaLog(4, "Exclusão de produto por ID: " + id);

        return true;
    }

    private void validarProduto(Produto produto) {
        if (produto.getNome() == null || produto.getNome().isBlank()) {
            throw new CampoInvalidoException("O nome do produto é obrigatório.");
        }

        String nome = produto.getNome().trim();

        if (nome.matches("\\d+")) {
            throw new CampoInvalidoException("O nome do produto não pode ser apenas numérico.");
        }

        if (produto.getPreco() == null || produto.getPreco().compareTo(BigDecimal.ZERO) < 0) {
            throw new CampoInvalidoException("O preço do produto não pode ser negativo.");
        }

        if (produto.getCategoria() == null || produto.getCategoria().isBlank()) {
            throw new CampoInvalidoException("A categoria do produto é obrigatória.");
        }

        String categoria = produto.getCategoria().trim();

        if (categoria.matches("\\d+")) {
            throw new CampoInvalidoException("A categoria do produto não pode ser apenas numérica.");
        }

        if (produto.getEstoque() == null || produto.getEstoque() < 0) {
            throw new CampoInvalidoException("O estoque do produto não pode ser negativo.");
        }

        produto.setNome(nome);
        produto.setCategoria(categoria);
    }

    private void validarId(Integer id) {
        if (id == null || id <= 0) {
            throw new CampoInvalidoException("ID inválido.");
        }
    }
}

