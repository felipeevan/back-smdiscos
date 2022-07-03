package smdecommerce.venda.controle;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import smdecommerce.venda.modelo.Venda;
import smdecommerce.venda.modelo.VendaDAO;



/**
 *
 * 
 * Classe que implementa a ação de editar uma venda existente
 */
public class AtualizarVendaServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JsonObject data = new Gson().fromJson(request.getReader(), JsonObject.class);
        
        /* Entrada */
        int id = data.get("id").getAsInt();
        int pagamento = data.get("pagamento").getAsInt();
        String status_pag = data.get("status_pag").getAsString();
        int entrega = data.get("entrega").getAsInt();
        String status_ent = data.get("status_ent").getAsString();
        String status_pedido = data.get("status_pedido").getAsString();
        
        /* processamento */
        VendaDAO vendaDAO = new VendaDAO();
        Venda venda = new Venda();
        boolean sucesso = false;
        String mensagem = null;
  
        try {
            vendaDAO.atualizar(id, pagamento, status_pag, entrega, status_ent, status_pedido);
            venda = vendaDAO.obter(id);
            sucesso = true;
            mensagem = "Venda atualizada com sucesso";
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
            myResponse.put("data", gson.toJson(venda));
            myResponse.put("mensagem", sucesso ? "Venda atualizada com sucesso" : mensagem);
            out.print(myResponse);
            out.flush();
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");    }

}
