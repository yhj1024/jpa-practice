package jpabook.jpbshop.domain.jpa10domain;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;

@Entity
public class Album10 extends Item10 {

    private String artist;
    private String etc;

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
