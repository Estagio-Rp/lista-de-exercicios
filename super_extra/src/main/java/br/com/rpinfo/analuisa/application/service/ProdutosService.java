package br.com.rpinfo.analuisa.application.service;

import br.com.rpinfo.analuisa.application.dto.produtos.ProdutosDTO;
import br.com.rpinfo.analuisa.domain.exceptions.CampoInvalidoException;
import br.com.rpinfo.analuisa.domain.exceptions.RegistroNaoEncontradoException;
import br.com.rpinfo.analuisa.domain.model.entity.Produto;
import br.com.rpinfo.analuisa.domain.repositories.produtos.ProdutosDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutosService {

    private final ProdutosDAO produtosDAO;

    public ProdutosService(ProdutosDAO produtosDAO) {
        this.produtosDAO = produtosDAO;
    }

    @Transactional
    public boolean inserirProduto(ProdutosDTO dto) {
        Produto produto = dto.toEntity();

        validarProduto(produto);

        Produto produtoSalvo = produtosDAO.save(produto);

        return produtoSalvo.getId() != null;
    }

    public List<ProdutosDTO> listarProdutos() {
        return produtosDAO.findAllByOrderByIdAsc()
                .stream()
                .map(ProdutosDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public ProdutosDTO buscarPorId(Integer id) {
        validarId(id);

        Produto produto = produtosDAO.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Produto não encontrado."));

        return ProdutosDTO.fromEntity(produto);
    }

    @Transactional
    public ProdutosDTO atualizarProduto(Integer id, ProdutosDTO dto) {
        validarId(id);

        Produto produtoAtual = produtosDAO.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Produto não encontrado."));

        if (dto.getNome() != null) {
            if (dto.getNome().isBlank()) {
                throw new CampoInvalidoException("O nome do produto é obrigatório.");
            }

            String nome = dto.getNome().trim();

            if (nome.matches("\\d+")) {
                throw new CampoInvalidoException("O nome do produto não pode ser apenas numérico.");
            }

            produtoAtual.setNome(nome);
        }

        if (dto.getPreco() != null) {
            if (dto.getPreco().compareTo(BigDecimal.ZERO) < 0) {
                throw new CampoInvalidoException("O preço do produto não pode ser negativo.");
            }

            produtoAtual.setPreco(dto.getPreco());
        }

        if (dto.getCategoria() != null) {
            if (dto.getCategoria().isBlank()) {
                throw new CampoInvalidoException("A categoria do produto é obrigatória.");
            }

            String categoria = dto.getCategoria().trim();

            if (categoria.matches("\\d+")) {
                throw new CampoInvalidoException("A categoria do produto não pode ser apenas numérica.");
            }

            produtoAtual.setCategoria(categoria);
        }

        if (dto.getEstoque() != null) {
            if (dto.getEstoque() < 0) {
                throw new CampoInvalidoException("O estoque do produto não pode ser negativo.");
            }

            produtoAtual.setEstoque(dto.getEstoque());
        }

        Produto produtoSalvo = produtosDAO.save(produtoAtual);

        return ProdutosDTO.fromEntity(produtoSalvo);
    }

    @Transactional
    public boolean deletarProduto(Integer id) {
        validarId(id);

        if (!produtosDAO.existsById(id)) {
            throw new RegistroNaoEncontradoException("Produto não encontrado.");
        }

        produtosDAO.deleteById(id);

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
    }

    private void validarId(Integer id) {
        if (id == null || id <= 0) {
            throw new CampoInvalidoException("ID inválido.");
        }
    }
}
