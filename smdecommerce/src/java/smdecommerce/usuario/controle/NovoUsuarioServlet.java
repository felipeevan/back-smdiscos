package smdecommerce.usuario.controle;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import smdecommerce.cliente.modelo.ClienteDAO;
import smdecommerce.usuario.modelo.Usuario;
import smdecommerce.usuario.modelo.UsuarioDAO;


/**
 *
 * 
 * Classe que implementa a ação de inserir um novo usuário do tipo cliente
 */
public class NovoUsuarioServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /* entrada */
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String login = request.getParameter("login");
        String senha = request.getParameter("senha");
        /* processamento */
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        ClienteDAO clienteDAO = new ClienteDAO();
        boolean inseriu = false;
        String mensagem = null;
        Usuario usuario;
        try {
            usuarioDAO.inserir(nome, email, login, senha);
            usuario = usuarioDAO.obter(login);
            clienteDAO.inserir(usuario.getId(), "Não cadastrado");
            inseriu = true;
            mensagem = "Cliente inserido com sucesso";
        } catch (Exception ex) {
            inseriu = false;
            mensagem = ex.getMessage();
            
            PrintWriter out = response.getWriter();
            response.setStatus(400);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            JSONObject my_obj = new JSONObject();
            my_obj.put("message", ex.getMessage());
            out.print(my_obj);
            out.flush();   
            return;
        }
        /* saída */
        //request.setAttribute("mensagem", mensagem);
        //RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
        //requestDispatcher.forward(request, response);
        
        PrintWriter out = response.getWriter();
        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        JSONObject my_obj = new JSONObject();
        Gson gson = new Gson();
        my_obj.put("data", gson.toJson(usuario));
        out.print(my_obj);
        out.flush();   
    }

}
