package smdecommerce.categoria.controle;

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
import smdecommerce.categoria.modelo.Categoria;
import smdecommerce.categoria.modelo.CategoriaDAO;

/**
 *
 * @author nicol
 */
public class NovaCategoriaServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JsonObject data = new Gson().fromJson(request.getReader(), JsonObject.class);
        
        /* Entrada */
        String descricao = data.get("descricao").getAsString();
        
        /* Processamento */
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        
        boolean sucesso     = false;
        String mensagem     = null;
        Categoria categoria = null;
        
        try{
            categoriaDAO.inserir(descricao);
            categoria = categoriaDAO.obterPorDesc(descricao);
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
            myResponse.put("data", gson.toJson(categoria));
            myResponse.put("mensagem", sucesso ? "Categoria criada com sucesso" : mensagem);
            out.print(myResponse);
            out.flush();
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }
}
