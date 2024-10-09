package jpabook.jpbshop;

import jpabook.jpbshop.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain23 {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{


            for(int i=0; i<100; i++) {
                Member member = new Member();
                member.setName("홍길동" + i);
                member.setAge(i);
                em.persist(member);
            }
            em.flush();
            em.clear();
            // 페이징 API
            // JPA는 페이징을 다음 두 API로 추상화
            // setFirstResult(int startPosition) : 조회 시작 위치 0 부터 시작
            // setMaxResults (int maxResult) : 조회할 데이터 수

            List<Member> resultList = em.createQuery("select m from Member m order by m.age desc", Member.class)
                    .setFirstResult(0)
                    .setMaxResults(10)
                    .getResultList();

            // LIMIT OFFSET 쿼리가 나가는데 이는 H2 방언
            // 이 때 Oracle12cDialect 로 바꿔도 오라클 스타일로 페이징 처리를 해준다

            System.out.println(resultList.size());
            for(Member memeber1 : resultList) {
                // toString 만들때 양방향 조심해야됨
                // Member Team 서로 ToString 때문에 양쪽으로 무한반복 조회 stackoverflow 발생
                System.out.println(memeber1.toString());
            }

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
