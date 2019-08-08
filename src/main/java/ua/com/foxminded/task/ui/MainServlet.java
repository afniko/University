package ua.com.foxminded.task.ui;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/")
public class MainServlet extends HttpServlet {

    private static final long serialVersionUID = 7553902061756712545L;
    private String text = "main text page";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("text", text);
        req.getRequestDispatcher("main.jsp").forward(req, resp);
    }

}
