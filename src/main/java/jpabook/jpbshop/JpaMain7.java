package jpabook.jpbshop;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaMain7 {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        // 실습

        // 회원, 주문, 배송, 주문상품, 상품, 카테고리 테이블이 있다고 한다

        // 회원과 주문은 1:* 일대다
        // 주문과 배송은 1:1 일대일
        // 주문과 주문상품은 *:* 다대다
        // 주문상품과 상품은 *:1 다대일
        // 카테고리와 물품은 *:* 대다대

        // 주문과 배송은 주문이 주 테이블로하여 PK 를 가지게끔 설계
        // 상품과 상품 카테고리는 일대다 다대일 구조로 변경하기 위해
        // 카테고리-상품 테이블을 만들어서 풀어낸다

        // 엔티티 생성

        // 일대일 관계는 외래 키를 양쪽 어디다 둬도 괜찮다
        // ORDERS에 두면 : 성능(바로 확인 가능, 나중에 프록시 등등) 객체 입장에서 편리함
        // DELIVERY에 두면: 1 -> N으로 확장이 편리함 (DB 컬럼 변경없이 N으로 변경 가능)
        // 다대다 관계 -> 테이블은 중간 테이블을 만들고 일대다 다대일 관계로 풀어야 한다.

        // ManyToOne 에는 mappedBy가 없음, 즉 다대일은 연관관계 주인이 되어야 한다는 것


    }

}
