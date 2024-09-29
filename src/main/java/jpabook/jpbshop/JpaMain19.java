package jpabook.jpbshop;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain19 {

    public static void main(String[] args) {
        // 값 타입 매핑 실전 예제

        // member
        // id, username, city, street, zipcode
        // delivery
        // id, city, street, zipcode, order (그냥 생략)

        // 여기서 의미있게 뺄 수 있는게 city, street, zipcode 를
        // address embedded 타입으로 분리
        // jpa19domain

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

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
