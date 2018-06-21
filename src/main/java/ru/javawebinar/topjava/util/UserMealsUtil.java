package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field

        Map<LocalDate, List<UserMeal>> mealByDate = mealList.stream().collect(Collectors.groupingBy(UserMeal::getDate));

        Map<LocalDate, Integer> caloriesByDate = mealList.stream()
                .collect(Collectors.groupingBy(UserMeal::getDate, Collectors.summingInt(UserMeal::getCalories)));

        Map<LocalDate, Integer> datesExceeds = caloriesByDate.entrySet().stream()
                .filter(e -> e.getValue() > caloriesPerDay)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return mealList.stream()
                .filter(s -> datesExceeds.keySet().contains(s.getDate())) //filtering meals in "exceed" days
                .filter(s -> LocalTime.of(s.getDateTime().getHour(), s.getDateTime().getMinute()).isAfter(startTime) &&
                        LocalTime.of(s.getDateTime().getHour(), s.getDateTime().getMinute()).isBefore(endTime))  //filtering meals in the defined time of day
                .map(s -> new UserMealWithExceed(s.getDateTime(), s.getDescription(), s.getCalories(), true)) // processing into dto
                .collect(Collectors.toList()); //collecting result

//                .collect(Collectors.groupingBy(UserMeal::getDateTime, Collectors.summingInt(UserMeal::getCalories)))




    }
}
