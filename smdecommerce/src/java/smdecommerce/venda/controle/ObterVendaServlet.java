package smdecommerce.venda.controle;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import smdecommerce.produto.modelo.Produto;
import smdecommerce.produto.modelo.ProdutoDAO;
import smdecommerce.venda.modelo.Venda;
import smdecommerce.venda.modelo.VendaDAO;
import smdecommerce.venda_produto.modelo.Venda_Produto;
import smdecommerce.venda_produto.modelo.Venda_ProdutoDAO;

/**
 *
 * @author nicol
 */
public class ObterVendaServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JsonObject data = new Gson().fromJson(request.getReader(), JsonObject.class);
        
        /* Entrada */
        int id = data.get("id").getAsInt();
        
        /* Processamento */
        VendaDAO vendaDAO = new VendaDAO();
        Venda_ProdutoDAO venda_produtoDAO = new Venda_ProdutoDAO();
        ProdutoDAO produtoDAO = new ProdutoDAO();
        
        boolean sucesso = false;
        String mensagem = null;
        Venda venda = null;
        List<Venda_Produto> venda_produto = null;
        List quantidades = new ArrayList(); 
        List<Produto> produtos = null;
        
        try{
            venda = vendaDAO.obter(id);
            venda_produto = venda_produtoDAO.obterVendas(id);
            for (Venda_Produto obj:venda_produto) {
                quantidades.add(obj.getQuantidade());
            }
            response.setStatus(200);
            sucesso = true;
            
        } catch (Exception ex) {
            response.setStatus(400);
            sucesso = false;
            mensagem = ex.getMessage();
        }
        
        /* Retorno */
        try (PrintWriter out = response.getWriter()) {
            JSONObject myResponse = new JSONObject();
            Gson gson = new Gson();
            JsonObject dataReturn = new Gson().fromJson(gson.toJson(venda), JsonObject.class);
            dataReturn.addProperty("categorias", gson.toJson(produtos));
            dataReturn.addProperty("quantidades", gson.toJson(quantidades));
            myResponse.put("sucesso", sucesso);
            myResponse.put("data", gson.toJson(dataReturn));
            myResponse.put("mensagem", sucesso ? "Venda encontrado com sucesso" : mensagem);
            out.print(myResponse);
            out.flush();
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }
}
