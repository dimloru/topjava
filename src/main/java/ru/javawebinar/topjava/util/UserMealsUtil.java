package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.model.UserMealsWithTotalCaloriesHolder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 510),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 500)
        );

        // test console output
        for (UserMealWithExceed userMealWithExceed : getFilteredWithExceeded(mealList,
                LocalTime.of(7, 0), LocalTime.of(12,0), 2000)) {
            System.out.println(userMealWithExceed);
        }
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

          return mealList.stream()
                .collect(Collectors.toMap(
                        UserMeal::getDate,
                        UserMealsWithTotalCaloriesHolder::new,
                        UserMealsWithTotalCaloriesHolder::addMeal
                )).values().stream()
                .map(p -> p.getTimeFilteredUserMealsWithExceed(caloriesPerDay, startTime, endTime))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
