package jpabook.jpbshop;

import jpg_basic.Member5;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain35 {

    /*
    벌크 연산
     - PK 를 집어서 단건을 업데이트 딜리트 하는걸 제외한 모든 업데이트 딜리트 연산
     - JPA 변경 감지 기능으로 실행하려면 너무 많은 SQL이 실행된다 LIST 조회하고 가격 10% 증가 시키고 LIST만큼의 SQL이 실행된다
     - JPA는 실시간성에 더 가깝다 안 쓸순 없으니 이러한 기능이 제공된다
     */

    // 모든 회원의 나이를 20살로 바꾸는 예제

    public static void main(String[] args) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        Member5 member5 = new Member5();
        member5.setAge(12);
        member5.setUsername("홍길동");
        em.persist(member5);

        Member5 member6 = new Member5();
        member6.setAge(13);
        member6.setUsername("홍길동");
        em.persist(member6);

        Member5 member7 = new Member5();
        member7.setAge(14);
        member7.setUsername("홍길동");
        em.persist(member7);

        Member5 member8 = new Member5();
        member8.setAge(15);
        member8.setUsername("홍길동");
        em.persist(member8);

        Member5 member9 = new Member5();
        member9.setAge(16);
        member9.setUsername("홍길동");
        em.persist(member9);

        em.flush();
        em.clear();

        List<Member5> members = em.createQuery("select m from Member5 m", Member5.class).getResultList();

        for (Member5 member : members) {
            System.out.printf("이름 %s, 나이 %d \n",member.getUsername(), member.getAge());
        }

        int resultCount = em.createQuery("update Member5 m set m.age = 20 where m.id = :id")
                .setParameter("id", member6.getId())
                        .executeUpdate();

        System.out.println(resultCount);

        // 벌크 연산 주의
        // 벌크 연산은 영속성 컨텍스트를 무시하고 데이터베이스에 직접 쿼리
        // 따라서 아래 둘중에 한 가지 방법을 선택해서 사용해야된다
        // 벌크 연산을 먼저 실행
        // 벌크 연산 수행 후 영속성 컨텍스트 초기화

//        em.flush();
        // flush (영속성 컨텍스트 변경 사항 db에 반영 작업) 자동 호출
        em.clear();

        List<Member5> updatedMembers = em.createQuery("select m from Member5 m", Member5.class).getResultList();

        for (Member5 member : updatedMembers) {
            System.out.printf("이름 %s, 나이 %d \n",member.getUsername(), member.getAge());
        }

        tx.commit();
        
    }

}
