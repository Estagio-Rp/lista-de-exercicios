package br.com.rpinfo.analuisa.adapter.security;

import br.com.rpinfo.analuisa.application.port.output.TokenGeneratorPort;
import br.com.rpinfo.analuisa.domain.model.entity.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class JwtService implements TokenGeneratorPort {

    private final JwtEncoder jwtEncoder;
    private final long expiresIn;

    public JwtService(
            JwtEncoder jwtEncoder,
            @Value("${jwt.expiration-seconds}") long expiresIn
    ) {
        this.jwtEncoder = jwtEncoder;
        this.expiresIn = expiresIn;
    }

    @Override
    public String gerarToken(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException(
                    "O usuário não pode ser nulo para gerar o token."
            );
        }

        if (usuario.getEmail() == null ||
                usuario.getEmail().isBlank()) {

            throw new IllegalArgumentException(
                    "O usuário deve possuir um e-mail para gerar o token."
            );
        }

        if (usuario.getPerfil() == null) {
            throw new IllegalArgumentException(
                    "O usuário deve possuir um perfil para gerar o token."
            );
        }

        Instant agora = Instant.now();
        Instant expiracao = agora.plusSeconds(expiresIn);

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("super-extra-api")
                .issuedAt(agora)
                .expiresAt(expiracao)
                .subject(usuario.getEmail())
                .claim("usuarioId", usuario.getCodigo())
                .claim("nome", usuario.getNome())
                .claim(
                        "roles",
                        List.of(usuario.getPerfil().name())
                )
                .claim(
                        "permissoes",
                        criarPermissoes(usuario)
                )
                .build();

        JwsHeader header = JwsHeader
                .with(MacAlgorithm.HS256)
                .type("JWT")
                .build();

        JwtEncoderParameters parametros = JwtEncoderParameters.from(
                header,
                claims
        );

        return jwtEncoder
                .encode(parametros)
                .getTokenValue();
    }

    private List<String> criarPermissoes(Usuario usuario) {
        List<String> permissoes = new java.util.ArrayList<>();

        adicionarPermissao(
                permissoes,
                usuario.getCadastros(),
                "CADASTROS"
        );

        adicionarPermissao(
                permissoes,
                usuario.getEntradas(),
                "ENTRADAS"
        );

        adicionarPermissao(
                permissoes,
                usuario.getSaidas(),
                "SAIDAS"
        );

        adicionarPermissao(
                permissoes,
                usuario.getCancelamentos(),
                "CANCELAMENTOS"
        );

        adicionarPermissao(
                permissoes,
                usuario.getRelatorios(),
                "RELATORIOS"
        );

        adicionarPermissao(
                permissoes,
                usuario.getConfiguracoes(),
                "CONFIGURACOES"
        );

        return permissoes;
    }

    private void adicionarPermissao(
            List<String> permissoes,
            String valor,
            String permissao
    ) {
        if ("S".equalsIgnoreCase(valor)) {
            permissoes.add(permissao);
        }
    }
}