package br.edu.ifto.web.servlet;

import br.edu.ifto.web.model.Carrinho;
import br.edu.ifto.web.service.CarrinhoService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/carrinho")
public class CarrinhoServlet extends HttpServlet {
    private final CarrinhoService carrinhoService = new CarrinhoService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Carrinho carrinho = carrinhoService.obter(request);
        request.setAttribute("itens", carrinho.getItens());
        request.setAttribute("quantidadeTotal", carrinho.getQuantidadeTotal());
        request.setAttribute("valorTotal", carrinho.getValorTotal());
        request.getRequestDispatcher("/WEB-INF/views/carrinho.jsp").forward(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        carrinhoService.limpar(request);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
