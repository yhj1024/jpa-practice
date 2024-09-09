package jpabook.jpbshop.domain.jpa8domain;

import javax.persistence.Entity;

@Entity
public class Jpa8Album extends Jpa8Item {

    private String artist;

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
