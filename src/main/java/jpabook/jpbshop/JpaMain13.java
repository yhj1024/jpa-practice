package jpabook.jpbshop;

import jpabook.jpbshop.domain.jpa14domain.Child;
import jpabook.jpbshop.domain.jpa14domain.Parent;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain13 {

    public static void main(String[] args) throws Exception {
        // 영속성 전이 : CASCADE
        // 특정 엔티티를 영속 상태로 만들 때 연관된 엔티티도 함께 영속 상태로 만들고 싶을 때
        // 예: 부모 엔티티를 저장할 때 자식 엔티티도 함께 저장
        // Parent1 *Child
        
        // Child, Parent 도메인 참고

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{

            Child child1 = new Child();
            Child child2 = new Child();

            Parent parent = new Parent();

            parent.addChild(child1);
            parent.addChild(child2);

            em.persist(parent);
            em.persist(child1);
            em.persist(child2);

            // persist 를 3번 해야된다 근데 이게 귀찮다
            // 현재 parent 중심으로 하고 있는데 child를 계속 persist를 해야한다
            // parent를 중심으로 child가 알아서 자동으로 관리되게 해주는게 Cascade

            // 부모만 persist 하면 child insert는 안 나감
            // 근데 옵션으로 cascade = CascadeType.ALL 주면 부모만 persist 해줘도
            // 자식들 insert가 함께 나감

            // 영속성 전이는 연관관계를 매핑하는 것과 아무 관련이 없음
            // 엔티티를 영속화 할 때 연관된 엔티티도 함께 영속화하는 편리함을 제공할 뿐

            // CASCADE 종류
            // ALL : 모두 적용
            // PERSIST : 영속
            // ===== 위 2개를 많이 사용
            // REMOVE : 삭제
            // MERGE : 병합
            // REFRESH : REFRESH
            // DETACH : DETACH

            // 하나의 부모가 자식을 관리하는 경우 유용할 수 있음
            // 예) 게시판과 파일 관계
            // 쓰면 안 되는 경우
            // 파일을 다른 엔티티에서 관리하는 경우

            // 즉 하나의 부모에서만 관리되는 child인 경우 사용 OK
            // 다른 데서 child에 영향이 있는 경우에는 사용하면 안됨
            // (단일 엔티티에 종속적일때)

            // 고아객체
            // 고아 객체 제거 : 부모 엔티티와 연관관계가 끊어진 자식 엔티티를 자동으로 삭제
            // orphanRemoval = true
            // Parent1 parent1 = em.find(Parent1.class, id);
            // parent1.getChildren().remove(0);
            // 자식 엔티티를 컬렉션에서 제거
            // DELETE FROM CHILD WHERE ID = ?

            // Parent orphanRemove 옵션 추가

            em.flush();
            em.clear();

            Parent findParent = em.find(Parent.class, parent.getId());
//            findParent.getChildList().remove(0); // delete 쿼리가 나간다
            
            // 함부로 쓰면 위험할 수 있음
            // 참조가 제거된 엔티티는 다른 곳에서 참조하지 않는 고아 객체로 보고 삭제 가능
            // !!이것도 참조하는 곳이 하나일 때 사용해야 한다
            // !!특정 엔티티가 개인 소유할 때 사용

            // OneToOne, OneToMany 만 가능
            // 개념적으로 부모를 제거하면 자식은 고아가 된다 따라서
            // 고아 객체 제거 기능을 활성화하면, 부모를 제거할 때 자식도 함게 제거된다
            // 이것은 CascadeType.REMOVE 처럼 동작한다 (ALL도 마찬가지)

             em.remove(findParent);

             // 영속성 전이 + 고아객체, 생명주기
            // CascadeType.ALL + orphanRemoval = true
            // 스스로 생명주기를 관리하는 엔티티는 em.persist()로 영속화, em.remove()로 제거
            // 두 옵션을 모두 활성화하면 부모 엔티티를 통해서 자식의 생명 주기를 관리할 수 있음
            // 도메인 주도 설계 (DDD) 의 Aggregate Root 개념을 구현할 때 유용
            // Aggregate Root : repository 는 Aggregate Root 만 만들고
            // 나머지는 만들지 않는다 (자식의 생명 주기는 Aggregate Root 에서 관리하는게 좋다는 내용)

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
