package ru.job4j.dream.model;

import java.util.Objects;

/**
 * 1. HttpSession [#6869]
 * Уровень : 3. МидлКатегория : 3.2. Servlet JSP Топик : 3.2.6. Filter, Security
 * Когда браузер отправляет запрос в tomcat создается объект HttpSession.
 * Этот объект связан с работой текущего пользователя.
 * Если вы открете другой браузер и сделаете новый запрос, то tomcat создаст еще один объект HttpSession,
 * который уже будет связан с другим пользователем.
 * В объекте HttpSession можно хранить информацию о текущем пользователе.
 */
public class User {
    private int id;
    private String name;
    private String email;
    private String password;

    public User() {
    }

    public User(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
