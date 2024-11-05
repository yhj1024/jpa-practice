package jpabook.jpbshop.domain.jpa19domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team19 {

    @Id @GeneratedValue
    @Column(name="TEAM_ID")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team")
    private List<Member19> members = new ArrayList<>();
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

    public List<Member19> getMembers() {
        return members;
    }

    public void setMembers(List<Member19> members) {
        this.members = members;
    }

    public void setName(String name) {
        this.name = name;
    }
}
