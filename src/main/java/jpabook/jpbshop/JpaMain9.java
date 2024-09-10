package jpabook.jpbshop;

import jpabook.jpbshop.domain.jpa8domain.Jpa8Item;
import jpabook.jpbshop.domain.jpa8domain.Jpa8Movice;
import jpabook.jpbshop.domain.jpa9domain.Member9Domain;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class JpaMain9 {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        // MappedSuperclass

        // 공통 매핑 정보가 필요할 때 사용(id, name)
        // 객체
        // Member (id, name, email)
        // Seller (id, name, shopName)
        // => BaseEntity (id, name)
        // ㄴ Member (email)
        // ㄴ Seller (shopName)
        
        // DB
        // MEMBER(ID,NAME,EMAIL)
        // SELLER(ID,NAME,SHOPNAME)
        
        // 여기서 ID랑 NAME이 반복되니
        // 이거를 편하게 하자 라고 할 때 싸용함
        
        // 예를 들어 DBA 가 모든 테이블에
        // 작성자 createdBy, 작성일 createdDate, 수정자 lastModifiedBy, 수정일 lastModifiedDate
        // 이거를 공통으로 넣으라고 한다
        // 이거를 다 복붙으로 넣기 귀찮고 너무 불필요한 반복이 너무 많다
        // 이거를 가능하게 하는게 MappedSuperclass 이다
        
        // baseEntity 클래스를 만들고
        // 엔티티에 상속을 받는 구조로 만든다
        // 그리고 상위 클래스에 MappedSuperclass 를 넣어주면 된다
        
        // @Column 해서 name 옵션으로 INSERT_MEMBER 이런 식으로 수정해서 사용도 가능
        // 상속관계 매핑 X
        // 엔티티X, 테이블과 매핑 X
        // 조회 검색 em.find(BaseEntity.class) 사용 불가
        // 직접 생성해서 사용할 일이 없으므로 추상 클래스 권장
        
        try {
            tx.begin();

            Member9Domain member9Domain = new Member9Domain();
            member9Domain.setName("홍길동");
            member9Domain.setCreatedBy("KIM");
            member9Domain.setCreatedDate(LocalDateTime.now());

            em.persist(member9Domain);
            em.flush();
            em.clear();

            tx.commit();
        } catch (Exception e) {
            System.out.println(e);
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

    }

}
