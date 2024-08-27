package jpg_basic;

public class Main09 {

    // 실전 예제
    // 요구 사항
    // 회원은 상품을 주문할 수 있다
    // 주문 시 여러 종류의 상품을 선택할 수 있다

    // 도메인 모델 분석
    // 회원과 주문의 관계 : 회원은 여러 번 주문할 수 있다 (일대다)
    // 주문과 상품의 관계 : 주문할 때 여러 상품을 선택할 수 있다. 반대로 같은 상품도 여러 번 주문될 수 있다
    // 주문 상품이라는 모델을 만들어서 다대다 관계를 일대다, 다대일 관계로 풀어냄

    // 회원1 <-> (다)주문(다) <-> 주문상품(다) -> 1상품

    // 테이블 설계
    // MEMBER1 <-> (다)ORDERS(다) <-> ORDER_ITEM(다) -> 1ITEM
    // MEMBER_ID   MEMBER_ID(FK)      ORDER_ITEM_ID   ITEM_ID
    // NAME        ORDER_ID           ORDER_ID        NAME
    // CITY        ORDERDATE          ITEM_ID         PRICE
    // STREET      STATUS             ORDERPRICE      STOCKQUANTITY
    // ZIPCODE                        COUNT

    // 엔터티 설계와 매핑
    // MEMBER1 <->          (다)ORDERS(다) <->        ORDER_ITEM(다) ->  1ITEM
    // id: Long             id: Long                 id: Long           id: Long
    // name: string         memberId: Long           orderId: Long      name: string
    // city: string         orderDate: Date          itemId: Long       price: int
    // street: string       status: OrderStatus      orderPrice: int    stockQuantity: int
    // zipcode: string                               count: int

    // jpabook 패키지 새로 생성하여 위 내용 진행
    // entity 패키지까지 만들고 여기에 테이블 쭉 생성

    
}

