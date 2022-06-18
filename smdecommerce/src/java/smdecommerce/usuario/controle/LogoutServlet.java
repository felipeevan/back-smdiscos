package smdecommerce.usuario.controle;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONObject;

/**
 *
 *
 * Classe que implementa a ação de logout de um usuário
 */
public class LogoutServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        //request.setAttribute("mensagem", "Sua sessão foi encerrada");
        //RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
        //requestDispatcher.forward(request, response);
        PrintWriter out = response.getWriter();
        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JSONObject my_obj = new JSONObject();
        my_obj.put("message", "Sua sessão foi encerrada");
        out.print(my_obj);
        out.flush();   
    }

}
