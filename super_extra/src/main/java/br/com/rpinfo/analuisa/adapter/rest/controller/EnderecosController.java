package br.com.rpinfo.analuisa.adapter.rest.controller;

import br.com.rpinfo.analuisa.application.dto.enderecos.EnderecosDTO;
import br.com.rpinfo.analuisa.application.usecase.EnderecosUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enderecos")
public class EnderecosController {

    private final EnderecosUseCase enderecosUseCase;

    public EnderecosController(EnderecosUseCase enderecosUseCase) {
        this.enderecosUseCase = enderecosUseCase;
    }

    @PostMapping
    public ResponseEntity<String> inserir(@RequestBody EnderecosDTO dto) {
        try {
            boolean cadastrado = enderecosUseCase.inserirEndereco(dto);

            if (cadastrado) {
                return ResponseEntity.ok("Cadastro realizado com sucesso!");
            }

            return ResponseEntity.badRequest().body("Não foi possível cadastrar o endereço.");

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        try {
            List<EnderecosDTO> enderecos = enderecosUseCase.listarEnderecos();
            return ResponseEntity.ok(enderecos);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Integer id) {
        try {
            EnderecosDTO endereco = enderecosUseCase.buscarEndereco(id);
            return ResponseEntity.ok(endereco);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Integer id, @RequestBody EnderecosDTO dto) {
        try {
            EnderecosDTO endereco = enderecosUseCase.atualizarEndereco(id, dto);
            return ResponseEntity.ok(endereco);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Integer id) {
        try {
            boolean deletado = enderecosUseCase.deletarEndereco(id);

            if (deletado) {
                return ResponseEntity.ok("Endereço deletado com sucesso!");
            }

            return ResponseEntity.badRequest().body("Não foi possível deletar o endereço.");

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}