package ru.javawebinar.topjava.web;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("users.jsp").forward(request, response);
//        response.sendRedirect("users.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
