package smdecommerce.produto.controle;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Integer.parseInt;
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

public class NovoProdutoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JsonObject data = new Gson().fromJson(request.getReader(), JsonObject.class);
        
        /* Entrada */
        String nome      = data.get("nome").getAsString();
        String autor     = data.get("autor").getAsString();
        String descricao = data.get("descricao").getAsString();
        double preco     = data.get("preco").getAsDouble();
        int quantidade   = data.get("quantidade").getAsInt();
        String foto      = data.get("foto").getAsString();
        String categorias = data.get("categorias").getAsString();
        
        /* Processamento */
        ProdutoDAO produtoDAO = new ProdutoDAO();
        Produto_CategoriaDAO produto_categoriaDAO = new Produto_CategoriaDAO();
        //categorias = categorias.substring(1,-2);
        categorias = categorias.replaceAll(" ","");
        String[] listaCategorias = categorias.split(",");
        boolean sucesso = false;
        String mensagem = null;
        Produto produto = null;
        
        try{
            produtoDAO.inserir(nome,autor,descricao,preco,quantidade,foto);
            produto = produtoDAO.obterProdutoPorNome(nome);
            for (int i = 0; i < listaCategorias.length; i++) {
                produto_categoriaDAO.inserir(produto.getId(),parseInt(listaCategorias[i]));
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
            myResponse.put("sucesso", sucesso);
            myResponse.put("data", gson.toJson(produto));
            myResponse.put("mensagem", sucesso ? "Produto cadastrado com sucesso" : mensagem);
            out.print(myResponse);
            out.flush();
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }
}
