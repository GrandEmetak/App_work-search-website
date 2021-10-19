package ru.job4j.dream.model;

import java.util.Objects;

/**
 * 4. candidates.jsp - список кандидатов. [#276522]
 * Уровень : 3. Мидл Категория : 3.2. Servlet JSPТопик : 3.2.2. JSP
 * модель описывающую кандидата.
 *
 * @author SlartiBartFast-art
 * @since 22.09.21
 */
public class Candidate {
    private int id;
    private String name;

    public Candidate(int id, String name) {
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
        Candidate candidate = (Candidate) o;
        return id == candidate.id
                && Objects.equals(name, candidate.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Candidate{"
                + "id=" + id
                + ", name='" + name + '\''
                + '}';
    }
}
