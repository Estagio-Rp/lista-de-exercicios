package br.com.rpinfo.analuisa.shared;

import java.sql.Connection;
import java.sql.Statement;

//Pedi ajuda pro chat!!
public class SequenceUtils {

    public static void reorganizarIdsCidades(Connection connection) {
        try (Statement stmt = connection.createStatement()) {

            stmt.execute("DROP TABLE IF EXISTS temp_mapa_cidades");

            stmt.execute("""
                    CREATE TEMP TABLE temp_mapa_cidades AS
                    SELECT
                        id AS id_antigo,
                        ROW_NUMBER() OVER (ORDER BY id) AS id_novo
                    FROM cidades
                    """);

            stmt.execute("""
                    UPDATE enderecos e
                    SET cida_id = -m.id_novo
                    FROM temp_mapa_cidades m
                    WHERE e.cida_id = m.id_antigo
                    """);

            stmt.execute("""
                    UPDATE cidades c
                    SET id = -m.id_novo
                    FROM temp_mapa_cidades m
                    WHERE c.id = m.id_antigo
                    """);

            stmt.execute("""
                    UPDATE cidades
                    SET id = ABS(id)
                    """);

            stmt.execute("""
                    UPDATE enderecos
                    SET cida_id = ABS(cida_id)
                    WHERE cida_id IS NOT NULL AND cida_id < 0
                    """);

            stmt.execute("""
                    SELECT setval(
                        pg_get_serial_sequence('cidades', 'id'),
                        COALESCE((SELECT MAX(id) FROM cidades), 1),
                        (SELECT COUNT(*) > 0 FROM cidades)
                    )
                    """);

            stmt.execute("DROP TABLE IF EXISTS temp_mapa_cidades");

        } catch (Exception e) {
            throw new RuntimeException("Erro ao reorganizar ID das cidades: " + e.getMessage());
        }
    }

    public static void reorganizarIdsProdutos(Connection connection) {
        try (Statement stmt = connection.createStatement()) {

            stmt.execute("DROP TABLE IF EXISTS temp_mapa_produtos");

            stmt.execute("""
                CREATE TEMP TABLE temp_mapa_produtos AS
                SELECT
                    id AS id_antigo,
                    ROW_NUMBER() OVER (ORDER BY id) AS id_novo
                FROM produtos
                """);

            stmt.execute("""
                UPDATE produtos p
                SET id = -m.id_novo
                FROM temp_mapa_produtos m
                WHERE p.id = m.id_antigo
                """);

            stmt.execute("""
                UPDATE produtos
                SET id = ABS(id)
                """);

            stmt.execute("""
                SELECT setval(
                    pg_get_serial_sequence('produtos', 'id'),
                    COALESCE((SELECT MAX(id) FROM produtos), 1),
                    (SELECT COUNT(*) > 0 FROM produtos)
                )
                """);

            stmt.execute("DROP TABLE IF EXISTS temp_mapa_produtos");

        } catch (Exception e) {
            throw new RuntimeException("Erro ao reorganizar IDs dos produtos: " + e.getMessage());
        }
    }
}
