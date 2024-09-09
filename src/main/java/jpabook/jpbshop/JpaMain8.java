package jpabook.jpbshop;

import jpabook.jpbshop.domain.jpa8domain.Jpa8Album;
import jpabook.jpbshop.domain.jpa8domain.Jpa8Item;
import jpabook.jpbshop.domain.jpa8domain.Jpa8Movice;

import javax.persistence.*;

public class JpaMain8 {

    public static void main(String[] args) {
        // 상속 관계 매핑

        // 관계형 데이터베이스는 상속관계 X
        // 슈퍼타입 서브타입 관계라는 모델링 기법이 객체 상속과 유사
        // 상속관계 매핑 : 객체의 상속과 구조와 db의 슈퍼타입 서브타입 관계를 매핑

        // DB의 슈퍼타입 서브타입 관계
        // 물품
        // 음반, 영화, 책

        // 객체
        // Item
        // Album, Movice, Book

        // 상속관계 매핑
        // 슈퍼타입 서브타입 논리 모델을 실제 물리 모델로 구현하는 방법

        // 조인 전략 (굉장히 정규화 된 방식, JPA와 가장 유사함)
        // ITEM 테이블을 만들고 ALBUM, MOVICE, BOOK 각 테이블에 ITEM_ID를 둬서 조인으로 들고 오는 방법
        // INSERT 2번, 조회할 때는 조인으로 그리고 ITEM에는 DTYPE을 가지고 있도록

        // 단일 테이블 전략 (성능, 단순성 때문에 고려하기도 함)
        // ITEM, ITEM_ID, NAME, PRICE, ARTIST, DIRECTOR, ACTOUR, AUTHOR, ISBN, DTYPE
        // 한 테이블에 다 넣는 방법

        // 구현 클래스마다 테이블 전략
        // ALBUM, MOVIE, BOOK 각 테이블을 만들고
        // NAME, PRICE, ARTIST 를 각각 들고 만드는 방식

        // DB는 위 3가지 구현 방법이 있는데,
        // JPA는 어떤 방법이든 객체 매핑이 가능하다

        // jpa8domain 엔티티 생성

        // 단순히 상속만 하는 경우 한 테이블에 다 몰아넣게 만들어진다
        // 이거를 먼저 조인 전략으로 하려면
        // 부모에 @Inheritance(strategy = InheritanceType.JOINED) 를 설정한다
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Jpa8Movice movie = new Jpa8Movice();
            movie.setActor("AAAA");
            movie.setDirector("BBBB");
            movie.setName("바람과 함께 사라지다");
            movie.setPrice(10000);
            em.persist(movie);

            em.flush();
            em.clear();

//            Jpa8Movice findMovie = em.find(Jpa8Movice.class, movie.getId());
            Jpa8Item item = em.find(Jpa8Item.class, movie.getId());
            System.out.println(item);

//            Jpa8Album album = new Jpa8Album();
//            album.setArtist("아티스트");
//            album.setPrice(5000);
//            album.setName("앨범");
//            em.persist(album);

            // 이러면 아이템의 DTYPE 이 생략됨
            // ITEM에 DiscriminatorColumn 추가를 해줘야한다.
            // 이러면 자동으로  DTYPE이 생성이 됨
            // 그리고 DTYPE 에 Movie 라는게 자동으로 (엔티티명) 이 들어감
            // 없어도 되지만 이게 왠만하면 있는게 좋다
            // 옵션으로 name으로 직접 컬럼명을 설정할 수 있다

            // 그리고 DTYPE에 들어가는 명칭은 자식으로 가서 DiscriminatorValue 를 추가해서 설정 가능하다

            // ========= 싱글 테이블 전략 @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
            // ITEM 테이블에 다 때려 박고 DTYPE으로 구분만 하는거
            // 조인 전략에서 인헤리턴스 스트레티지만 바꿔주면 됨
            // insert 한번에 가능하고 조인도 필요없으니 성능이 가장 좋다
            // 그리고 디스크리미네이터가 없어도 자동으로 DTYPE이 생성이 됨 (필수이기 때문)
            // JPA 가 좋은게 조인 전략을 쓰다가 단일 테이블로 변경하면 그냥 쿼리 변경없이 다 처리 가능


            // ========= 단일 테이블 전략
            // ITEM 테이블 없이 중복되게 테이블 마다 넣는 방식
            // 인헤리턴스타입을 TABLE_PER_CLASS 로 하면 됨
            // 그리고 abstract 클래스로 만들어야 함 (원래 처음부터 추상클래스로 해야됨)
            // 근데 이거의 단점은
            // 상속 관계니 부모 클래스로도 조회할 수 있어야 하는데
            // Item item = em.find(Item.class, movie.getId());
            // 이러면 찾을때 테이블을 union all 로 다 합쳐서 조회해야함
            // 굉장히 비효율적임 item_id로 찾으려면 세 테이블을 다 확인해봐야함

            // 각각의 장단점
            // 조인 전략 (기본적으로 정석)
            // 장점 : 정규화, 정산 시 ITEM만 봐도 된다, 외래키 참조 무결성 제약 조건 활용 가능, 저장 공간 효율화
            // 단점 : 조회 시 조인을 많이 사용, 성능 저하, 조회 쿼리가 복잡함 (테이블이 많아지니 당연히 복잡해진다), 데이터 저장 시 INSERT SQL 두번 나감

            // 단일 테이블 전략
            // 장점 : 조인이 필요 없다, 쿼리가 단순 하다
            // 단점 : 자식 엔티티의 컬럼들은 다 널을 허용해야한다, 단일 테이블에 모든 것을 저장 하므로 테이블이 커질 수 있고 상황에 따라 오히려 성능이 느려질 수 있다

            // 구현 클래스마다 테이블 전략
            // 결론부터 얘기하면 이거는 쓰면 안 된다
            // db, 객체 관점 뭘로 봐도 좋은 전략이 아님
            // 장점 : 서브 타입을 명확하게 구분해서 처리할 때 효과적, not null 제약 조건 사용 가능
            // 단점 : 여러 자식 테이블을 함께 조회할 때 쿼리 성능이 느림

            // 정리
            // 관계형 DB는 상속 관계가 없음
            // 슈퍼타입 서브타입 관계라는 모델링 기법이 객체와 유사
            // 그리고 조인 전략, 단일 테이블 전략, 구현 클래스마다 테이블 전략이 있다
            // 주요 어노테이션
            // @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
            // JOINED : 조인 전략
            // SINGLE_TABLE : 단일 테이블 전략
            // TABLE_PER_CLASS : 구현 클래스마다 테이블 전략
            // DiscriminatorColumn(name="디타입네임")
            // DiscriminatorValue("명칭")
            
            // 기본적으로 조인 전략을 가져간다
            // 그리고 단일 테이블 전략 두 개를 고민한다
            // 데이터가 단순하고 확장 가능성이 없다고 하면 단일 테이블 전략을 선택한다
            // 근데 비즈니스 적으로 복잡하고 중요하면 조인 테이블 전략을 사용한다
            
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
