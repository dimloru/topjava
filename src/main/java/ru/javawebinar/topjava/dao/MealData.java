package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface MealData {

    List<Meal> getMeals();

//    void add(LocalDateTime ldt, String description, int calories);

    void add(Meal meal);

    void delete(int id);

    Meal getById(int id);

//    void put(int id, LocalDateTime ldt, String description, int calories);

    void put(int id, Meal meal);


}
