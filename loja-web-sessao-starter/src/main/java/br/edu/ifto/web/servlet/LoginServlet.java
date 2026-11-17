package br.edu.ifto.web.servlet;

import br.edu.ifto.web.dao.UsuarioMemoriaRepository;
import br.edu.ifto.web.model.Usuario;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UsuarioMemoriaRepository repository = new UsuarioMemoriaRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        Optional<Usuario> usuario = repository.autenticar(email, senha);

        if (usuario.isPresent()) {
            HttpSession session = request.getSession();
            session.setAttribute("usuarioLogado", usuario.get());
            response.sendRedirect(request.getContextPath() + "/");
        } else {
            request.setAttribute("erro", "E-mail ou senha inválida.");
            request.setAttribute("email", email);
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }
}
