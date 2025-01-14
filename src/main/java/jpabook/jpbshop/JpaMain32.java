package jpabook.jpbshop;

import jpabook.jpbshop.domain.jpa19domain.Member19;
import jpabook.jpbshop.domain.jpa19domain.Team19;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain32 {

    public static void main(String[] args) {

        // JPQL 다형성 쿼리

        // TYPE
        // item
        // album, movie, book 이 있는 다형성 구조가 있다고 해보자

        // 이 때 조회 대상을 트겅 자식으로 한정할 수 있다
        // select i from Item i where type(i) IN (Album, Movie)

        // sql 로 변환하면
        // select i from item i where i.dtype in ('A', 'M')

        // TREAT
        // 자바의 타입 캐스팅과 유사하다
        // 상속 구조에서 부모 타입을 특정 자식 타입으로 다룰 때 사용
        // FROM, WHERE, SELECT (하이버네이트 지원) 사용할 수 있다

        // select i from item i where treat(i as Album).artist = 'A'

        // sql 로 변환하면 (싱글테이블 일때)
        // select i from item i where i.dtype = 'A' and i.artist = 'A'

    }
}
