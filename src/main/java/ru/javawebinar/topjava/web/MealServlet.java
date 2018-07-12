package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.mock.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceImpl;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

//    private MealRepository repository;
//    private MealService mealService;
//    private MealRestController mealRestController;

    private ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-app.xml");
    private MealRestController mealRestController = context.getBean(MealRestController.class);


//    @Override
//    public void init(ServletConfig config) throws ServletException {
//        super.init(config);
//        repository = new InMemoryMealRepositoryImpl();
//        mealService = new MealServiceImpl(repository);
//        mealRestController = new MealRestController(mealService);
//    }


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        ServletContext servletContext = config.getServletContext();
        servletContext.setAttribute("appCtx", context);
}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        mealRestController.processPost(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        mealRestController.processGet(request, response);
    }

    @Override
    public void destroy() {
        context.close();
        super.destroy();
    }

    //    private int getId(HttpServletRequest request) {
//        String paramId = Objects.requireNonNull(request.getParameter("id"));
//        return Integer.parseInt(paramId);
//    }
}
