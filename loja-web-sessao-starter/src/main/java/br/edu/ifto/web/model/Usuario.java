package br.edu.ifto.web.model;

import java.io.Serializable;
import java.util.Objects;

public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int id;
    private final String nome;
    private final String email;
    private final String senha;

    public Usuario(int id, String nome, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return id == usuario.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Usuario{id=" + id + ", nome='" + nome + "', email='" + email + "'}";
    }
}
