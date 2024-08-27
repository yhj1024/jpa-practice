package jpabook.jpbshop;

import jpabook.jpbshop.domain.Member;
import jpabook.jpbshop.domain.Order;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try{
            
            // ENTITY 에 index나, 제약 조건 같이 넣어 주면 개발자가 entity만 보고 파악할 수 있어 편리하다

            // 테이블 필드에 orderDate 이대로 생성되는데 자바는 카멜인데
            // DB는 보통 파스칼 order_date, ORDER_DATE 처럼 생성한다.
            // 스프링 부트에서는 스프링부트 JPA 를 걸어서 올리면 알아서 파스칼로 바꿔준다
            // (설정 기본 값)
            // Column으로 일일히 매핑 해주는 방법도 있음!

            // 어떤 주문을 가져온다고 하자
            Order order = em.find(Order.class, 1L);
            // 이제 주문을 한 멤버를 조회하고 싶다?
            Long memberId = order.getMemberId();
            // 주문에 멤버 아이디로 멤버를 조회해옴
            Member member = em.find(Member.class, memberId);

            // 이 과정이 객체지향 스럽지 않다.
            // order안에 member가 있어야 객체지향스러운 코드이다
            // order.getMember(); 이렇게 가져올 수 있어야 함

            // 그래서 이런 방식의 설계는 객체지향스럽지 않다.
            // 이거는 관계형 DB에 맞춘 설계이다

            // 데이터 중심의 설계 문제점

            // 현재 방식은 객체 설계를 테이블 설계에 맞춘 방식
            // 테이블의 외래키를 객체에 그대로 가져옴
            // 객체 그래프 탐색이 불가능
            // 참조가 없으므로 UML 도 잘못됨


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

}
