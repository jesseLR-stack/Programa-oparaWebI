package br.edu.ifto.web.servlet;

import br.edu.ifto.web.model.Carrinho;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/carrinho")
public class CarrinhoServlet extends HttpServlet {

    private Carrinho obterCarrinho(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");
        if (carrinho == null) {
            carrinho = new Carrinho();
            session.setAttribute("carrinho", carrinho);
        }
        return carrinho;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Carrinho carrinho = obterCarrinho(request);
        request.setAttribute("itens", carrinho.getItens());
        request.setAttribute("quantidadeTotal", carrinho.getQuantidadeTotal());
        request.setAttribute("valorTotal", carrinho.getValorTotal());
        request.getRequestDispatcher("/WEB-INF/views/carrinho.jsp").forward(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute("carrinho");
        }
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
