package br.com.rpinfo.analuisa.application.usecase;

import br.com.rpinfo.analuisa.application.dto.produtos.ProdutosDTO;
import br.com.rpinfo.analuisa.application.service.ProdutosService;
import br.com.rpinfo.analuisa.application.service.ServiceBase;
import br.com.rpinfo.analuisa.domain.model.entity.Produto;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class ProdutosUseCase {

    public static void inserirProduto(ProdutosDTO produtoDTO) {
        try (Connection connection = ServiceBase.connectionManager()) {
            ProdutosService service = new ProdutosService(connection);

            Produto produto = produtoDTO.toEntity();

            service.cadastrarProduto(produto);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao inserir produto: " + e.getMessage());
        }
    }

    public static List<ProdutosDTO> listarProdutos() {
        try (Connection connection = ServiceBase.connectionManager()) {
            ProdutosService service = new ProdutosService(connection);

            List<Produto> produtos = service.listarProdutos();
            List<ProdutosDTO> produtosDTO = new ArrayList<>();

            for (Produto produto : produtos) {
                produtosDTO.add(ProdutosDTO.fromEntity(produto));
            }

            return produtosDTO;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar produtos: " + e.getMessage());
        }
    }

    public static void atualizarProduto(Integer id, ProdutosDTO produtoDTO) {
        try (Connection connection = ServiceBase.connectionManager()) {
            ProdutosService service = new ProdutosService(connection);

            Produto produto = produtoDTO.toEntity();
            produto.setId(id);

            service.atualizarProduto(produto);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar produto: " + e.getMessage());
        }
    }

    public static void deletarProduto(Integer id) {
        try (Connection connection = ServiceBase.connectionManager()) {
            ProdutosService service = new ProdutosService(connection);

            service.deletarProduto(id);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar produto: " + e.getMessage());
        }
    }
}