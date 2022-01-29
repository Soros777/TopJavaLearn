package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.Util;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;


@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private final Map<Integer, InMemoryBaseRepository<Meal>> usersMealsMap = new ConcurrentHashMap<>();

    {
        /* it must work in such way too
        MealTestData.meals.forEach(meal -> save(meal, USER_ID));
        save(new Meal(LocalDateTime.of(2015, Month.JULY, 15, 8, 0), "Завтрак админ", 500), ADMIN_ID);
        save(new Meal(LocalDateTime.of(2015, Month.JULY, 15, 14, 0), "Обед админ", 1000), ADMIN_ID);
         */
        InMemoryBaseRepository<Meal> userMeals = new InMemoryBaseRepository<>();
        MealTestData.meals.forEach(meal -> userMeals.map.put(meal.getId(), meal));
        usersMealsMap.put(USER_ID, userMeals);
    }

    @PostConstruct
    public void postConstruct() {
        log.info("+++ postConstruct");
    }

    @PreDestroy
    public void preDestroy() {
        log.info("+++ preDestroy");
    }

    @Override
    public Meal save(Meal meal, int userId) {
        InMemoryBaseRepository<Meal> userMeals = usersMealsMap.computeIfAbsent(userId, uId -> new InMemoryBaseRepository<>());
        return userMeals.save(meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        InMemoryBaseRepository<Meal> userMeals = usersMealsMap.get(userId);
        return userMeals != null && userMeals.delete(id);
    }

    @Override
    public Meal get(int id, int userId) {
        InMemoryBaseRepository<Meal> userMeals = usersMealsMap.get(userId);
        return userMeals == null ? null : userMeals.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return filterByPredicate(userId, meal -> true);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(int userId, LocalDateTime start, LocalDateTime end) {
        return filterByPredicate(userId, meal -> Util.isBetweenHalfOpen(meal.getDateTime(), start, end));
    }

    private List<Meal> filterByPredicate(int userId, Predicate<Meal> filter) {
        InMemoryBaseRepository<Meal> userMeals = usersMealsMap.get(userId);
        return userMeals == null ? Collections.emptyList() :
                userMeals.getCollection().stream()
                        .filter(filter)
                        .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                        .collect(Collectors.toList());
    }
}
