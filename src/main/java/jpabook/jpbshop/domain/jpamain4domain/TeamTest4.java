package jpabook.jpbshop.domain.jpamain4domain;

import jpabook.jpbshop.domain.Member2;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TeamTest4 {

    @Id @GeneratedValue
    @Column(name="TEAM_ID")
    private Long id;

    private String name;

    @OneToMany
    @JoinColumn(name="TEAM_ID") // 이러면 얘가 연관 관계의 주인이 된다
    private List<MemberTest4> members = new ArrayList<>();
    // new Array List는 관례 (null pointer 방지)


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MemberTest4> getMembers() {
        return members;
    }

    public void setMembers(List<MemberTest4> members) {
        this.members = members;
    }
}
