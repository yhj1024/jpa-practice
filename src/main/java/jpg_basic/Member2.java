package jpg_basic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

//@Entity // JPA 에서 관리할 엔티티 라고 지정함 (name 이라는 속성이 있는데 기본값은 현재 클래스 명과 동일)
// 다른 패키지에 같은 클래스가 중복되는 경우 사용할 수도 있지만 기본값을 대부분 씀 JPA가 내부적으로 관리할 때 쓰는 이름이라고 생각 하면 됨)
//@Table(name = "USER") 

// name 속성 : 테이블 이름이 Member가 아니라 다른 명칭일때 으로 매핑할 테이블 이름을 지정할 수 있다
// catalog : 테이터 베이스 catalog 설정
public class Member2 {

    // 데이터베이스 스키마 자동 생성
    // DDL 을 애플리케이션 실행 시점에 자동 생성
    // DB를 처음에 만들어줌
    // 이러면 테이블 중심 -> 객체 중심이 됨 (원래는 테이블 만들고 시작하는데 객체만 짜면됨)
    // 데이터베이스 방언을 활용해서 적절한 DDL 생성
    // 운영 서버에서는 사용하지 않거나 적절히 다듬은 후 사용

    @Id // JPA 한테 Key는 알려줘야 함
    private Long id;
    //    @Column(name="username") 맵핑할 컬럼명이 username 일 때 이렇게 하면 됨
    @Column(unique = true, length = 10) // 유니크 및 길이 제약 조건 추가
    private String name;
    private int age; // 이러면 필드가 새로 추가된다.

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
