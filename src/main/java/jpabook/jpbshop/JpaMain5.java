package jpabook.jpbshop;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaMain5 {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        // 일대일 관계
        // 일대일 관계는 그 반대도 일대일
        // 주 테이블이나 대상 테이블 중에 외래 키 선택 가능
        // 주 테이블에 외래 키
        // 대상 테이블에 외래 키
        // 외래 키에 데이터베이스 유니크 (UNI) 제약조건 추가

        // 회원이 사물함을 가진 경우
        // Member <-> Locker
        // id         id
        // Locker     name
        // username

        // 테이블 표현
        // MEMBER               LOCKER
        // MEMBER_ID            LOCKER_ID
        // LOCKER_ID(FK, UNI)   NAME
        // USERNAME

        // 다대일 단방향 매핑과 유사
        // 양방향으로 하고 싶다? LOCKER에 MEMBER 객체 추가 하면 됨

        // Locker에 Member_id가 있는 경우 대상 테이블에 외래 키가 있는 경우 JPA 단방향 지원 X
        // 양방향은 가능 LOCKER 가 주인으로 설정해서 양방향 매핑을 하면 됨

        // MEMBER가 LOCKER를 가질거냐 LOCKER가 MEMBER를 가질거냐?
        // DBA 입장
        // 시간이 지나서 회원이 라커를 여러개 가진다고 해보면
        // LOCKER가 MEMBER_ID를 가진 경우에는 변경이 쉬운데
        // MEMBER가 LOCKER_ID를 가진 경우에는 LOCKER 수정이 필요하고 손이 좀 더 많이감
        // 근데 반대로 라커가 여러 회원이 쓸 수 있으면 MEMBER가 LOCKER_ID를 가지는게 맞음
        // 개발자 입장
        // MEMBER를 조회해서 LOCKER_ID 유무로 뭔가 로직을 처리할 수 있어서 편리함
        // 성능상 이점

        // 일대일정리

        // 주 테이블에 외래키
        // 주 객체가 대상 객체의 참조를 가지는 것 처럼
        // 주 테이블에 외래 키를 두고 대상 테이블을 찾음
        // 객체지향 개발자 선호
        // JPA 매핑 편리
        // 장점 : 주 테이블만 조회해도 대상 테이블에 데이터가 있는지 확인 가능
        // 단점 : 값이 없으면 외래 키에 null 허용

        // 대상 테이블에 외래 키
        // 대상 테이블에 외래 키가 존재
        // 전통적인 데이터베이스 개발자 선호
        // 장점 : 주 테이블과 대상 테이블을 일대일에서 일대다 관계로 변경할 때 테이블 구조 유지
        // 단점 : 프록시 기능의 한계로 지연 로딩으로 설정해도 항상 즉시 로딩됨 (프록시는 뒤에서 설명)

        // 멤버가 라커 외래 키를 가진 경우 키 유무로 있다 없다 판단 가능
        // 근데 외래 키가 라커에 있는 경우, 라커 테이블까지 가서 멤버가 있는지 찾아야 됨
        // 이 때 쿼리가 어차피 다 나가기 때문에 프록시 기능이 의미가 없음

    }
}
