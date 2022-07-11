package smdecommerce.produto_categoria.controle;

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
import smdecommerce.produto_categoria.modelo.Produto_CategoriaDAO;
import smdecommerce.usuario.modelo.Usuario;

/**
 *
 * 
 */
public class ExcluirProdutoCategoriaServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JsonObject data = new Gson().fromJson(request.getReader(), JsonObject.class);
        HttpSession session = request.getSession(false);
        Usuario adm = (Usuario) session.getAttribute("administrador");
        
        /* Entrada */
        int id_produto = data.get("id_produto").getAsInt();
        int id_categoria = data.get("id_categoria").getAsInt();
        
        /* Processamento */
        Produto_CategoriaDAO produto_categoriaDAO = new Produto_CategoriaDAO();
        
        boolean sucesso     = false;
        String mensagem     = null;
        
        if (adm != null){
            try{
                produto_categoriaDAO.excluir(id_produto,id_categoria);
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
