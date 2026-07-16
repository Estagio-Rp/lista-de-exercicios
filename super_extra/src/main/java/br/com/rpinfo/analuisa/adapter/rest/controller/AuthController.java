package br.com.rpinfo.analuisa.adapter.rest.controller;

import br.com.rpinfo.analuisa.application.dto.login.CadastroRequest;
import br.com.rpinfo.analuisa.application.dto.login.LoginRequest;
import br.com.rpinfo.analuisa.application.dto.login.TokenResponse;
import br.com.rpinfo.analuisa.application.port.input.AutenticarUsuarioInputPort;
import br.com.rpinfo.analuisa.application.port.input.CadastrarUsuarioInputPort;
import br.com.rpinfo.analuisa.domain.model.entity.Perfil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final CadastrarUsuarioInputPort cadastrarUsuarioInputPort;
    private final AutenticarUsuarioInputPort autenticarUsuarioInputPort;
    private final long expiresIn;

    public AuthController(
            CadastrarUsuarioInputPort cadastrarUsuarioInputPort,
            AutenticarUsuarioInputPort autenticarUsuarioInputPort,
            @Value("${jwt.expiration-seconds}") long expiresIn
    ) {
        this.cadastrarUsuarioInputPort = cadastrarUsuarioInputPort;
        this.autenticarUsuarioInputPort = autenticarUsuarioInputPort;
        this.expiresIn = expiresIn;
    }

    @PostMapping("/cadastro")
    public ResponseEntity<Void> cadastrar(
            @RequestBody CadastroRequest request
    ) {
        cadastrarUsuarioInputPort.cadastrar(
                request.nome(),
                request.email(),
                request.senha(),
                Perfil.CLIENTE
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(
            @RequestBody LoginRequest request
    ) {
        String token = autenticarUsuarioInputPort.autenticar(
                request.email(),
                request.senha()
        );

        TokenResponse response = new TokenResponse(
                token,
                "Bearer",
                expiresIn
        );

        return ResponseEntity.ok(response);
    }
}