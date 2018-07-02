package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDataMemoryStorageImpl;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("GET MealServlet");

        List<MealWithExceed> meals =  MealsUtil.getAllMealsWithExceeded();
        request.setAttribute("meals", meals);
        request.setAttribute("formatter", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        request.getRequestDispatcher("/meals.jsp").forward(request, response);

//        response.sendRedirect("meals.jsp");
    }


}
