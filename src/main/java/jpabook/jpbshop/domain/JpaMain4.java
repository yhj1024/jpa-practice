package jpabook.jpbshop.domain;

import jpabook.jpbshop.domain.refact.OrderRefact;
import jpabook.jpbshop.domain.refact.OrderItemRefact;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain4 {

    // 연관관계 매핑 실습2

    // 테이블 구조

    // 테이블 구조는 이전과 같다

    // MEMBER
    // MEMBER_ID
    // NAME
    // CITY
    // STREET
    // ZIPCODE
    // [일대다]
    // ORDERS
    // ORDER_ID
    // MEMBER_ID
    // ORDERDATE
    // STATUS
    // [일대다]
    // ORDER_ITEM
    // ORDER_ITEM_ID
    // ORDER_ID
    // ITEM_ID
    // ORDERPRICE
    // COUNT
    // [다대일]
    // ITEM
    // ITEM_ID
    // NAME
    // PRICE
    // STOCKQUANTITY

    // 참조를 사용하도록 바꿔본다
    // MEMBER
    // List<orders>
    // ORDERS
    // member
    // List<orderItems>
    // ORDER_ITEM
    // Orderorder
    // ITEM

    // 실제 refact 에 만들어본다
    // 단방향을 기본으로 설계하고 양방향은 개발 중 필요할때 추가한다.
    // 이 때 연관관계 주인을 먼저 설정하는게 편함

    // 이제 양방향 매핑을 설정,
    // 근데 사실 Order 안에 Member만 있으도 된다
    // 굳이 Member에 Order가 들어갈 필요가 없다
    // 객체 관점으로 Member에서 Order, Order에서 Member 왔다갔다 하지만
    // 이렇게하면 끝도 없고, 설계 시 필요없는건 끊어내어야 하는데, 끊어내질 못했다고 판단
    // 하지만 연습상 추가 해본다

    // 이제 실제 활용

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            // 주문을 한다고 하면
            OrderRefact order = new OrderRefact();
            // addOrderItem 로 양방향 연관관계를 딱 잡는다
            // 이러면 주문을 만들고 주문 아이템을 쭉쭉 만들 수 있다
            order.addOrderItem(new OrderItemRefact());

            // 아니면 그냥 아래처럼 써도 된다
            // 양방향 연관관계가 반드시 필요한것은 아니다
//            Order order = new Order();
//            em.persist(order);
//
//            OrderItem orderItem = new OrderItem();
//            orderItem.setOrder(order);
//            em.persist(orderItem);

            // 단방향으로 할 수 있으면 단방향으로
            // 근데 나중에 개발중 편의성이나 JPQL 쓰려면 양방향 필요하다

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }


}
