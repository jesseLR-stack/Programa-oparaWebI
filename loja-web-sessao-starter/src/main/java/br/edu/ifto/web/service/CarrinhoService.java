package br.edu.ifto.web.service;

import br.edu.ifto.web.dao.CarrinhoDAO;
import br.edu.ifto.web.model.Carrinho;
import br.edu.ifto.web.model.ItemCarrinho;
import br.edu.ifto.web.model.Produto;
import br.edu.ifto.web.model.Usuario;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Regras do carrinho:
 * - usuário autenticado: o carrinho é gravado no banco (tabela carrinho_item)
 *   e continua disponível em outros acessos/navegadores;
 * - visitante: o carrinho fica temporariamente na sessão e é transferido
 *   para o banco no momento do login.
 */
public class CarrinhoService {
    public static final String ATRIBUTO_CARRINHO = "carrinho";
    public static final String ATRIBUTO_USUARIO = "usuarioLogado";

    private final CarrinhoDAO dao = new CarrinhoDAO();

    public Carrinho obter(HttpServletRequest request) {
        Usuario usuario = usuarioLogado(request);
        if (usuario != null) return dao.carregar(usuario.getId());
        HttpSession session = request.getSession(false);
        Carrinho carrinho = session == null ? null : (Carrinho) session.getAttribute(ATRIBUTO_CARRINHO);
        return carrinho == null ? new Carrinho() : carrinho;
    }

    public void adicionar(HttpServletRequest request, Produto produto) {
        Usuario usuario = usuarioLogado(request);
        if (usuario != null) {
            dao.adicionar(usuario.getId(), produto.getId(), 1);
            return;
        }
        HttpSession session = request.getSession();
        Carrinho carrinho = (Carrinho) session.getAttribute(ATRIBUTO_CARRINHO);
        if (carrinho == null) {
            carrinho = new Carrinho();
            session.setAttribute(ATRIBUTO_CARRINHO, carrinho);
        }
        carrinho.adicionar(produto);
    }

    public void remover(HttpServletRequest request, int produtoId) {
        Usuario usuario = usuarioLogado(request);
        if (usuario != null) {
            dao.remover(usuario.getId(), produtoId);
            return;
        }
        HttpSession session = request.getSession(false);
        if (session == null) return;
        Carrinho carrinho = (Carrinho) session.getAttribute(ATRIBUTO_CARRINHO);
        if (carrinho != null) carrinho.remover(produtoId);
    }

    public void limpar(HttpServletRequest request) {
        Usuario usuario = usuarioLogado(request);
        if (usuario != null) dao.limpar(usuario.getId());
        HttpSession session = request.getSession(false);
        if (session != null) session.removeAttribute(ATRIBUTO_CARRINHO);
    }

    /** Move os itens escolhidos antes do login para o carrinho do usuário no banco. */
    public void transferirParaUsuario(HttpSession session, Usuario usuario) {
        Carrinho carrinho = (Carrinho) session.getAttribute(ATRIBUTO_CARRINHO);
        if (carrinho != null) {
            for (ItemCarrinho item : carrinho.getItens()) {
                dao.adicionar(usuario.getId(), item.getProduto().getId(), item.getQuantidade());
            }
            session.removeAttribute(ATRIBUTO_CARRINHO);
        }
    }

    private Usuario usuarioLogado(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session == null ? null : (Usuario) session.getAttribute(ATRIBUTO_USUARIO);
    }
}
