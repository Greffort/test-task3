package com.haulmont.testtask.config;

import com.haulmont.testtask.StartApplication;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/*
 * Defines a custom WebInitializer
 * @version 12.11.2020
 * Created by Aleksandr Kravchina
 */

public class WebInitializer extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(@NotNull SpringApplicationBuilder application) {
        return application.sources(StartApplication.class);
    }
}