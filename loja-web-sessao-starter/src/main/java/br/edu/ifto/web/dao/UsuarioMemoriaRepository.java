package br.edu.ifto.web.dao;

import br.edu.ifto.web.model.Usuario;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UsuarioMemoriaRepository {
    private final List<Usuario> usuarios = Collections.unmodifiableList(Arrays.asList(
        new Usuario(1, "Cristiano Xavier", "cristiano@email.com", "123456")
    ));

    public List<Usuario> listarTodos() { return usuarios; }

    public Optional<Usuario> buscarPorId(int id) {
        return usuarios.stream().filter(u -> u.getId() == id).findFirst();
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        if (email == null) return Optional.empty();
        return usuarios.stream().filter(u -> u.getEmail().equalsIgnoreCase(email.trim())).findFirst();
    }

    public Optional<Usuario> autenticar(String email, String senha) {
        if (email == null || senha == null) return Optional.empty();
        return usuarios.stream()
            .filter(u -> u.getEmail().equalsIgnoreCase(email.trim()) && u.getSenha().equals(senha))
            .findFirst();
    }
}
