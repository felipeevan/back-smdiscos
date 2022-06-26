package smdecommerce.venda.controle;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import smdecommerce.usuario.modelo.Usuario;
import smdecommerce.venda.modelo.Venda;
import smdecommerce.venda.modelo.VendaDAO;

/**
 *
 * @author nicol
 */
public class ListarVendasClienteServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        /* Processamento */
        VendaDAO vendaDAO = new VendaDAO();
        List<Venda> vendas = null;
        
        boolean sucesso     = false;
        String mensagem     = null;
        
        try{
            vendas = vendaDAO.obterVendascliente(usuario.getId());
            response.setStatus(200);
            sucesso = true;
        } catch (Exception ex) {
            response.setStatus(400);
            sucesso = false;
            mensagem = ex.getMessage();
        }
        
        /* Saída */
        try (PrintWriter out = response.getWriter()) {
            JSONObject myResponse = new JSONObject();
            Gson gson = new Gson();
            myResponse.put("sucesso", sucesso);
            myResponse.put("data", gson.toJson(vendas));
            //myResponse.put("mensagem", sucesso ? "Venda listado  com sucesso" : mensagem);
            out.print(myResponse);
            out.flush();
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }
}
