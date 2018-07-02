package ru.javawebinar.topjava.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        System.out.println(req.getParameter("datetime"));
        System.out.println(req.getParameter("description"));
        System.out.println(req.getParameter("calories"));

        resp.sendRedirect("meals");
    }
}
