package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);
    private MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal get(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("get {} for user {}", id, userId);
        return service.get(id, userId);
    }

    public Meal create(Meal meal) {
        int userId = SecurityUtil.authUserId();
        log.info("create {} for user {}", meal, userId);
        checkNew(meal);
        return service.create(meal, userId);
    }

    public Meal update(Meal meal) {
        int userId = SecurityUtil.authUserId();
        log.info("update {} for user {}", meal, userId);
        assureIdConsistent(meal, userId);
        return service.update(meal, userId);
    }

    public void delete(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("delete {} for user {}", id, userId);
        service.delete(id, userId);
    }

    public List<MealTo> getAll() {
        int userId = SecurityUtil.authUserId();
        log.info("get all for user {}", userId);
        return MealUtil.getTos(service.getAll(userId), SecurityUtil.authUserCaloriesPerDay());
    }

    /**
     * <ol>Filter separately
     *     <li>by date</li>
     *     <li>by time for every date</li>
     * </ol>
     */
    public List<MealTo> getBetween(@Nullable LocalDate startDate, @Nullable LocalDate endDate,
                                   @Nullable LocalTime startTime, @Nullable LocalTime endTime) {
        int userId = SecurityUtil.authUserId();
        log.info("get between dates ({} - {}) times ({} - {}) for user {}", startDate, endDate, startTime, endTime, userId);
        List<Meal> dateFilteredMeals = service.getBetweenInclusive(userId, startDate, endDate);
        return MealUtil.getFilteredTos(dateFilteredMeals, startTime, endTime, SecurityUtil.authUserCaloriesPerDay());
    }
}
