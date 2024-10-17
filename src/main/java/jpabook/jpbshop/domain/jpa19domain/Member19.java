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

    @Enumerated(EnumType.STRING) // 기본이 ORDINAL 이라 주의 필요
    private MemberType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="TEAM_ID")
    private Team team;

    // 양방향 연관관계 편의 메서드
    public void changeTeam(Team team) {
//        this.team = team;
//        team.getMembers().add(this);
    }

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

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public MemberType getType() {
        return type;
    }

    public void setType(MemberType type) {
        this.type = type;
    }
}
