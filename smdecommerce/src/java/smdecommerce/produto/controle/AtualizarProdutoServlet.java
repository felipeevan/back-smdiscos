package smdecommerce.produto.controle;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Integer.parseInt;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import smdecommerce.categoria.modelo.Categoria;
import smdecommerce.produto.modelo.Produto;
import smdecommerce.produto.modelo.ProdutoDAO;
import smdecommerce.produto_categoria.modelo.Produto_CategoriaDAO;

/**
 *
 * @author nicol
 */
@WebServlet(name = "AtualizarProdutoServlet", urlPatterns = {"/AtualizarProduto"})
public class AtualizarProdutoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JsonObject data = new Gson().fromJson(request.getReader(), JsonObject.class);
        
        /* Entrada */
        int id           = data.get("id").getAsInt();
        String nome      = data.get("nome").getAsString();
        String autor     = data.get("autor").getAsString();
        String descricao = data.get("descricao").getAsString();
        double preco     = data.get("preco").getAsDouble();
        int quantidade   = data.get("quantidade").getAsInt();
        String foto      = data.get("foto").getAsString();
        String categoriasVelhas = data.get("categoriasVelhas").getAsString();
        String categoriasNovas = data.get("categoriasNovas").getAsString();
        
        /* Processamento */
        ProdutoDAO produtoDAO = new ProdutoDAO();
        Produto_CategoriaDAO produto_categoriaDAO = new Produto_CategoriaDAO();
        //categorias = categorias.substring(1,-2);
        categoriasVelhas = categoriasVelhas.replaceAll(" ","");
        categoriasNovas = categoriasNovas.replaceAll(" ","");
        String[] listaCategoriasVelhas = categoriasVelhas.split(",");
        String[] listaCategoriasNovas = categoriasNovas.split(",");
        boolean sucesso = false;
        String mensagem = null;
        Produto produto = null;
        List<Categoria> categorias = null;
        
        try{
            produtoDAO.atualizar(id,nome,autor,descricao,preco,quantidade,foto);
            for (int i = 0; i < listaCategoriasVelhas.length; i++) {
                produto_categoriaDAO.excluir(id,parseInt(listaCategoriasVelhas[i]));
            }
            for (int i = 0; i < listaCategoriasNovas.length; i++) {
                produto_categoriaDAO.inserir(id,parseInt(listaCategoriasNovas[i]));
            }
            produto = produtoDAO.obter(id);
            categorias = produto_categoriaDAO.obterCategorias(id);
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
            JsonObject dataReturn = new Gson().fromJson(gson.toJson(produto), JsonObject.class);
            dataReturn.addProperty("categorias", gson.toJson(categorias));
            myResponse.put("sucesso", sucesso);
            myResponse.put("data", gson.toJson(dataReturn));
            myResponse.put("mensagem", sucesso ? "Produto atualizado com sucesso" : mensagem);
            out.print(myResponse);
            out.flush();
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }
}
