package br.edu.ifto.web.servlet;

import br.edu.ifto.web.dao.ProdutoMemoriaRepository;
import br.edu.ifto.web.model.Carrinho;
import br.edu.ifto.web.model.Produto;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/carrinho/itens")
public class CarrinhoItemServlet extends HttpServlet {
    private final ProdutoMemoriaRepository repository = new ProdutoMemoriaRepository();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String valorId = request.getParameter("id");
        try {
            int id = Integer.parseInt(valorId);
            Optional<Produto> produto = repository.buscarPorId(id);
            if (!produto.isPresent()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Produto não encontrado.");
                return;
            }
            HttpSession session = request.getSession();
            Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");
            if (carrinho == null) {
                carrinho = new Carrinho();
                session.setAttribute("carrinho", carrinho);
            }
            carrinho.adicionar(produto.get());
            response.sendRedirect(request.getContextPath() + "/carrinho");
        } catch (NumberFormatException | NullPointerException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Informe um id de produto válido.");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String valorId = request.getParameter("id");
        if (valorId == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Informe o parâmetro id.");
            return;
        }
        try {
            int id = Integer.parseInt(valorId);
            HttpSession session = request.getSession(false);
            if (session != null) {
                Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");
                if (carrinho != null) carrinho.remover(id);
            }
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Id inválido.");
        }
    }
}
