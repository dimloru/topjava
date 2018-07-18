package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.MealTestData.USER_MEALS;
import static ru.javawebinar.topjava.MealTestData.ADMIN_MEALS;

@ContextConfiguration({
        "classpath:spring/spring-db.xml",
        "classpath:spring/spring-app.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts  = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal actual = service.get(100002, 100000);
        Meal testMeal = new Meal(MealTestData.USER_MEALS.get(0));
//        testMeal.setDescription("Corrupted");  //thy done to test isEqual function. uses overriden .equals
        assertThat(actual).isEqualToIgnoringGivenFields(testMeal);
    }

    @Test
    public void delete() {
        service.delete(100008, 100001);
        List<Meal> all = service.getAll(100001);
        assertThat(service.getAll(100001)).containsOnly(ADMIN_MEALS.get(1));
    }

    @Test(expected = NotFoundException.class)
    public void hostileDelete() {
        service.delete(100002, 100001);
    }


    @Test
    public void getBetweenDates() {
        List<Meal> actual =         service.getBetweenDates(
                LocalDate.of(2015,5,31),
                LocalDate.of(2015, 5, 31),
                100000);
        assertThat(actual).containsOnly(USER_MEALS.get(3), USER_MEALS.get(4), USER_MEALS.get(5));
    }

    @Test
    public void getBetweenDateTimes() {
        List<Meal> actual = service.getBetweenDateTimes(
                LocalDateTime.of(2015, 5, 30, 10, 00),
                LocalDateTime.of(2015, 5, 30, 14, 00),
                100000
        );
        assertThat(actual).containsOnly(USER_MEALS.get(0), USER_MEALS.get(1));
    }

    @Test
    public void getAll() {
        assertThat(service.getAll(100000))
                .hasSameSizeAs(MealTestData.USER_MEALS)
                .hasSameElementsAs(MealTestData.USER_MEALS);
    }

    @Test
    public void update() {
        Meal updated = new Meal(USER_MEALS.get(0));
        updated.setDescription("Updated Meal");
        updated.setCalories(112);
        service.update(updated, 100000);
        assertThat(service.get(100002, 100000)).isEqualToIgnoringGivenFields(updated);
    }

    @Test(expected = NotFoundException.class)
    public void hostileUpdate() {
        Meal updated = new Meal(USER_MEALS.get(0));
        updated.setDescription("Hostile Invasion");
        updated.setCalories(666);
        service.update(updated, 100001);
        assertThat(service.get(100002, 100000)).isEqualToIgnoringGivenFields(updated);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(LocalDateTime.now(), "Some food", 555);
        service.create(newMeal, 100000);

        assertThat(service.getAll(100000))
                .containsAll(USER_MEALS)
                .contains(newMeal)
                .hasSize(USER_MEALS.size() + 1);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateTimeCreate() {
        Meal newMeal = new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак х2", 1000);
        service.create(newMeal, 100000);

    }
}