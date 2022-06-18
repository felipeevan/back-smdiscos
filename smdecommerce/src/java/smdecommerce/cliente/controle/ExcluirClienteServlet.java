package smdecommerce.cliente.controle;

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
import smdecommerce.cliente.modelo.ClienteDAO;
import smdecommerce.usuario.modelo.Usuario;
import smdecommerce.usuario.modelo.UsuarioDAO;


/**
 *
 * 
 * Classe que implementa a ação de editar um usuário do tipo administrador existente
 */
public class ExcluirClienteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JsonObject data = new Gson().fromJson(request.getReader(), JsonObject.class);
        /* entrada */
        HttpSession session = request.getSession(false);
        Usuario usuario = (Usuario) session.getAttribute("usuario");
       
        /* processamento */
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        ClienteDAO clienteDAO = new ClienteDAO();
        boolean sucesso = false;
        String mensagem = null;
  
        try {
            usuarioDAO.excluir(usuario.getId());
            clienteDAO.excluir(usuario.getId());
            sucesso = true;
            mensagem = "Usuário excluído com sucesso";
            session.invalidate();
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
            myResponse.put("mensagem", sucesso ? "Usuário excluído com sucesso" : mensagem);
            out.print(myResponse);
            out.flush();
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }

}
