package br.com.rpinfo.analuisa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BancoUtils {

    public static boolean existeRegistro(String tabela, int id) {
        String sql = "SELECT COUNT(*) AS total FROM " + tabela + " WHERE id = ?";

        try (Connection connect = Conexao.conectar();
             PreparedStatement stmt = connect.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt("total") > 0;
            }

        } catch (Exception e) {
            System.out.println("Erro ao verificar registro na tabela " + tabela + ": " + e.getMessage());
            return false;
        }
    }

    public static boolean temRegistros(String tabela) {
        String sql = "SELECT COUNT(*) AS total FROM " + tabela;

        try (Connection connect = Conexao.conectar();
             PreparedStatement stmt = connect.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            return rs.next() && rs.getInt("total") > 0;

        } catch (Exception e) {
            System.out.println("Erro ao verificar tabela " + tabela + ": " + e.getMessage());
            return false;
        }
    }
}
