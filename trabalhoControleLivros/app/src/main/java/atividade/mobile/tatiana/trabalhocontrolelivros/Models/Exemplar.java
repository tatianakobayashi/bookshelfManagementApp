package atividade.mobile.tatiana.trabalhocontrolelivros.Models;

import java.io.Serializable;
import java.util.Objects;

import atividade.mobile.tatiana.trabalhocontrolelivros.Enums.BookType;

public class Exemplar implements Serializable {
    private int id;
    private Book book;
    private User user;
    private Status status;
    private Publisher publisher;
    private int edition;
    private int pages;
    private int currentPage;
    private BookType bookType;
    private int timesRead;
    private int timesLent;
    private String coverImage;
    private String language;
    private String comments;
    private float classificationLast;
    private float classificationMean; // no setter
    private int timesClassificated;

    // Construtor para criação de exemplar
    public Exemplar(Book book, User user, Status status, Publisher publisher, int edition,
                    int pages, BookType bookType, String language) {
        this.book = book;
        this.user = user;
        this.status = status;
        this.publisher = publisher;
        this.edition = edition;
        this.pages = pages;
        this.bookType = bookType;
        this.language = language;
    }

    // Construtor para objetos tirados do banco de dados
    public Exemplar(int id, Book book, User user, Status status, Publisher publisher,
                    int edition, int pages, int currentPage, BookType bookType, int timesRead, int timesLent,
                    String coverImage, String language, String comments, float classificationLast, float classificationMean, int timesClassificated) {
        this.id = id;
        this.book = book;
        this.user = user;
        this.status = status;
        this.publisher = publisher;
        this.edition = edition;
        this.pages = pages;
        this.currentPage = currentPage;
        this.bookType = bookType;
        this.timesRead = timesRead;
        this.timesLent = timesLent;
        this.coverImage = coverImage;
        this.language = language;
        this.comments = comments;
        this.classificationLast = classificationLast;
        this.classificationMean = classificationMean;
        this.timesClassificated = timesClassificated;
    }

    public int getId() {
        return this.id;
    }

    public Book getBook() {
        return this.book;
    }

    public User getUser() {
        return this.user;
    }

    public Status getStatus() {
        return this.status;
    }

    public Publisher getPublisher() {
        return this.publisher;
    }

    public int getEdition() {
        return this.edition;
    }

    public int getPages() {
        return this.pages;
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public BookType getBookType() {
        return this.bookType;
    }

    public int getTimesRead() {
        return this.timesRead;
    }

    public int getTimesLent() {
        return this.timesLent;
    }

    public String getCoverImage() {
        return this.coverImage;
    }

    public String getLanguage() {
        return this.language;
    }

    public String getComments() {
        return this.comments;
    }

    public float getClassificationLast() {
        return this.classificationLast;
    }

    public float getClassificationMean() {
        return this.classificationMean;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setBookType(BookType bookType) {
        this.bookType = bookType;
    }

    public void setTimesRead(int timesRead) {
        this.timesRead = timesRead;
    }

    public void setTimesLent(int timesLent) {
        this.timesLent = timesLent;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setClassificationLast(float classificationLast) {
        this.classificationLast = classificationLast;
        timesClassificated++;
        if (timesClassificated == 1) {
            classificationMean = this.classificationLast;
        } else {
            updateMean();
        }
    }

    private void updateMean() {
        classificationMean = (classificationMean * (timesClassificated - 1) + classificationLast) / timesClassificated;
    }

    public int getTimesClassificated() {
        return timesClassificated;
    }

    public void setTimesClassificated(int timesClassificated) {
        this.timesClassificated = timesClassificated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exemplar exemplar = (Exemplar) o;
        return id == exemplar.id &&
                edition == exemplar.edition &&
                pages == exemplar.pages &&
                Objects.equals(book, exemplar.book) &&
                Objects.equals(user, exemplar.user) &&
                Objects.equals(publisher, exemplar.publisher) &&
                bookType == exemplar.bookType &&
                Objects.equals(language, exemplar.language);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, book, user, publisher, edition, pages, bookType, language);
    }

    @Override
    public String toString() {
        return id + " - Exemplar de " + book.getTitle() + ", editora " + publisher.getName() + ", " + language;
    }
}
