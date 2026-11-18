package br.edu.ifto.web.servlet;

import br.edu.ifto.web.dao.ProdutoDAO;
import br.edu.ifto.web.model.Produto;
import br.edu.ifto.web.service.CarrinhoService;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/carrinho/itens")
public class CarrinhoItemServlet extends HttpServlet {
    private final ProdutoDAO produtoDAO = new ProdutoDAO();
    private final CarrinhoService carrinhoService = new CarrinhoService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String valorId = request.getParameter("id");
        int id;
        try {
            id = Integer.parseInt(valorId);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Informe um id de produto válido.");
            return;
        }
        Optional<Produto> produto = produtoDAO.buscarPorId(id);
        if (!produto.isPresent()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Produto não encontrado.");
            return;
        }
        carrinhoService.adicionar(request, produto.get());
        response.sendRedirect(request.getContextPath() + "/carrinho");
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
            carrinhoService.remover(request, Integer.parseInt(valorId));
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Id inválido.");
        }
    }
}
