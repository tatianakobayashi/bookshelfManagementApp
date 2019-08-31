package atividade.mobile.tatiana.trabalhocontrolelivros.Models;

import java.io.Serializable;
import java.util.Objects;

public class Status implements Serializable {
    private int id;
    private String name;

    public Status(String name) {
        this.name = name;
    }

    public Status(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Status status = (Status) o;
        return id == status.id &&
                Objects.equals(name, status.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return id + " - " + name;
    }
}
