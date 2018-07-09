package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.MealServlet;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@Controller
public class MealRestController {
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    private MealService service;


    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public void processPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")),
                SecurityUtil.authUserId());

        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        service.save(meal);
        response.sendRedirect("meals");

    }

    public void processGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                service.delete(id, SecurityUtil.authUserId());
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000, SecurityUtil.authUserId()) :
                        service.get(getId(request), SecurityUtil.authUserId());
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all": // getAttribute("userId")
            default:
                log.info("getAll");

                // might be done try - parse, in  case date = kfadsghl
                // might switch to "post", so no need in the above try block, .../meals in browser, BUT
                // need to either:
                // 1. add "action" to processPost method or
                // 2. change the realization of /meals all, may be add FilteredServlet mapped to /filtered
                // don't want to spend time on that

                LocalTime startTime = request.getParameter("startTime") == null || request.getParameter("startTime").isEmpty() ?
                        LocalTime.MIN : LocalTime.parse(request.getParameter("startTime"));
                LocalTime endTime = request.getParameter("endTime") == null || request.getParameter("endTime").isEmpty() ?
                        LocalTime.MAX : LocalTime.parse(request.getParameter("endTime"));

                LocalDate startDate = request.getParameter("startDate") == null || request.getParameter("startDate").isEmpty() ?
                        LocalDate.MIN : LocalDate.parse(request.getParameter("startDate"));
                LocalDate endDate = request.getParameter("endDate") == null || request.getParameter("endDate").isEmpty() ?
                        LocalDate.MAX : LocalDate.parse(request.getParameter("endDate"));

//                request.setAttribute("meals",
//                        MealsUtil.getWithExceeded(service.getAll(SecurityUtil.authUserId()), MealsUtil.DEFAULT_CALORIES_PER_DAY));

                request.setAttribute("meals",
                        MealsUtil.getFilteredWithExceeded(service.getAll(SecurityUtil.authUserId()), MealsUtil.DEFAULT_CALORIES_PER_DAY,
                                startTime, endTime, startDate, endDate));

                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

    public Collection<Meal> getAllTest() {
        return service.getAll(1);
    }
}

