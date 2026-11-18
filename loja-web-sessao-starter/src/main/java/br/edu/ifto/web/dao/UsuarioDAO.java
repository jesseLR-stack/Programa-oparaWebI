package br.edu.ifto.web.dao;

import br.edu.ifto.web.config.ConnectionFactory;
import br.edu.ifto.web.model.Usuario;
import br.edu.ifto.web.util.SenhaUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UsuarioDAO {

    public Optional<Usuario> buscarPorEmail(String email) {
        if (email == null) return Optional.empty();
        String sql = "SELECT id, nome, email, senha_hash FROM usuario WHERE LOWER(email) = LOWER(?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email.trim());
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return Optional.empty();
                return Optional.of(new Usuario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha_hash")));
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar usuário.", e);
        }
    }

    public Optional<Usuario> autenticar(String email, String senha) {
        if (email == null || senha == null) return Optional.empty();
        String hash = SenhaUtil.hash(senha);
        return buscarPorEmail(email).filter(u -> u.getSenha().equals(hash));
    }
}
