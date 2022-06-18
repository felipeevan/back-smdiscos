package smdecommerce.usuario.controle;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONObject;
import smdecommerce.usuario.modelo.Usuario;
import smdecommerce.usuario.modelo.UsuarioDAO;
import smdecommerce.administrador.modelo.AdministradorDAO;
import smdecommerce.administrador.modelo.Administrador;

/**
 *
 *
 * Classe que implementa a ação de login de um usuário e determina seu tipo
 */
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JsonObject data = new Gson().fromJson(request.getReader(), JsonObject.class);
        String login = data.get("login").getAsString();
        String senha = data.get("senha").getAsString();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        AdministradorDAO admDAO = new AdministradorDAO();
        boolean sucesso = false;
        String mensagem = null;
        Usuario usuario = null;
        Administrador administrador = null;
        try {
            usuario = usuarioDAO.obter(login);
            administrador = admDAO.obter(usuario.getId());
            if (usuario.getSenha().equals(senha)) {
                sucesso = true;
                HttpSession session = request.getSession(true);
                if (administrador == null){
                    session.setAttribute("usuario", usuario);
                } else {
                    session.setAttribute("administrador", usuario);
                  }
            } else {
                sucesso = false;
                mensagem = "Login ou senha inválida";
            }
        } catch (Exception ex) {
            sucesso = false;
            mensagem = ex.getMessage();
        }
         try (PrintWriter out = response.getWriter()) {
            out.print("{");
            if (administrador != null){
            out.print("\"sessao\": Administrador, ");
            } else {
            out.print("\"sessao\": Cliente, ");
            }
            out.print("\"sucesso\": " + sucesso + ", ");
            out.print("\"mensagem\": \"" + (sucesso ? "Usuário logado com sucesso" : mensagem) + "\"");
            out.print("}");
        }
    }
}

