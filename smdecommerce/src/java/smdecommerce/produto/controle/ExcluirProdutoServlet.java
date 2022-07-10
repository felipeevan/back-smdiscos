package smdecommerce.produto.controle;

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
import smdecommerce.produto.modelo.Produto;
import smdecommerce.produto.modelo.ProdutoDAO;
import smdecommerce.produto_categoria.modelo.Produto_CategoriaDAO;

/**
 *
 * @author nicol
 */
public class ExcluirProdutoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JsonObject data = new Gson().fromJson(request.getReader(), JsonObject.class);
        
        /* Entrada */
        int id = data.get("id").getAsInt();
        
        /* Processamento */
        ProdutoDAO produtoDAO = new ProdutoDAO();
        Produto_CategoriaDAO produto_categoriaDAO = new Produto_CategoriaDAO();
        
        boolean sucesso     = false;
        String mensagem     = null;
        Produto produto = null;
        
        try{
            produtoDAO.excluir(id);
            produto_categoriaDAO.excluirProduto(id);
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
            myResponse.put("sucesso", sucesso);
            myResponse.put("data", gson.toJson(produto));
            myResponse.put("mensagem", sucesso ? "Produto excluído com sucesso" : mensagem);
            out.print(myResponse);
            out.flush();
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }
}
