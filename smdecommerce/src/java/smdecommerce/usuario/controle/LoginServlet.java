package smdecommerce.usuario.controle;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;        
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
                response.setStatus(200);
            } else {
                response.setStatus(400);
                sucesso = false;
                mensagem = "Login ou senha inválida";
            }
        } catch (Exception ex) {
            response.setStatus(500);
            sucesso = false;
            mensagem = ex.getMessage();
        }
         try (PrintWriter out = response.getWriter()) {
            JSONObject myResponse = new JSONObject();
            Gson gson = new Gson();
            if (administrador != null){
                myResponse.put("tipoSessao", 1);
            } else {
                myResponse.put("tipoSessao", 2);
            }
            myResponse.put("sucesso", sucesso);
            myResponse.put("data", gson.toJson(usuario));
            myResponse.put("mensagem", sucesso ? "Usuário logado com sucesso" : mensagem);
            out.print(myResponse);
            out.flush();
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }
}

