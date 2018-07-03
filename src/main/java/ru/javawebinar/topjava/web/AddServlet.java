package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;

public class AddServlet extends HttpServlet {
    private static final Logger log = getLogger(AddServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        // можно заменить этот сервлет на put c id "-1"

        // old version
//        try {
//            LocalDateTime ldt = LocalDateTime.parse(req.getParameter("datetime"));
//            int calories = Integer.parseInt(req.getParameter("calories"));
//            MealsUtil.addMeal(ldt, req.getParameter("description"), calories);
//            log.debug("Meal added");
//        } catch (Exception e) {
//            log.warn("Add: invalid parameters");
//        }


        //might be done this way, but want to save both put and add functions just in case
//        MealsUtil.put(
//                req.getParameter("id"),
//                req.getParameter("dateTime"),
//                req.getParameter("description"),
//                req.getParameter("calories"));

        MealsUtil.add(
                req.getParameter("datetime"),
                req.getParameter("description"),
                req.getParameter("calories"));


        resp.sendRedirect("meals");
    }
}
