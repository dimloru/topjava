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
import javax.persistence.Parameter;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
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
        User ref = em.getReference(User.class, userId);

        // Dont't see the point in using criteria here

        if (meal.isNew()) {
            meal.setUser(ref);
            em.persist(meal);
            return meal;
        }

        ValidationUtil.checkNotFound(get(meal.getId(), userId), "While updating");
        meal.setUser(ref);

        return em.merge(meal);
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        User ref = em.getReference(User.class, userId);

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaDelete<Meal> q = cb.createCriteriaDelete(Meal.class);
        Root m = q.from(Meal.class);
        q.where(
                cb.equal(m.get("id"), id),
                cb.equal(m.get("user"), ref));
        return em.createQuery(q).executeUpdate() != 0;


//        return em.createQuery("DELETE FROM Meal m WHERE m.id=:id AND m.user=:user")
//                .setParameter("id", id)
//                .setParameter("user", ref)
//                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        User ref = em.getReference(User.class, userId);

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Meal> q = cb.createQuery(Meal.class);
        Root<Meal> m = q.from(Meal.class);
        ParameterExpression<Integer> param = cb.parameter(Integer.class);
        q.select(m).where(
                cb.equal(m.get("id"), param),
                cb.equal(m.get("user"), ref));


        TypedQuery<Meal> query = em.createQuery(q);
        query.setParameter(param, id);

        List<Meal> meals = query.getResultList();

        /*
        // Old JPQL version

        // Changed Query to TypedQuery
        TypedQuery<Meal> q = em.createQuery("SELECT m FROM Meal m WHERE m.id=:id AND m.user=:user", Meal.class);

        List<Meal> meals = q
                .setParameter("id", id)
                .setParameter("user", ref)
                .getResultList();
        */

        return  DataAccessUtils.singleResult(meals);
    }

    @Override
    public List<Meal> getAll(int userId) {
        User ref = em.getReference(User.class, userId);

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Meal> q = cb.createQuery(Meal.class);
        Root<Meal> m = q.from(Meal.class);
        q.select(m);
        q.where(cb.equal(m.get("user"), ref));
        q.orderBy(cb.desc(m.get("dateTime")));

        TypedQuery<Meal> query = em.createQuery(q);
        return query.getResultList();


//        return em.createNamedQuery(Meal.GET_All, Meal.class)
//                .setParameter("user", ref)
//                .getResultList();
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