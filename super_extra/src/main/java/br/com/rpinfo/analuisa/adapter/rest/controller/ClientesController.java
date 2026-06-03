package br.com.rpinfo.analuisa.adapter.rest.controller;

import br.com.rpinfo.analuisa.application.dto.clientes.ClientesDTO;
import br.com.rpinfo.analuisa.application.usecase.ClientesUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClientesController {

    private final ClientesUseCase clientesUseCase;

    public ClientesController(ClientesUseCase clientesUseCase) {
        this.clientesUseCase = clientesUseCase;
    }

    @PostMapping
    public ResponseEntity<String> inserir(@RequestBody ClientesDTO dto) {
        try {
            boolean cadastrado = clientesUseCase.inserirCliente(dto);

            if (cadastrado) {
                return ResponseEntity.ok("Cadastro realizado com sucesso!");
            }

            return ResponseEntity.badRequest().body("Não foi possível cadastrar o cliente.");

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        try {
            List<ClientesDTO> clientes = clientesUseCase.listarClientes();
            return ResponseEntity.ok(clientes);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Integer id) {
        try {
            ClientesDTO cliente = clientesUseCase.buscarCliente(id);
            return ResponseEntity.ok(cliente);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizar(@PathVariable Integer id, @RequestBody ClientesDTO dto) {
        try {
            clientesUseCase.atualizarCliente(id, dto);

            return ResponseEntity.ok("Cliente atualizado com sucesso.");

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Integer id) {
        try {
            boolean deletado = clientesUseCase.deletarCliente(id);

            if (deletado) {
                return ResponseEntity.ok("Cliente deletado com sucesso!");
            }

            return ResponseEntity.badRequest().body("Não foi possível deletar o cliente.");

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}