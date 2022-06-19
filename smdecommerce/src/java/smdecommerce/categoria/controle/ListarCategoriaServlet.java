package smdecommerce.categoria.controle;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.List;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import smdecommerce.categoria.modelo.Categoria;
import smdecommerce.categoria.modelo.CategoriaDAO;

/**
 *
 * @author nicol
 */
public class ListarCategoriaServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /* Processamento */
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        List<Categoria> categorias = null;
        
        boolean sucesso     = false;
        String mensagem     = null;
        
        try{
            categorias = categoriaDAO.obterCategorias();
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
            myResponse.put("data", gson.toJson(categorias));
            //myResponse.put("mensagem", sucesso ? "Categoria criada com sucesso" : mensagem);
            out.print(myResponse);
            out.flush();
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }
}
