package br.edu.ifto.web.config;

import br.edu.ifto.web.dao.DAOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Fábrica de conexões JDBC.
 * Os dados de acesso podem ser alterados pelas variáveis de ambiente
 * DB_URL, DB_USER e DB_PASSWORD.
 */
public final class ConnectionFactory {
    private static final String URL = env("DB_URL", "jdbc:postgresql://localhost:5432/loja");
    private static final String USER = env("DB_USER", "loja");
    private static final String PASSWORD = env("DB_PASSWORD", "loja");

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError("Driver do PostgreSQL não encontrado.");
        }
    }

    private ConnectionFactory() { }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new DAOException("Não foi possível conectar ao banco de dados em " + URL, e);
        }
    }

    public static String getUrl() { return URL; }

    private static String env(String nome, String padrao) {
        String valor = System.getenv(nome);
        if (valor == null || valor.trim().isEmpty()) valor = System.getProperty(nome);
        return (valor == null || valor.trim().isEmpty()) ? padrao : valor.trim();
    }
}
