package jpabook.jpbshop;

import jpabook.jpbshop.domain.jpa15domain.Address15Domain;
import jpabook.jpbshop.domain.jpa15domain.Member15Domain;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain17 {

    public static void main(String[] args) {

        // 값 타입의 비교
        // 값 타입 : 인스턴스가 달라도 그 안에 값이 같으면 같은 것으로 봐야 함

        // 예 int a = 10 , int b = 10  a==b true 프리미티브 타입
        // Address a = new Address("서울")
        // Address b = new Address("서울")
        // a == b 당연히 false...

        // 그래서 equals 를 사용해야되고 override 를 해서 모든 필드를 비교하도록 만들어 주는게 낫다 (왠만하면 자동으로 생성)
        // 그리고 equals 쓰면 hashCode 도 override 해서 쓰는게 좋다

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
