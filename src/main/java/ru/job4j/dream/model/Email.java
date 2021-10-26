package ru.job4j.dream.model;

/**
 * 4.1. JSON формат. [#238554 #210967]
 * Уровень : 3. Мидл Категория : 3.2. Servlet JSP Топик : 3.2.8. JS, JQuery, Ajax
 * В этом уроке рассмотрим как для передачи данных можно использовать формат JSON.
 * Добавим библиотеку для сериализации GSON:<dependency>...</dependency>
 * Добавим модель Email, которую мы будем сериализовать/десериализовать:
 */
public class Email {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
