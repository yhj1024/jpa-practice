package jpabook.jpbshop;

import jpabook.jpbshop.domain.jpa18domain.Address18Domain;
import jpabook.jpbshop.domain.jpa18domain.Address18DomainEntity;
import jpabook.jpbshop.domain.jpa18domain.Member18Domain;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class JpaMain18 {

    public static void main(String[] args) {

        // 값 타입 컬렉션
        // Member
        // id
        // favoriteFoods : Set<String>
        // addressHistory : List<Address>
        // 이게 DB 테이블로 구현할때가 문제가 된다.

        // 컬렉션은 결국 1대다 개념
        // DB는 MEMBER FAVOTIRE_FOOD, ADDRESS 별도의 테이블로 일대다로 구현해야함

        // 그리고 각 값들을 PK 로 묶어 버려야함 개별적으로 PK를 ADDRESS_ID 가지면 값 타입이 아니라 엔티티가 되어버림
        // 컬렉션 인터페이스는 기본적으로 다 사용 가능함
        // jpa18domain 참고

        // 만들고 나면 ADDRESS 테이블에 MEMBER_ID 와 생성한 domain 하위 필드가 생성됨

        // 값 타입 컬렉션
        // 값 타입을 하나 이상 저장할 때 사용
        // @ElementCollection, @CollectionTable 사용
        // 데이터 베이스는 컬렉션을 같은 테이블에 저장할 수 없다 그래서 일대다 테이블로 풀고 member_id같은 외래키를 가져야한다
        // 컬렉션을 저장하기 위한 별도의 테이블이 필요함
        
        // 아래 실제 사용

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{

            Member18Domain member = new Member18Domain();
            member.setUsername("member1");
            member.setHomeAddress(new Address18Domain("homeCity", "street", "10000"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");

            member.getAddressHistory().add(new Address18DomainEntity("old1", "street", "10000"));
            member.getAddressHistory().add(new Address18DomainEntity("old2", "street", "10000"));

            em.persist(member);

            // 흥미로운 점은 favorite, address history 따로 persist 없이 잘됨 (homeaddress 는 embedded 타입이라 원래 잘 되는 것)
            // 값 타입(컬렉션) 이기 때문에 생명주기가 member를 그대로 따라감

            em.flush();
            em.clear();

            System.out.println(" ============ start =========== ");
            Member18Domain findMember = em.find(Member18Domain.class, member.getId());

            // SQL 보면 값 타입 컬렉션은 조회가 안 되는것을 알 수 있음 (기본적으로 지연 로딩)
            // 근데 Embedded 는 바로 불러옴

            // 이러면 이제 쿼리가 나가게 된다
            // 이런건 당연히 Lazy 로 쓰는게 좋다
            List<Address18DomainEntity> addressHistory = findMember.getAddressHistory();
            for(Address18DomainEntity address18Domain : addressHistory) {
                System.out.println("addr city is " + address18Domain.getAddress18Domain().getCity());
            }
            Set<String> favoriteFoods = findMember.getFavoriteFoods();
            for(String food : favoriteFoods) {
                System.out.printf("favorite food is %s %n", food);
            }

            // 수정 예시
            // 이렇게 해도 Update 돌긴 하는데 값 타입은 사이드 이펙트가 생길 수 있음
//            findMember.getHomeAddress().setCity("newCity");

            // 따라서 반드시 아래 처럼 이뮤터블하게 처리를 해야한다
            findMember.setHomeAddress(new Address18Domain("newCitry", findMember.getHomeAddress().getStreet(), findMember.getHomeAddress().getZipCode()));
            
            // 이번에는 Set<String> 인데
            // set으로 처리할 방법이 없다
            // 따라서 아래처럼 지우고 add 해야한다
            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("한식");
            
            // 이번에는 addressHistory 차례
            // equals 를 기준으로 비교를 함 그래서 이거를 제대로 넣어야된다
            // 이거를 제대로 안 넣으면 안 지워짐
            findMember.getAddressHistory().remove(new Address18DomainEntity("old1", "street", "10000"));
            findMember.getAddressHistory().add(new Address18DomainEntity("newCity1", "street", "10000"));

            // 그리고 실행된 쿼리를 보면
            // delete -> insert -> insert 가 나가게 된다
            // 그리고 delete가 member id 에 포함된게 전부 삭제되고
            // 새로 insert 가 된다

            // 값 타입 컬렉션은 영속성 전에 (Cascade) + 고아 객체 제거 기능을 필수로 가진다고 볼 수 있다

            // 값 타입 컬렉션의 제약사항
            // 값 타입은 엔티티와 다르게 식별자 개념이 없다
            // 값은 변경하면 추적이 어렵다
            // * 값 타입 컬렉션에 변경사항이 발생하면 주인 엔티티와 연관된 모든 데이터를 삭제하고,
            // 값 타입 컬렉션에 있는 현재 값을 모두 다시 저장한다
            // 값 타입 컬렉션을 매핑하는 테이블은 모든 컬럼을 묶어서 기본 키를 구성해야 함 : null 입력, 중복 저장 x
            
            // 회원이 데이터가 10개 있음 -> 값 변경 발생 -> 10개 다 제거
            // 남은 컬렉션 다시 전부 insert 처리

            // -> 결론 : 안 쓰는게 낫다 (대안이 있긴함 OrderColumn 넣고 막 하면 되긴함
            // 컬렉션 순서 값을 넣어서 이거를 PK로 같이 잡음
            // 이럼 될거 같은데 위험하다 의도한대로 동작이 잘 안됨)

            // 그래서 이렇게 복잡할게 쓸거면
            // 실무에서는 상황에 따라 값 타입 컬렉션 대신에 일대다 관계를 고려
            // 일대다 관계를 위한 엔티티를 만들고, 여기에 값 타입을 사용
            // 영속성 전이 (Cascade) + 고아 객체 제거를 사용해서 값 타입 컬렉션 처럼 사용

            // 그래서 이거는 언제 쓰냐? 아주 단순한 select box 같은거에 치킨 피자 이런거 자주 안 바뀌고 들어갈때
            // 바뀌어도 상관없을때 그럴때 써야한다

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
