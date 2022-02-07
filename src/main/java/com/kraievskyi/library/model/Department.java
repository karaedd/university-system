package com.kraievskyi.library.model;

import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    @ManyToMany
    private List<Lector> lectorList;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Department that = (Department) o;
        return Objects.equals(id, that.id)
                && Objects.equals(name, that.name)
                && Objects.equals(lectorList, that.lectorList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lectorList);
    }

    @Override
    public String toString() {
        return "Department{"
                + "id="
                + id
                + ", name='"
                + name
                + '\''
                + ", lectorList="
                + lectorList
                + '}';
    }
}
