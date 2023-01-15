package com.worksearch.website.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 4. candidates.jsp - список кандидатов. [#276522]
 * Уровень : 3. Мидл Категория : 3.2. Servlet JSPТопик : 3.2.2. JSP
 * модель описывающую кандидата.
 * 5. Реализация интерфейса [#2519]
 * Уровень : 3. МидлКатегория : 3.2. Servlet JSPТопик : 3.2.8. JS, JQuery, Ajax
 * Добавить в модель Candidate поле city_id.
 * 5 . Реализация интерфейса [#2519]10
 * Уровень : 3. МидлКатегория : 3.2. Servlet JSPТопик : 3.2.8. JS, JQuery, Ajax
 * переделываение модели данных Кандидат 26.10.21
 *
 * @author SlartiBartFast-art
 * @since 22.09.21
 */
public class Candidate {
    private int id;
    private String name;
    private String position;
    private String cityId;
    private String created;
    private LocalDateTime dateTime;

    public Candidate(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Candidate(int id, String name, String position, String cityId) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.cityId = cityId;
        this.dateTime = LocalDateTime.now();
    }

    public Candidate(int id, String name, String position, String cityId, String created) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.cityId = cityId;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Candidate candidate = (Candidate) o;
        return id == candidate.id
                && Objects.equals(name, candidate.name)
                && Objects.equals(position, candidate.position)
                && Objects.equals(cityId, candidate.cityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, position, cityId);
    }

    @Override
    public String toString() {
        return "Candidate{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", position='" + position + '\''
                + ", cityId='" + cityId + '\''
                + ", created=" + created
                + '}';
    }
}
