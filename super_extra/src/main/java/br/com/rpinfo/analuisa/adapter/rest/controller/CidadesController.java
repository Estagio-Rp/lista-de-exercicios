package br.com.rpinfo.analuisa.adapter.rest.controller;

import br.com.rpinfo.analuisa.application.dto.cidades.CidadesDTO;
import br.com.rpinfo.analuisa.application.usecase.CidadesUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cidades")
public class CidadesController {

    private final CidadesUseCase cidadesUseCase;

    public CidadesController(CidadesUseCase cidadesUseCase) {
        this.cidadesUseCase = cidadesUseCase;
    }

    @PostMapping
    public ResponseEntity<String> inserir(@RequestBody CidadesDTO dto) {
        try {
            boolean cadastrado = cidadesUseCase.inserirCidade(dto);

            if (cadastrado) {
                return ResponseEntity.ok("Cadastro realizado com sucesso!");
            }

            return ResponseEntity.badRequest().body("Não foi possível cadastrar a cidade.");

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        try {
            List<CidadesDTO> cidades = cidadesUseCase.listarCidades();
            return ResponseEntity.ok(cidades);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Integer id) {
        try {
            CidadesDTO cidade = cidadesUseCase.buscarCidade(id);
            return ResponseEntity.ok(cidade);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Integer id, @RequestBody CidadesDTO dto) {
        try {
            CidadesDTO cidade = cidadesUseCase.atualizarCidade(id, dto);
            return ResponseEntity.ok(cidade);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Integer id) {
        try {
            boolean deletado = cidadesUseCase.deletarCidade(id);

            if (deletado) {
                return ResponseEntity.ok("Cidade deletada com sucesso!");
            }

            return ResponseEntity.badRequest().body("Não foi possível deletar a cidade.");

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}