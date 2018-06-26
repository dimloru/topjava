package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserMealWithExceed {
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final boolean exceed;

    public UserMealWithExceed(LocalDateTime dateTime, String description, int calories, boolean exceed) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.exceed = exceed;
    }

    public UserMealWithExceed(UserMeal userMeal, boolean exceed) {
        this.dateTime = userMeal.getDateTime();
        this.description = userMeal.getDescription();
        this.calories = userMeal.getCalories();
        this.exceed = exceed;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public LocalDate getDate() {
        return LocalDate.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth());
    }

    @Override
    public String toString() {
        return "UserMealWithExceed{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", exceed=" + exceed +
                '}';
    }
}
