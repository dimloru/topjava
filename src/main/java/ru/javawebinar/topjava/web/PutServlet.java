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

public class PutServlet extends HttpServlet {
    private static final Logger log = getLogger(AddServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        MealsUtil.put(
                req.getParameter("id"),
                req.getParameter("datetime"),
                req.getParameter("description"),
                req.getParameter("calories"));

        resp.sendRedirect("meals");
    }
}