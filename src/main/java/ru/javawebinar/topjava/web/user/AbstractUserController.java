package ru.javawebinar.topjava.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

public abstract class AbstractUserController {
    protected final Logger log = LoggerFactory.getLogger(AbstractUserController.class);
    @Autowired
    private UserService service;

    public User create(User user) {
        log.info("create {}", user);
        checkNew(user);
        return service.create(user);
    }

    public void update(User user, int id) {
        log.info("update {} with id {}", user, id);
        assureIdConsistent(user, id);
        service.update(user);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    public User get(int id) {
        log.info("get user with id = {}", id);
        return service.get(id);
    }

    public User getByEmail(String email) {
        log.info("get by email ; {}", email);
        return service.getByEmail(email);
    }

    public List<User> getAll() {
        return service.getAll();
    }
}
