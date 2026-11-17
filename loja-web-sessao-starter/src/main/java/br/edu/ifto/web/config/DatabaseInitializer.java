package br.edu.ifto.web.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Cria as tabelas e insere os dados iniciais quando a aplicação sobe.
 * Tenta novamente por alguns segundos caso o banco ainda esteja iniciando.
 */
@WebListener
public class DatabaseInitializer implements ServletContextListener {
    private static final Logger LOG = Logger.getLogger(DatabaseInitializer.class.getName());
    private static final int TENTATIVAS = 15;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String script = lerScript();
        for (int tentativa = 1; tentativa <= TENTATIVAS; tentativa++) {
            try (Connection conn = ConnectionFactory.getConnection();
                 Statement st = conn.createStatement()) {
                for (String sql : script.split(";\\s*(\\r?\\n|$)")) {
                    if (!sql.trim().isEmpty()) st.execute(sql);
                }
                LOG.info("Banco de dados inicializado: " + ConnectionFactory.getUrl());
                return;
            } catch (Exception e) {
                LOG.log(Level.WARNING, "Banco indisponível (tentativa " + tentativa + "/" + TENTATIVAS + "): "
                        + e.getMessage());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
        LOG.severe("Não foi possível inicializar o banco de dados.");
    }

    private String lerScript() {
        try (InputStream in = getClass().getResourceAsStream("/db/schema.sql")) {
            if (in == null) throw new IllegalStateException("Arquivo db/schema.sql não encontrado.");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            return reader.lines()
                    .filter(linha -> !linha.trim().startsWith("--"))
                    .collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new IllegalStateException("Erro ao ler db/schema.sql", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) { }
}
