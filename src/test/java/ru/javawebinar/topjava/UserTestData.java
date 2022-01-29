package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Collections;

import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQUENCE;

public class UserTestData {
    public static final MatcherFactory.Matcher<User> USER_MATCHER = MatcherFactory.usingIgnoringFields("registered", "roles");
    public static final int USER_ID = START_SEQUENCE;
    public static final int ADMIN_ID = START_SEQUENCE + 1;
    public static final int NOT_FOUND = 10;

    public static final User user = new User(USER_ID, "user", "user@gmail.com", "userPass", Role.USER);
    public static final User admin = new User(ADMIN_ID, "admin", "admin@gmail.com", "adminPass", Role.ADMIN);

    public static User getNew() {
        return new User(null, "new", "new@gmail.com", "newPass", Role.USER);
    }

    public static User getUpdated() {
        User updated = new User(user);
        updated.setName("updated");
        updated.setPassword("updatedPass");
        updated.setEmail("updated@gmail.com");
        updated.setEnabled(false);
        updated.setCaloriesPerDay(333);
        updated.setRoles(Collections.singletonList(Role.ADMIN));
        return updated;
    }
}
