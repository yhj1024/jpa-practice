package jpg_basic;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity // JPA 에서 관리할 엔티티 라고 지정함 (name 이라는 속성이 있는데 기본값은 현재 클래스 명과 동일)
// 다른 패키지에 같은 클래스가 중복되는 경우 사용함 기본값을 대부분 씀)
//@Table(name = "USER") 

// name 속성 : 테이블 이름이 Member가 아니라 다른 명칭일때 으로 매핑할 테이블 이름을 지정할 수 있다
// catalog : 테이터 베이스 catalog 설정
public class Member2 {

    @Id // JPA 한테 Key는 알려줘야 함
    private Long id;
    //    @Column(name="username") 맵핑할 컬럼명이 username 일 때 이렇게 하면 됨
    private String name;

    public Member2() {
    }

    public Member2(Long id, String name) { // 생성자 사용시 기본생성자가 반드시 필요
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
