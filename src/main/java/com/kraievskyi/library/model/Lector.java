package com.kraievskyi.library.model;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Lector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String degree;
    private String name;
    private int salary;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Lector lector = (Lector) o;
        return salary == lector.salary
                && Objects.equals(id, lector.id)
                && Objects.equals(degree, lector.degree)
                && Objects.equals(name, lector.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, degree, name, salary);
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Lector{"
                + "id="
                + id
                + ", degree='"
                + degree
                + '\''
                + ", name='"
                + name
                + '\''
                + '}';
    }
}
