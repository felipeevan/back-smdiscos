package smdecommerce.produto_categoria.controle;

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
import smdecommerce.produto_categoria.modelo.Produto_CategoriaDAO;
import smdecommerce.usuario.modelo.Usuario;

/**
 *
 * @author nicol 
 * @author priscila
 */
public class ListarProdutosdaCategoriaServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JsonObject data = new Gson().fromJson(request.getReader(), JsonObject.class);
        HttpSession session = request.getSession(false);
        Usuario adm = (Usuario) session.getAttribute("administrador");
        /* Entrada */
        int id_categoria = data.get("id_categoria").getAsInt();
        
        /* Processamento */
        Produto_CategoriaDAO produto_categoriaDAO = new Produto_CategoriaDAO();
        List<Produto> produtos = null;
        
        boolean sucesso     = false;
        String mensagem     = null;
        
        if (adm != null){
            try{
                produtos = produto_categoriaDAO.obterProdutos(id_categoria);
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
            //myResponse.put("mensagem", sucesso ? "Produto listado  com sucesso" : mensagem);
            out.print(myResponse);
            out.flush();
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }
}
