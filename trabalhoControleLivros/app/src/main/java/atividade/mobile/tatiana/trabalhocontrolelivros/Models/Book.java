package atividade.mobile.tatiana.trabalhocontrolelivros.Models;

import java.io.Serializable;
import java.util.Objects;

public class Book implements Serializable {
    private int id;
    private String title;
    private String genre;

    public Book(String title, String genre) {
        this.title = title;
        this.genre = genre;
    }

    public Book(int id, String title, String genre) {
        this.id = id;
        this.title = title;
        this.genre = genre;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getGenre() {
        return this.genre;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return id + " - " + title +  ", " + genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id &&
                Objects.equals(title, book.title) &&
                Objects.equals(genre, book.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, genre);
    }
}
