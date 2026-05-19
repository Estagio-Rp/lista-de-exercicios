package br.com.rpinfo.analuisa.adapter.rest.controller;

import br.com.rpinfo.analuisa.application.dto.produtos.ProdutosDTO;
import br.com.rpinfo.analuisa.application.usecase.ProdutosUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutosController {

    private final ProdutosUseCase produtosUseCase;

    public ProdutosController(ProdutosUseCase produtosUseCase) {
        this.produtosUseCase = produtosUseCase;
    }

    @PostMapping
    public ResponseEntity<String> inserir(@RequestBody ProdutosDTO dto) {
        try {
            boolean cadastrado = produtosUseCase.inserirProduto(dto);

            if (cadastrado) {
                return ResponseEntity.ok("Cadastro realizado com sucesso!");
            }

            return ResponseEntity.badRequest().body("Não foi possível cadastrar o produto.");

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        try {
            List<ProdutosDTO> produtos = produtosUseCase.listarProdutos();
            return ResponseEntity.ok(produtos);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Integer id) {
        try {
            ProdutosDTO produto = produtosUseCase.buscarProduto(id);
            return ResponseEntity.ok(produto);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Integer id, @RequestBody ProdutosDTO dto) {
        try {
            ProdutosDTO produto = produtosUseCase.atualizarProduto(id, dto);
            return ResponseEntity.ok(produto);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Integer id) {
        try {
            produtosUseCase.deletarProduto(id);
            return ResponseEntity.noContent().build();

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}