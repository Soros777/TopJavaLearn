package ru.javawebinar.topjava.web.user;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.User;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class ProfileRestController extends AbstractUserController {

    public void update(User user) {
        super.update(user, authUserId());
    }

    public void delete() {
        super.delete(authUserId());
    }

    public User get() {
        return super.get(authUserId());
    }
}
