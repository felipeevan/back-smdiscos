package smdecommerce.venda_produto.controle;

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
import smdecommerce.venda_produto.modelo.Venda_ProdutoDAO;

/**
 *
 * 
 */
public class ExcluirProdutodaVendaServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JsonObject data = new Gson().fromJson(request.getReader(), JsonObject.class);
        HttpSession session = request.getSession(false);
        
        /* Entrada */
        int id_venda = data.get("id_venda").getAsInt();
        int id_produto = data.get("id_produto").getAsInt();
        
        /* Processamento */
        Venda_ProdutoDAO venda_produtoDAO = new Venda_ProdutoDAO();
        
        boolean sucesso     = false;
        String mensagem     = null;
        
        if (session != null){
            try{
                venda_produtoDAO.excluir(id_venda, id_produto);
                response.setStatus(200);
                sucesso = true;

            } catch (Exception ex) {
                response.setStatus(400);
                sucesso = false;
                mensagem = ex.getMessage();
            }
        }    
        
        /* Retorno */
        try (PrintWriter out = response.getWriter()) {
            JSONObject myResponse = new JSONObject();
            Gson gson = new Gson();
            myResponse.put("sucesso", sucesso);
            myResponse.put("mensagem", sucesso ? "Produto excluído da categoria com sucesso" : mensagem);
            out.print(myResponse);
            out.flush();
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }
}
