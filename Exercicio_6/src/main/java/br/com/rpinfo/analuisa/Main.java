package br.com.rpinfo.analuisa;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {

        Properties properties = new Properties();

        try {
            InputStream input = Main.class
                    .getClassLoader()
                    .getResourceAsStream("application.properties");

            if (input == null) {
                System.out.println("application properties file not found");
                return;
            }

            properties.load(input);

            String url = properties.getProperty("spring.datasource.url");
            String usuario = properties.getProperty("spring.datasource.username");
            String senha = properties.getProperty("spring.datasource.password");

            Connection conn = DriverManager.getConnection(url, usuario, senha);

            String query = "SELECT id, nome, email FROM usuarios";

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                System.out.println("USUÁRIOS CADASTRADOS");

                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id"));
                    System.out.println("Nome: " + rs.getString("nome"));
                    System.out.println("Email: " + rs.getString("email"));
                    System.out.println("-------------------------");
                }
            }

            conn.close();

        }catch (Exception e) {
            System.out.println("erro ao cosultar dados" + e.getMessage());
        }
    }
}