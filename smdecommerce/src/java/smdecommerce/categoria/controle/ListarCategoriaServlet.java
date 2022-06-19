package smdecommerce.categoria.controle;

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
    }
}
