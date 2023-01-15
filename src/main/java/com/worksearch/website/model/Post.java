package com.worksearch.website.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

/**
 * 2. Scriplet. Динамическая таблица. [#276520]
 * Уровень : 3. Мидл Категория : 3.2.
 * Servlet JSPТопик : 3.2.2. JSP
 * path 1
 *
 * @author SlartiBartFast-art
 * @since 21.09.21
 */
public class Post {
    private int id;
    private String name;
    private String description;
    private String cityId;
    private String created;
    private LocalDateTime createDateTime;

    public Post(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Post(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Post(int id, String name, String description, String city) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cityId = city;
        this.createDateTime = LocalDateTime.now();
    }

    public Post(int id, String name, String description, String city, String created) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cityId = city;
        this.created = created;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
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
        return id == post.id
                && name.equals(post.name)
                && description.equals(post.description)
                && Objects.equals(cityId, post.cityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, cityId);
    }

    @Override
    public String toString() {
        return "Post{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", description='" + description + '\''
                + ", cityId='" + cityId + '\''
                + ", created='" + created + '\''
                + ", createDateTime=" + createDateTime
                + '}';
    }

    public static void main(String[] args) {
        Post post = new Post(1, "Валера", "beginner java engineer");
        System.out.println(post);
        Date date = new Date();
        System.out.println("Data " + date);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println(timestamp);
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);
    }
}
