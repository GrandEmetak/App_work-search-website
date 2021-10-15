package ru.job4j.dream.model;

import java.util.Objects;

/**
 * 2. Scriplet. Динамическая таблица. [#276520]
 * Уровень : 3. Мидл Категория : 3.2.
 * Servlet JSPТопик : 3.2.2. JSP
 * path 1
 * @author SlartiBartFast-art
 * @since 21.09.21
 */
public class Post {
    private int id;
    private String name;

    public Post(int id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return id == post.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Post{"
                + "id=" + id
                + ", name='" + name + '\''
                + '}';
    }
}
