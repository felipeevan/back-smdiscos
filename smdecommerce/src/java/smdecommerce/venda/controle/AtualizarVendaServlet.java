package smdecommerce.venda.controle;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Integer.parseInt;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONObject;
import smdecommerce.produto.modelo.Produto;
import smdecommerce.usuario.modelo.Usuario;
import smdecommerce.venda.modelo.Venda;
import smdecommerce.venda.modelo.VendaDAO;
import smdecommerce.venda_produto.modelo.Venda_Produto;
import smdecommerce.venda_produto.modelo.Venda_ProdutoDAO;


/**
 *
 * 
 * Classe que implementa a ação de inserir uma nova venda/pedido de um usuário do tipo cliente
 */
public class AtualizarVendaServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JsonObject data = new Gson().fromJson(request.getReader(), JsonObject.class);
        HttpSession session = request.getSession(false);
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        /* entrada */
        int id = data.get("id").getAsInt();
        int pagamento = data.get("pagamento").getAsInt();
        String status_pag = data.get("status_pag").getAsString();
        int entrega = data.get("entrega").getAsInt();
        String status_ent = data.get("status_ent").getAsString();
        String status_pedido = data.get("status_pedido").getAsString();
        String produtos = data.get("produtos").getAsString();
        String quantidades = data.get("quantidades").getAsString();
        
        /* processamento */
        VendaDAO vendaDAO = new VendaDAO();
        Venda_ProdutoDAO venda_produtoDAO = new Venda_ProdutoDAO();
        
        produtos = produtos.replaceAll(" ","");
        String[] listaProdutos = produtos.split(",");
        quantidades = quantidades.replaceAll(" ","");
        String[] listaQuantidades = quantidades.split(",");
        
        boolean sucesso = false;
        String mensagem = null;
        Venda venda = null;
        List<Venda_Produto> produtosQuantidade = null;
        List<Produto> produtosDavenda = null;
        
        try {
            venda = vendaDAO.obter(id);
            vendaDAO.atualizar(usuario.getId(), pagamento, status_pag, entrega, status_ent, status_pedido);
            venda_produtoDAO.excluirProdutos(id);
            for (int i = 0; i < listaProdutos.length; i++) {
                venda_produtoDAO.inserir(venda.getId(),parseInt(listaProdutos[i]), parseInt(listaQuantidades[i]));
            }
            produtosQuantidade = venda_produtoDAO.obterVendaProduto(venda.getId());
            produtosDavenda = venda_produtoDAO.obterProdutos(venda.getId());
           
            sucesso = true;
            mensagem = "Venda inserida com sucesso";
            response.setStatus(200);
        } catch (Exception ex) {
            response.setStatus(400);
            sucesso = false;
            mensagem = ex.getMessage(); 
        }
        try (PrintWriter out = response.getWriter()) {
            JSONObject myResponse = new JSONObject();
            Gson gson = new Gson();
            JsonObject dataReturn = new Gson().fromJson(gson.toJson(venda), JsonObject.class);
            dataReturn.addProperty("produtosDavenda", gson.toJson(produtosDavenda));
            dataReturn.addProperty("venda_produto", gson.toJson(produtosQuantidade));
            dataReturn.addProperty("usuario", gson.toJson(usuario));
            myResponse.put("sucesso", sucesso);
            myResponse.put("data", gson.toJson(dataReturn));
            myResponse.put("mensagem", sucesso ? "Venda atualizada com sucesso" : mensagem);
            out.print(myResponse);
            out.flush();
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }

}
