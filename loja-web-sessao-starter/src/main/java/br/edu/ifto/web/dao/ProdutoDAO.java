package br.edu.ifto.web.dao;

import br.edu.ifto.web.config.ConnectionFactory;
import br.edu.ifto.web.model.Produto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProdutoDAO {

    public List<Produto> listarTodos() {
        String sql = "SELECT id, nome, descricao, preco FROM produto ORDER BY id";
        List<Produto> produtos = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) produtos.add(mapear(rs));
            return produtos;
        } catch (SQLException e) {
            throw new DAOException("Erro ao listar produtos.", e);
        }
    }

    public Optional<Produto> buscarPorId(int id) {
        String sql = "SELECT id, nome, descricao, preco FROM produto WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapear(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar produto " + id + ".", e);
        }
    }

    static Produto mapear(ResultSet rs) throws SQLException {
        return new Produto(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("descricao"),
                rs.getBigDecimal("preco").doubleValue());
    }
}
