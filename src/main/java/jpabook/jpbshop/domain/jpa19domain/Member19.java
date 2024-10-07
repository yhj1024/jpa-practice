package jpabook.jpbshop.domain.jpa19domain;

import jpabook.jpbshop.domain.Team;

import javax.persistence.*;

@Entity
public class Member19 {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    @Embedded // 생략해도 되지만 명시적으로
    private Address19 address;

    @ManyToOne
    @JoinColumn(name="TEAM_ID")
    private Team team;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Address19 getAddress() {
        return address;
    }

    public void setAddress(Address19 address) {
        this.address = address;
    }
}
