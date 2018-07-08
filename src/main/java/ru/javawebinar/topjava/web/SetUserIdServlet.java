package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SetUserIdServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(SetUserIdServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("userId"));

        log.info("changing uId {}", id);

        SecurityUtil.setUserId(id);

        resp.sendRedirect("meals");
    }
}
