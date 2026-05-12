package br.com.rpinfo.analuisa.shared;

import java.sql.Connection;
import java.sql.Statement;

public class SequenceUtils {

    public static void reorganizarIdsCidades(Connection connection) {
        String sql = """
                WITH mapa AS (
                    SELECT
                        id AS id_antigo,
                        ROW_NUMBER() OVER (ORDER BY id) AS id_novo
                    FROM cidades
                ),
                atualiza_enderecos AS (
                    UPDATE enderecos e
                    SET cida_id = -m.id_novo
                    FROM mapa m
                    WHERE e.cida_id = m.id_antigo
                    RETURNING e.id
                ),
                atualiza_cidades AS (
                    UPDATE cidades c
                    SET id = -m.id_novo
                    FROM mapa m
                    WHERE c.id = m.id_antigo
                    RETURNING c.id
                )
                UPDATE cidades
                SET id = ABS(id);
                
                UPDATE enderecos
                SET cida_id = ABS(cida_id)
                WHERE cida_id IS NOT NULL AND cida_id < 0;
                
                SELECT setval(
                    pg_get_serial_sequence('cidades', 'id'),
                    COALESCE((SELECT MAX(id) FROM cidades), 1),
                    (SELECT COUNT(*) > 0 FROM cidades)
                );
                """;

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao reorganizar IDs das cidades: " + e.getMessage());
        }
    }
}