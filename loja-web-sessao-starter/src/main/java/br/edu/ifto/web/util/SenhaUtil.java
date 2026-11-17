package br.edu.ifto.web.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/** Gera o hash SHA-256 (hexadecimal) das senhas, para não gravá-las em texto puro. */
public final class SenhaUtil {
    private SenhaUtil() { }

    public static String hash(String senha) {
        try {
            byte[] bytes = MessageDigest.getInstance("SHA-256").digest(senha.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }
}
