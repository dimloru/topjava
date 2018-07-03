package ru.javawebinar.topjava.dao;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class MealDataMemoryStorageImpl implements MealData {
    private static final Logger log = getLogger(MealDataMemoryStorageImpl.class);

    private Map<Integer, Meal> meals = new ConcurrentHashMap() {
        {
            put(0, new Meal(0, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
            put(1, new Meal(1, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
            put(3, new Meal(3, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
            put(4, new Meal(4, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
            put(7, new Meal(7, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
            put(8, new Meal(8, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
        }
    };
    AtomicInteger index = new AtomicInteger(9);

    public List<Meal> getMeals() {
//        return Arrays.asList(
//                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
//                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
//                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
//                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
//                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
//                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
//        );
        return new ArrayList(meals.values());
    }

    public int getNextId() {
        return index.getAndIncrement();
    }

//    @Override
//    public void add(LocalDateTime ldt, String description, int calories) {
////        meals.put(getNextId(), new Meal(getNextId(), ldt, description, calories));
//        put(-1, ldt, description, calories);
//        log.debug("DB added");
//    }

    @Override
    public void add(Meal meal) {
        int id = index.getAndIncrement();
        meal.setId(id);
        meals.put(id, meal);
    }

    @Override
    public void delete(int id) {
        meals.remove((Integer)id);
        log.debug("Deleting " + id);
    }

    @Override
    public Meal getById(int id) {
        return meals.get(id);
    }

//    @Override
//    public void put(int id, LocalDateTime ldt, String description, int calories) {
//        if (id < 0) id = index.getAndIncrement();
//        meals.put(id, new Meal(id, ldt, description, calories));
//        log.debug("Meal pud into db");
//    }

    @Override
    public void put(int id, Meal meal) {
        meals.put(id, meal);
    }
}
