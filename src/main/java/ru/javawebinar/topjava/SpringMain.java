package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;

import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));

//        InMemoryUserRepository userRepository = (InMemoryUserRepository) appCtx.getBean("inMemoryUserRepository");
//        InMemoryUserRepository userRepository = appCtx.getBean(InMemoryUserRepository.class);
//        userRepository.getAll();
        UserService userService = appCtx.getBean(UserService.class);
        userService.create(new User(1, "name", "email", "password", Role.ADMIN));
        appCtx.close();
    }
}
