package ru.javawebinar.topjava.model;

import ru.javawebinar.topjava.util.TimeUtil;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


//  Class for grouping meals by date and countin calories per date
//  Holds meals, taken and total calories for that meals
public class UserMealsWithTotalCaloriesHolder {
    int totalCalories;
    List<UserMeal> userMeals;


    public UserMealsWithTotalCaloriesHolder(UserMeal userMeal) {
        this.totalCalories = userMeal.getCalories();
        userMeals = new ArrayList<>();
        userMeals.add(userMeal);
    }

    public UserMealsWithTotalCaloriesHolder addMeal(UserMealsWithTotalCaloriesHolder userMeal) {
        this.totalCalories += userMeal.getTotalCalories();
        userMeals.addAll(userMeal.userMeals);
        return this;
    }

    public int getTotalCalories() {
        return totalCalories;
    }

    public List<UserMealWithExceed> getTimeFilteredUserMealsWithExceed(int limit, LocalTime startTime, LocalTime endTime) {

        return userMeals.stream()
                .filter(s -> TimeUtil.isBetween(s.getDateTime().toLocalTime(), startTime, endTime)) //filtering done here to reduce the number of newly created objects
                .map(x -> new UserMealWithExceed(x, totalCalories > limit))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "UserMealsWithTotalCaloriesHolder{" +
                "totalCalories=" + totalCalories +
                ", userMeals:" + userMeals +
                "}\n";
    }
}
