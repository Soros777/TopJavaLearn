package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserMealUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = List.of(
                new UserMeal(LocalDateTime.of(2021, Month.JANUARY, 30, 8, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2021, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2021, Month.JANUARY, 30, 19, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2021, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2021, Month.JANUARY, 31, 8, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2021, Month.JANUARY, 31, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2021, Month.JANUARY, 31, 19, 0), "Ужин", 410)
        );
        List<UserMealWithExcess> filteredMeals = filterByStreams(meals, LocalTime.of(9, 20), LocalTime.of(14, 0), 2000);

        filteredMeals.forEach(System.out::println);
    }

    private static List<UserMealWithExcess> filterByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> daysCalories = new HashMap<>();
        meals.forEach(meal -> daysCalories.merge(meal.getDate(), meal.getCalories(), Integer::sum));
        List<UserMealWithExcess> result = new ArrayList<>();
        meals.forEach(meal -> {
            if(DateTimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime)){
                result.add(mealTo(meal, daysCalories.get(meal.getDate()) > caloriesPerDay));
            }
        });
        return result;
    }

    private static List<UserMealWithExcess> filterByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        final Map<LocalDate, Integer> daysCalories = meals.stream().collect(Collectors.groupingBy(UserMeal::getDate, Collectors.summingInt(UserMeal::getCalories)));
        return meals.stream()
                .filter(meal -> DateTimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime))
                .map(meal -> mealTo(meal, daysCalories.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static UserMealWithExcess mealTo(UserMeal meal, boolean excess) {
        return new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}
