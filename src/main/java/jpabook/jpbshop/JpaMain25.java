package jpabook.jpbshop;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain25 {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        // 나이가 평균 보다 많은 회원
        // select m from Member m
        // where m.age > (select avg(m2.age) from Member m2)

        // 한 건이라도 주문한 고객
        // select m from Member m
        // where (select count(o) from Order o where m = o.member) > 0

        // 서브 쿼리 - 예제 ( exists, all, any(some), in )
        // 팀A 소속인 회원
        // select m from Member m
        // where exists (select t from m.team t where t.name = '팀A')

        // 전체 상품 각각의 재고보다 주문량이 많은 주문들
        // Order Product
        // select o from Order o
        // where o.orderAmount > ALL (select p.stockAmount from Product p)

        // 어떤 팀이든 팀에 소속된 회원
        // any == some
        // select m from Member m
        // where m.team = ANY(select t from Team T)

        // JPA 는 WHERE, HAVING 절에서만 서브 쿼리 사용 가능 (표준 스펙에서만)
        // SELECT 절도 가능 (하이버네이트에서 지원)
        // FROM 절의 서브쿼리는 현재 JPQL에서 불가능 (조인으로 풀 수 있으면 풀어서 해결)
        // 그래도 안되면 네이티브 SQL, 다 가져와서 JAVA 로직 처리, 쿼리 두번 날리기

        try{
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            emf.close();
        }
        em.close();


    }

}
