package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MealUtil {
    public static List<Meal> MEALS = List.of(
            new Meal(LocalDateTime.of(2021, Month.JANUARY, 30, 8, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2021, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2021, Month.JANUARY, 30, 19, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2021, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Meal(LocalDateTime.of(2021, Month.JANUARY, 31, 8, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2021, Month.JANUARY, 31, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2021, Month.JANUARY, 31, 19, 0), "Ужин", 410)
    );
    public static int CALORIES_PER_DAY = 2000;

    public static void main(String[] args) {
        List<MealTo> filteredMeals = getFilteredTos(MEALS, LocalTime.of(9, 20), LocalTime.of(14, 0), CALORIES_PER_DAY);
        filteredMeals.forEach(System.out::println);
    }

    public static List<MealTo> getFilteredTos(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        return filterByPredicate(meals, caloriesPerDay, meal -> DateTimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime));
    }

    public static List<MealTo> getTos(List<Meal> meals, int caloriesPerDay) {
        return filterByPredicate(meals, caloriesPerDay, meal -> true);
    }

    public static List<MealTo> filterByPredicate(List<Meal> meals, int caloriesPerDay, Predicate<Meal> predicate) {
        final Map<LocalDate, Integer> daysCalories = meals.stream().collect(Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories)));
        return meals.stream()
                .filter(predicate)
                .map(meal -> mealTo(meal, daysCalories.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static MealTo mealTo(Meal meal, boolean excess) {
        return new MealTo(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}
