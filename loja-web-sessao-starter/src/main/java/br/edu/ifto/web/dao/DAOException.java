package br.edu.ifto.web.dao;

/** Exceção não verificada para erros de acesso ao banco de dados. */
public class DAOException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DAOException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
