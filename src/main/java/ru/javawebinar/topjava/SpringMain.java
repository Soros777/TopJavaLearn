package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository;

import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        System.out.println(Arrays.toString(appCtx.getBeanDefinitionNames()));

//        InMemoryUserRepository userRepository = (InMemoryUserRepository) appCtx.getBean("inMemoryUserRepository");
        InMemoryUserRepository userRepository = appCtx.getBean(InMemoryUserRepository.class);
        userRepository.getAll();
        appCtx.close();
    }
}
