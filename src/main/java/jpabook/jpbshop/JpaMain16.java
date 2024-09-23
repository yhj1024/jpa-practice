package jpabook.jpbshop;

import jpabook.jpbshop.domain.jpa15domain.Address15Domain;
import jpabook.jpbshop.domain.jpa15domain.Member15Domain;
import jpabook.jpbshop.domain.jpa15domain.Period15Domain;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain16 {

    public static void main(String[] args) {

        // 값 타입은 복잡한 객체 세상을 조금이라도 단순화 하려고 만든 개념이다
        // 따라서 값 타입은 단순하고 안전하게 다룰 수 있어야 한다

        // 값 타입 공유 참조
        // int 같은건 안전한데, 임베디드 타입 같은 값 타입을 여러 엔티티에서 공유하면 위험함
        // 부작용 발생

        // 회원1
        //          ----> 주소 (city: OldCity)
        // 회원2

        // city를 바꾸면 회원1,2 가 다 바뀜

        // 같은 address 를 사용 하고 있음

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            // 예)
            Address15Domain address15Domain = new Address15Domain("city", "street", "10000");
//            Address15Domain address15DomainCopy = new Address15Domain(address15Domain.getCity(), address15Domain.getStreet(), address15Domain.getZipCode());

            Member15Domain member1 = new Member15Domain();
            member1.setUsername("member1");
            member1.setHomeAddress(address15Domain);
            em.persist(member1);

            Member15Domain member2 = new Member15Domain();
            member2.setUsername("member2");
            member2.setHomeAddress(address15Domain);
//            member2.setHomeAddress(address15DomainCopy);
            em.persist(member2);

            // 여기까지하면 member1, member2 둘다 똑같은 주소가 잘 저장됨
            
            
            // 이러고 member1 의 주소만 newCity로 변경을 하고 싶음
            member1.getHomeAddress().setCity("newCity");

            // 이러고 실행을 하면 update 쿼리가 두번이 나간다
            // 이렇게 임베디드 타입 같은 값 타입을 여러 엔티티에서 공유하면 위험함
            // 부작용 발생

            // 대신 값(인스턴스)를 복사해서 사용
//            Address15Domain address15DomainCopy = new Address15Domain(address15Domain.getCity(), address15Domain.getStreet(), address15Domain.getZipCode());
//            member2.setHomeAddress(address15DomainCopy);
            // 이러면 첫번째만 영향이 있고 두번째는 영향이 없는 걸 확인할 수 있다

            // 객체 타입의 한계
            // 항상 값을 복사해서 사용하면 공유 참조로 인해 발생하는 부작용을 피할 수 있다
            // 객체 타입은 참조 값을 직접 대입하는 것을 막을 방법이 없다.
            // 객체의 공유 참조는 피할 수 없다
            
            // 불변 객체
            // 객체 타입을 수정할 수 없게 만들면 부작용을 원천 차단
            // 값 타입은 불변 객체로 설계해야함
            // 불변 객체 : 생성 시점 이 후에 절대 값을 변경할 수 없는 객체
            // 생성자로만 값을 설정하고 수정자(Setter)를 만들지 않으면 됨
            // 참고: Integer, String은 자바가 제공하는 대표적인 불변 객체
            
            // 근데 값을 실제 바꾸고 싶으면?
            // 그냥 새로 만들어야 한다
////            Address15Domain address15DomainCopy = new Address15Domain("newCity", address15Domain.getStreet(), address15Domain.getZipCode());


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
