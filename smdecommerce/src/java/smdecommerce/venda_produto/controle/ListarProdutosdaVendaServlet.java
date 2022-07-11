package smdecommerce.venda_produto.controle;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import smdecommerce.produto.modelo.Produto;
import java.util.List;
import javax.servlet.http.HttpSession;
import smdecommerce.venda_produto.modelo.Venda_ProdutoDAO;

/**
 *
 * @author nicol 
 * @author priscila
 */
public class ListarProdutosdaVendaServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JsonObject data = new Gson().fromJson(request.getReader(), JsonObject.class);
        HttpSession session = request.getSession(false);
        
        /* Entrada */
        int id_venda = data.get("id_venda").getAsInt();
        
        /* Processamento */
        Venda_ProdutoDAO venda_produtoDAO = new Venda_ProdutoDAO();
        List<Produto> produtos = null;
        
        boolean sucesso     = false;
        String mensagem     = null;
        
        if (session != null){
            try{
                produtos = venda_produtoDAO.obterProdutos(id_venda);
                response.setStatus(200);
                sucesso = true;
            } catch (Exception ex) {
                response.setStatus(400);
                sucesso = false;
                mensagem = ex.getMessage();
            }
        }    
        
        /* Saída */
        try (PrintWriter out = response.getWriter()) {
            JSONObject myResponse = new JSONObject();
            Gson gson = new Gson();
            myResponse.put("sucesso", sucesso);
            myResponse.put("data", gson.toJson(produtos));
            //myResponse.put("mensagem", sucesso ? "Produtos listados  com sucesso" : mensagem);
            out.print(myResponse);
            out.flush();
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }
}
