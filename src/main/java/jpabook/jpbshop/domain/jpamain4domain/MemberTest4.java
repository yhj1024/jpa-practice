package jpabook.jpbshop.domain.jpamain4domain;

import javax.persistence.*;

@Entity
public class MemberTest4 {

    // Member는 깔끔하게 id랑 name만 가진다
    @Id @GeneratedValue(strategy = GenerationType.AUTO) // 생략 시 오토임
    @Column(name = "MEMBER_ID")
    private Long id;
    private String name;

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
}
