package br.edu.ifto.web.servlet;

import br.edu.ifto.web.dao.ProdutoDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/produtos")
public class ProdutoServlet extends HttpServlet {
    private final ProdutoDAO produtoDAO = new ProdutoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("produtos", produtoDAO.listarTodos());
        request.getRequestDispatcher("/WEB-INF/views/produtos.jsp").forward(request, response);
    }
}
