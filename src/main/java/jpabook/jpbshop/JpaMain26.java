package jpabook.jpbshop;

import jpabook.jpbshop.domain.Member;
import jpabook.jpbshop.domain.jpa19domain.Member19;
import jpabook.jpbshop.domain.jpa19domain.MemberType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain26 {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        // JPQL 타입 표현
        // 문자: 'HEELO', 'She''s' 싱글 쿼테이션 이용 싱글 쿼테이션 넣을 떄는 싱클 쿼테이션 두개
        // 숫자: 10L (Long), 10D (Double), 10F (Float)
        // Boolean: TRUE, FALSE
        // ENUM: jpabook.MemberType.Admin (패키지명 포함) (+// 기본이 ORDINAL 이라 주의 필요 STRING 으로 바꿔서 쓰는게 안전)
        // 엔티티 타입: TYPE(m) = Member (상속 관계에서 사용)

        try{
            Member19 member19 = new Member19();
            member19.setUsername("하이");
            member19.setType(MemberType.ADMIN);

            em.persist(member19);
            em.flush();
            em.clear();

            String query = "select m.username, 'HELLO', TRUE from Member19 m " +
                           "where m.type = :userType";
            List<Object[]> result = em.createQuery(query).setParameter("userType", MemberType.ADMIN).getResultList();

            // 엔티티 타입 예시 (상속 관계에서 다형성 처럼 사용 할 수 있음)
            // Item 밑에 Album, Book, Movie 가 있는 상황이고 Book 만 조회하고 싶으면
            // select i from Item i where type(i) = Book


            for (Object[] objects : result) {
                System.out.println("objects = " + objects[0]);
                System.out.println("objects = " + objects[1]);
                System.out.println("objects = " + objects[2]);
            }

            // JPQL 기타
            // SQL과 문법이 같은 식
            // EXISTS, IN
            // AND, OR, NOT
            // =, >, >=, <, <=, <>
            // BETWEEN, LIKE, IS NULL

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
