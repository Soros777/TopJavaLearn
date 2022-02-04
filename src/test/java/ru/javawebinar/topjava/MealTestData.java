package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.temporal.ChronoUnit;
import java.util.List;

import static java.time.LocalDateTime.of;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQUENCE;

public class MealTestData {
    public static final MatcherFactory.Matcher<Meal> MEAL_MATCHER = MatcherFactory.usingIgnoringFields();
    public static final int NOT_FOUND = 11;
    public static final int MEAL1_ID = START_SEQUENCE + 2;
    public static final int ADMIN_MEAL1_ID = START_SEQUENCE + 9;

    public static final Meal meal1 = new Meal(MEAL1_ID, of(2021, 1, 30, 8, 0), "Завтрак", 500);
    public static final Meal meal2 = new Meal(MEAL1_ID + 1, of(2021, 1, 30, 13, 0), "Обед", 1000);
    public static final Meal meal3 = new Meal(MEAL1_ID + 2, of(2021, 1, 30, 19, 0), "Ужин", 500);
    public static final Meal meal4 = new Meal(MEAL1_ID + 3, of(2021, 1, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal meal5 = new Meal(MEAL1_ID + 4, of(2021, 1, 31, 8, 0), "Завтрак", 500);
    public static final Meal meal6 = new Meal(MEAL1_ID + 5, of(2021, 1, 31, 13, 0), "Обед", 1000);
    public static final Meal meal7 = new Meal(MEAL1_ID + 6, of(2021, 1, 31, 19, 0), "Ужин", 410);
    public static final Meal adminMeal1 = new Meal(ADMIN_MEAL1_ID, of(2015, 7, 15, 8, 0), "Завтрак админ", 500);
    public static final Meal adminMeal2 = new Meal(ADMIN_MEAL1_ID + 1, of(2015, 1, 31, 8, 0), "Завтрак админ", 500);

    public static final List<Meal> meals = List.of(meal7, meal6, meal5, meal4, meal3, meal2, meal1);

    public static Meal getNew() {
        return new Meal(null, of(2018, 7, 20, 8, 30), "Created meal", 300);
    }

    public static Meal getUpdated() {
        return new Meal(MEAL1_ID, meal1.getDateTime().plus(15, ChronoUnit.MINUTES), "Updated meal", 700);
    }
}
