package jpg_basic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//@Entity // 어노테이션 추가 필요
//@Table(name = "USER") // 테이블 이름이 Member가 아니라 USER일 때
public class Member {

    @Id // JPA 한테 Key는 알려줘야 함
    private Long id;
    //    @Column(name="username") 맵핑할 컬럼명이 username 일 때 이렇게 하면 됨
    private String name;

    public Member() {
    }

    public Member(Long id, String name) { // 생성자 사용시 기본생성자가 반드시 필요
        this.id = id;
        this.name = name;
    }

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
