package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.util.MealUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class MealServlet extends HttpServlet {
    public static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("get all");
        request.setAttribute("meals", MealUtil.getTos(MealUtil.MEALS, MealUtil.CALORIES_PER_DAY));
        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
