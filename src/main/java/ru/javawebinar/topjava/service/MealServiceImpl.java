package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;
import java.util.Collections;

@Service
public class MealServiceImpl implements MealService {

    private MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meal save(Meal meal) {
        Meal result = repository.save(meal);
        if (result == null) throw new NotFoundException("Save failed");
        return result;
    }

    @Override
    public boolean delete(int id, int userId) {
        boolean result = repository.delete(id, userId);
        if (!result) throw new NotFoundException("Delete failed");
        return result;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal result = repository.get(id, userId);
        if (result == null) throw new NotFoundException("Get failed");
        return result;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        Collection<Meal> result = repository.getAll(userId);
        if (result == null || result.isEmpty()) throw new NotFoundException("getAll failed");
        return result;
    }
}