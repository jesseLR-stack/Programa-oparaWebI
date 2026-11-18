package br.edu.ifto.web.servlet;

import br.edu.ifto.web.dao.UsuarioDAO;
import br.edu.ifto.web.model.Usuario;
import br.edu.ifto.web.service.CarrinhoService;
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
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final CarrinhoService carrinhoService = new CarrinhoService();

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

        Optional<Usuario> usuario = usuarioDAO.autenticar(email, senha);

        if (usuario.isPresent()) {
            HttpSession session = request.getSession();
            // Evita fixação de sessão: gera um novo id após autenticar
            request.changeSessionId();
            carrinhoService.transferirParaUsuario(session, usuario.get());
            session.setAttribute(CarrinhoService.ATRIBUTO_USUARIO, usuario.get());
            response.sendRedirect(request.getContextPath() + "/");
        } else {
            request.setAttribute("erro", "E-mail ou senha inválida.");
            request.setAttribute("email", email);
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }
}
