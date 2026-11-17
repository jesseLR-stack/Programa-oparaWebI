package br.edu.ifto.web.servlet;

import br.edu.ifto.web.dao.ProdutoMemoriaRepository;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/produtos")
public class ProdutoServlet extends HttpServlet {
    private final ProdutoMemoriaRepository repository = new ProdutoMemoriaRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("produtos", repository.listarTodos());
        request.getRequestDispatcher("/WEB-INF/views/produtos.jsp").forward(request, response);
    }
}
