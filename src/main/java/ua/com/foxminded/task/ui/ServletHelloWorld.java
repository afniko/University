package ua.com.foxminded.task.ui;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet("/hello")
public class ServletHelloWorld extends HttpServlet{

    private static final long serialVersionUID = -8107642356833737724L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       resp.setContentType("text/plain");
       resp.getWriter().write("Hello world! Is real servlet");
    }


    
}
