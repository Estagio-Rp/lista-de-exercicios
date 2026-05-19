package br.com.rpinfo.analuisa.application.usecase;

import br.com.rpinfo.analuisa.application.dto.produtos.ProdutosDTO;
import br.com.rpinfo.analuisa.application.service.ProdutosService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProdutosUseCase {

    private final ProdutosService produtosService;

    public ProdutosUseCase(ProdutosService produtosService) {
        this.produtosService = produtosService;
    }

    public boolean inserirProduto(ProdutosDTO dto) {
        return produtosService.inserirProduto(dto);
    }

    public List<ProdutosDTO> listarProdutos() {
        return produtosService.listarProdutos();
    }

    public ProdutosDTO buscarProduto(Integer id) {
        return produtosService.buscarPorId(id);
    }

    public ProdutosDTO atualizarProduto(Integer id, ProdutosDTO dto) {
        return produtosService.atualizarProduto(id, dto);
    }

    public void deletarProduto(Integer id) {
        produtosService.deletarProduto(id);
    }
}