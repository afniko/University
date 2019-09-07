package ua.com.foxminded.task.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class MainServletTest {
    private HttpServletRequest request = mock(HttpServletRequest.class);
    private HttpServletResponse response = mock(HttpServletResponse.class);
    private RequestDispatcher mockDispatcher = mock(RequestDispatcher.class);
    private MainServlet mainServlet = new MainServlet();

    @Test
    public void whenRequestGet_thenOpenMainViewPage() throws ServletException, IOException {
        String text = "Main text page";
        when(request.getRequestDispatcher("main.jsp")).thenReturn(mockDispatcher);

        mainServlet.doGet(request, response);
        verify(request, times(1)).setAttribute("text", text);
    }
}
