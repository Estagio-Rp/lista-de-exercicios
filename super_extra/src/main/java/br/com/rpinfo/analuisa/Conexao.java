package br.com.rpinfo.analuisa;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class Conexao {

    public static Connection conectar() throws Exception{
        Properties properties = new Properties();

        InputStream inputStream = Conexao.class
                .getClassLoader()
                .getResourceAsStream("application.properties");

        if (inputStream == null) {
            throw new Exception("Erro ao ler properties");
        }

        properties.load(inputStream);

        String url = properties.getProperty("spring.datasource.url");
        String usuario = properties.getProperty("spring.datasource.username");
        String senha = properties.getProperty("spring.datasource.password");

        return DriverManager.getConnection(url, usuario, senha);
    }
}
