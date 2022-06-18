package smdecommerce.administrador.controle;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import smdecommerce.administrador.modelo.AdministradorDAO;
import smdecommerce.administrador.modelo.Administrador;
import smdecommerce.usuario.modelo.Usuario;
import smdecommerce.usuario.modelo.UsuarioDAO;


/**
 *
 * 
 * Classe que implementa a ação de inserir um novo usuário do tipo administrador
 */
public class NovoAdmServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JsonObject data = new Gson().fromJson(request.getReader(), JsonObject.class);
        /* entrada */
        String nome = data.get("nome").getAsString();
        String email = data.get("email").getAsString();
        String login = data.get("login").getAsString();
        String senha = data.get("senha").getAsString();
        /* processamento */
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        AdministradorDAO admDAO = new AdministradorDAO();
        
        boolean sucesso = false;
        String mensagem = null;
        Usuario usuario = new Usuario();
        Administrador adm = null;
        try {
            usuarioDAO.inserir(nome, email, login, senha);
            usuario = usuarioDAO.obter(login);
            admDAO.inserir(usuario.getId());
            sucesso = true;
            mensagem = "Usuário inserido com sucesso";
            response.setStatus(200);
        } catch (Exception ex) {
            response.setStatus(400);
            sucesso = false;
            mensagem = ex.getMessage(); 
        }
        try (PrintWriter out = response.getWriter()) {
            JSONObject myResponse = new JSONObject();
            Gson gson = new Gson();
            myResponse.put("sucesso", sucesso);
            myResponse.put("data", gson.toJson(usuario));
            myResponse.put("mensagem", sucesso ? "Usuário criado com sucesso" : mensagem);
            out.print(myResponse);
            out.flush();
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }

}
