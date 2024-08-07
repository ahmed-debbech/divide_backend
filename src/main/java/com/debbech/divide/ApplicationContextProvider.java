package com.debbech.divide;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextProvider {
    private static ApplicationContext context;

    @Autowired
    public ApplicationContextProvider(ApplicationContext ac) {
        context = ac;
    }

    public static ApplicationContext getApplicationContext() {
        return context;
    }
}