package jpabook.jpbshop;

import jpg_basic.Member5;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain34 {

    // Named 쿼리 - 정적 쿼리
    // 미리 정의해서 이름을 부여해두고 사용하는 JPQL
    // 정적 쿼리만 가능하다
    // 어노테이션이나, XML 에 정의 할 수 있다
    // 애플레이션 로딩 시점에 파싱해서 메모리에 보관한다
    // 애플리케이션 로딩 시점에 쿼리를 검증할 수 있다 (엄청난 장점)
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Member5 member5 = new Member5();
        member5.setUsername("테스트");
        member5.setAge(13);
        em.persist(member5);

        em.flush();
        em.clear();

        List<Member5> l = em.createNamedQuery("Member5.findByUsername", Member5.class).setParameter("username", "테스트").getResultList();

        for (Member5 m : l) {
            System.out.println(m.getUsername());
        }

    }

}
