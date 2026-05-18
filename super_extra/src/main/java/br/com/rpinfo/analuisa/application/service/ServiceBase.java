package br.com.rpinfo.analuisa.application.service;

import br.com.rpinfo.analuisa.Conexao;

import java.sql.Connection;
import java.sql.SQLException;

public class ServiceBase {

    private Connection connection;

    public ServiceBase(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                this.connection = connectionManager();
            }

            return connection;

        } catch (SQLException e) {
            System.out.println("Erro ao revalidar/reabrir a conexão: " + e.getMessage());
            throw new RuntimeException("Falha ao garantir uma conexão válida.", e);
        }
    }

    public static Connection connectionManager() {
        try {
            return Conexao.conectar();

        } catch (Exception e) {
            System.out.println("Erro ao abrir conexão com o banco: " + e.getMessage());
            throw new RuntimeException("Falha ao conectar com o banco de dados.", e);
        }
    }
}