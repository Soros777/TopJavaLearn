package ru.javawebinar.topjava.web;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private ConfigurableApplicationContext springContext;
    private MealRestController mealController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        springContext = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/spring-db.xml");
        mealController = springContext.getBean(MealRestController.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action == null ? "all" : action) {
            case "delete" -> {
                int id = getId(request);
                mealController.delete(id);
                response.sendRedirect("meals");
            }
            case "create", "update" -> {
                Meal meal = "create".equals(action) ? new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 100) :
                        mealController.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("mealForm.jsp").forward(request, response);
            }
            case "filter" -> {
                LocalDate startDate = DateTimeUtil.parseDate(request.getParameter("startDate"));
                LocalDate endDate = DateTimeUtil.parseDate(request.getParameter("endDate"));
                LocalTime startTime = DateTimeUtil.parseTime(request.getParameter("startTime"));
                LocalTime endTime = DateTimeUtil.parseTime(request.getParameter("endTime"));
                request.setAttribute("meals", mealController.getBetween(startDate, endDate, startTime, endTime));
                request.getRequestDispatcher("meals.jsp").forward(request, response);
            }
            default -> {
                request.setAttribute("meals", mealController.getAll());
                request.getRequestDispatcher("meals.jsp").forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        if(StringUtils.hasLength(request.getParameter("id"))) {
            mealController.update(meal, getId(request));
        } else {
            mealController.create(meal);
        }
        response.sendRedirect("meals");
    }

    private int getId(HttpServletRequest request) {
        String idParameter = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(idParameter);
    }
}
