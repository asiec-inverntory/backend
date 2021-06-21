package ru.centralhardware.asiec.inventory;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SpringContext {

    private static ApplicationContext context;

    @Autowired
    public SpringContext(ApplicationContext applicationContext) {
        context = applicationContext;
    }

    public static <T> T getBean(@NotNull Class<T> clazz) {
        return context.getBean(clazz);
    }

}
