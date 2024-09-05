package jpabook.jpbshop;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaMain6 {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
    }

    // 다대다 [N:M]
    // 실무에서 쓰면 안 된다
    // 관계형 데이터베이스는 정규화된 테이블 2개로 다대다 관계를 표현할 수 없음
    // 연결 테이블을 추가해서 일대다, 다대일 관계로 풀어내야함
    // 예) 회원 - 상품
    // 이거를 회원 회원-상품 상품으로 다대일 관계로 풀어낸다 (일대다 다대일로 풀어냄)

    // 객체는 컬렉션을 사용해서 객체 2개로 다대다 관계 가능
    // 객체 컬렉션을 사용해서 이게 가능하다 여기서 딜레마가 온다

    // MemberDomain6, Product 에 MEMBER_PRODUCT 테이블

    // 편리해 보이지만 실무에서 사용 X
    // 연결 테이블이 단순히 연결만하고 끝나지 않음
    // 주문시간, 수량 같은 데이터가 들어올 수 있음
    // 중간 테이블이 숨겨져있기 때문에 어려워짐 중간에 들어오는게 너무 많아져 관리가 복잡함

    // 이거를 극복하기 위해 Order 라는 엔티티를 만들어서
    // 일대다 다대일 매핑으로 풀어내는게 낫다

    // 그리고 PK 잡을때 멤버, 상품 FK 두개로 묶어도 되는데
    // PK 의미없는 값으로 하나 새로 따는게 낫다 이러면 나중에 유연성이 생긴다

    // DB 관점에서는 FK두개로 묶는게 좋지만
    // 운영 유지 보수하다보면 시스템을 유연성있게 갈아치기 힘들다
    // 그래서 비즈니스 적으로 의미없는 값을 가지고 새로운 ID 를 다 까는 방식으로 많이 개발함

}
