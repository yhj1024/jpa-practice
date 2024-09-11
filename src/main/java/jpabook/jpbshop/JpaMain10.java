package jpabook.jpbshop;

import jpabook.jpbshop.domain.jpa10domain.Book10;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain10 {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

            // 요구사항 추가

            // 1. 상품의 종류는 음반, 도서, 영화가 있고 이 후 더 확장 될 수 있다
            // 2. 모든 데이터는 등록 일과 수정 일이 필수다

            // 1. 상속 관계 이용
            // 2. MappedSuperClass 사용

            // 회원 주문 배송 주문상품

            // 도메인 모델
            // 회원                카테고리
            // 1                  *
            // *                  *
            // 주문 1 * 주문상품*   1상품
            // 1
            // 1
            // 배송           ㄴ도서 상품의 상속관계
            //               ㄴ음반
            //               ㄴ영화

            // 테이블은 ITEM 테이블 하나로 설계

            // jpa10domain 만들기
            // 일단 아이템과 하위 먼저
            // 싱글테이블로 하면 테이블 하나에 다 넣어서 생성


            Book10 book = new Book10();
            book.setName("JPA");
            book.setAuthor("김영한");
            em.persist(book);
            // book 이 item 에 잘 저장된다

            // JOINED로 바꾸면 item이랑 book 각각 생성/저장됨

            // Base10Domain 만들기 MappedSuperclass
            // 사용할 테이블에 extend 해서 상속 받기

            // Album 같은건 Item에 들어가면 됨
            // 다대다 중간 테이블은 안 들어감 (쓰면 안 되는 이유 중 하나)

            // 결국 조인 전략과 싱글 테이블 둘 중에 고민이 필요한데
            // 조인 전략으로 하다가 데이터가 하루 백만건씩 쌓이고 이러면
            // 성능 문제로 싱글 테이블 전략으로 바꾸는 경우도 있다 (album, book 같은걸 json으로 말아놓고)

            // 기본은 조인 전략으로 하다가 성능 문제에 부딪히면 싱글 테이블 고려 필요

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();





    }
}
