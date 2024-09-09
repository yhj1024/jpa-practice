package jpabook.jpbshop.domain.jpa8domain;

import javax.persistence.Entity;

@Entity
public class Jpa8Book extends Jpa8Item {

    private String author;
    private String isbn;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
