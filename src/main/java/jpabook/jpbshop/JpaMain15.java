package jpabook.jpbshop;

import jpabook.jpbshop.domain.jpa15domain.Address15Domain;
import jpabook.jpbshop.domain.jpa15domain.Member15Domain;
import jpabook.jpbshop.domain.jpa15domain.Period15Domain;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain15 {

    public static void main(String[] args) {

        // JPA 의 데이터 타입 분류

        // 엔티티 타입
        // @Entity 로 정의하는 객체
        // 데이터가 변해도 식별자로 지속해서 추적 가능
        // 예) 회원 엔티티의 키나 나이 값을 변경해도 식별자로 인식 가능

        // 값 타입
        // int, Intger, String 처럼 단순히 값으로 사용하는 자바 기본 타입이나 객체
        // 식별자가 없고 값만 있으므로 변경 시 추적 불가
        // 예) 숫자 100을 200으로 변경하면 완전히 다른 값으로 대체

        // 값 타입 분류
        // 기본값 타입 (프리미티브 타입, int, double), 래퍼 클래스(Integer, Long), String ....
        // 임베디드 타입 예) 좌표 Position 클래스를 만들어 관리
        // 컬렉션 값 타입 예) 자바 컬렉션에 기본값이나 임베디드 타입을 넣는 것

        // 기본값 타입 String name, int age 같은
        // 생명주기를 엔티티에 의존 예) 회원을 삭제하면 이름 나이 필드도 함께 삭제
        // 값 타입은 절대 공유하면 X 부수 효과가 일어나면 안된다
        // 예) 회원 이름 변경 시 다른 회원의 이름도 함께 변경되면 안됨
        // 기본값 타입은 그래서 a=20, b=a, a=10 을 하면
        // 값을 복사해서 괜찮은데 래퍼 클래스 같은 경우에는 레퍼런스가 넘어가기 때문에
        // 문제가 발생됨 Integer a = new Integer(20), b=a, a변경시 b도 변경됨
        // 그래서 변경을 못 하게 해서 막는 방법으로 해결한다 (불변객체)

        // 임베디드 타입
        // 새로운 값 타입을 직접 정의할 수 있음
        // JPA는 임베디드 타입이라함
        // 주로 기본 값 타입을 모아서 만들어서 복합 값 타입이라고도 함
        // int, String과 같은 타입

        // 회원 엔티티는 이름, 근무 시작일, 근무 종료일, 주소 도시, 주소 번지, 주소 우편번호를 가진다
        // 근무 시작일과 종료일은 뭔가 비슷함
        // 주소 관련된것도 뭔가 공통으로 묶어서 쓸 수 있음

        // 회원 엔티티는 이름, 근무기간, 집주소를 가진다라고 추상화해서 설명
        // Member => id, name, workPeriod, homeAddress
        // workPeriod => startDate, endDate
        // Address => city, street, zipcode

        // 쉽게 생각해서 클래스 두개를 새로 뽑는거
        // 장점 : 재사용, 높은 응집도, 해당 값 타입만 사용하는 의미있는 메소드 생성 가능
        // 임베디드 타입을 포함한 모든 값 타입은, 값 타입을 소유한 엔티티에 생명주기를 의존함

        // DB 입장에서는 39라인과 동일하게 설계됨
        // 객체는 jpa15domain 패키지

        // 임베디드 타입 사용법
        // @Embeddable : 값 타입을 정의하는 곳에 표시
        // @Embedded: 값 타입을 사용하는 곳에 표시
        // 기본 생성자 필수


        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            // 사용 예시
            Member15Domain member15Domain = new Member15Domain();
            member15Domain.setUsername("테스트");
            member15Domain.setHomeAddress(new Address15Domain());
            member15Domain.setWorkPeriod(new Period15Domain());
            em.persist(member15Domain);
            // 임베디드 타입은 엔티티의 값일 뿐이다
            // 임베디드 타입을 사용하기 전과 후에 매핑하는 테이블은 같다
            // 객체와 테이블을 아주 세밀하게 매핑하는 것이 가능
            // 잘 설계한 ORM 애플리케이션은 매핑한 테이블의 수보다 클래스의 수가 더 많음

            // 임베디드 안에 다른 엔티티가 들어올 수도 있다

            // 만약 주소가 homeAddress 말고 workAddress 가 있다고 해보자
            // 이러면 중복되어서 오류가 나는데 이 때 속성을 재정의 해주는
            // @AttributeOverrides , @AttributeOverride 를 이용해서 컬럼명 속성을 재정의 할 수 있다
            // (Member15Domain 엔티티 참고)

            // 임베디드가 Null 이면 매핑한 컬럼은 모두 Null

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
