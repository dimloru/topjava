package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

public interface MealService {
    Meal save(Meal meal);

    boolean delete(int id, int i);

    Meal get(int id, int i);

    Collection<Meal> getAll(int i);
}