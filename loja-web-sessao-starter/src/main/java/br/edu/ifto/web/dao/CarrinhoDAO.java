package br.edu.ifto.web.dao;

import br.edu.ifto.web.config.ConnectionFactory;
import br.edu.ifto.web.model.Carrinho;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Persistência do carrinho de usuários autenticados (tabela carrinho_item). */
public class CarrinhoDAO {

    public Carrinho carregar(int usuarioId) {
        String sql = "SELECT p.id, p.nome, p.descricao, p.preco, ci.quantidade "
                + "FROM carrinho_item ci JOIN produto p ON p.id = ci.produto_id "
                + "WHERE ci.usuario_id = ? ORDER BY p.id";
        Carrinho carrinho = new Carrinho();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) carrinho.adicionar(ProdutoDAO.mapear(rs), rs.getInt("quantidade"));
            }
            return carrinho;
        } catch (SQLException e) {
            throw new DAOException("Erro ao carregar carrinho.", e);
        }
    }

    /** Soma a quantidade ao item (insere se ainda não existir). */
    public void adicionar(int usuarioId, int produtoId, int quantidade) {
        String sql = "INSERT INTO carrinho_item (usuario_id, produto_id, quantidade) VALUES (?, ?, ?) "
                + "ON CONFLICT (usuario_id, produto_id) "
                + "DO UPDATE SET quantidade = carrinho_item.quantidade + EXCLUDED.quantidade";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            ps.setInt(2, produtoId);
            ps.setInt(3, quantidade);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erro ao adicionar item ao carrinho.", e);
        }
    }

    public void remover(int usuarioId, int produtoId) {
        executar("DELETE FROM carrinho_item WHERE usuario_id = ? AND produto_id = ?", usuarioId, produtoId);
    }

    public void limpar(int usuarioId) {
        executar("DELETE FROM carrinho_item WHERE usuario_id = ?", usuarioId);
    }

    private void executar(String sql, int... parametros) {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 0; i < parametros.length; i++) ps.setInt(i + 1, parametros[i]);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erro ao atualizar carrinho.", e);
        }
    }
}
