package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

public class UserTestData {
    public static final int USER_ID = 1;
    public static final int ADMIN_ID = 2;
    public static final int NOT_FOUND = 10;

    public static final User user = new User(USER_ID, "user", "user@gmil.com", "userPass", Role.USER);
    public static final User admin = new User(ADMIN_ID, "admin", "admin@ukr.net", "adminPass", Role.ADMIN);
}
