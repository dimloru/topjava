package ru.javawebinar.topjava.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class JpaMealRepositoryImpl implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {


        if (meal.isNew()) {
            meal.setUser(em.getReference(User.class, userId));
            em.persist(meal);
            return meal;
        }

        ValidationUtil.checkNotFound(get(meal.getId(), userId), "While updating");

        User ref = em.getReference(User.class, userId);
        meal.setUser(ref);

        return em.merge(meal);
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        User ref = em.getReference(User.class, userId);

        return em.createQuery("DELETE FROM Meal m WHERE m.id=:id AND m.user=:user")
                .setParameter("id", id)
                .setParameter("user", ref)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        User ref = em.getReference(User.class, userId);

        // Changed Query to TypedQuery
        TypedQuery<Meal> q = em.createQuery("SELECT m FROM Meal m WHERE m.id=:id AND m.user=:user", Meal.class);

        List<Meal> meals = q
                .setParameter("id", id)
                .setParameter("user", ref)
                .getResultList();

        return  DataAccessUtils.singleResult(meals);
    }

    @Override
    public List<Meal> getAll(int userId) {
        User ref = em.getReference(User.class, userId);

        return em.createNamedQuery(Meal.GET_All, Meal.class)
                .setParameter("user", ref)
                .getResultList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        User ref = em.getReference(User.class, userId);

        return em.createNamedQuery(Meal.GET_BETWEEN, Meal.class)
                .setParameter("user", ref)
                .setParameter("start", startDate)
                .setParameter("end", endDate)
                .getResultList();
    }
}