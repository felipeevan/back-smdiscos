package smdecommerce.venda.controle;

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
import smdecommerce.venda.modelo.VendaDAO;
import smdecommerce.venda_produto.modelo.Venda_ProdutoDAO;


/**
 *
 * 
 * Classe que implementa a ação de editar um usuário do tipo administrador existente
 */
public class ExcluirVendaServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JsonObject data = new Gson().fromJson(request.getReader(), JsonObject.class);
        HttpSession session = request.getSession(false);
        /* entrada */
        int id = data.get("id").getAsInt();
        
        /* processamento */
        VendaDAO vendaDAO = new VendaDAO();
        Venda_ProdutoDAO venda_produtoDAO = new Venda_ProdutoDAO();
        boolean sucesso = false;
        String mensagem = null;
  
        if (session != null){
            try {

                vendaDAO.excluir(id);
                venda_produtoDAO.excluirProdutos(id);
                sucesso = true;
                mensagem = "Venda excluída com sucesso";
                response.setStatus(200);
            } catch (Exception ex) {
                response.setStatus(400);
                sucesso = false;
                mensagem = ex.getMessage(); 
            }
        }
        
        try (PrintWriter out = response.getWriter()) {
            JSONObject myResponse = new JSONObject();
            Gson gson = new Gson();
            myResponse.put("sucesso", sucesso);
            myResponse.put("mensagem", sucesso ? "Venda excluída com sucesso" : mensagem);
            out.print(myResponse);
            out.flush();
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }

}
