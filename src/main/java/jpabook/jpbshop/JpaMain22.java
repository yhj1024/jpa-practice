package jpabook.jpbshop;

import jpabook.jpbshop.domain.Team;
import jpabook.jpbshop.domain.jpa19domain.Address19;
import jpabook.jpbshop.domain.jpa19domain.Member19;

import javax.persistence.*;
import java.util.List;

public class JpaMain22 {

    public static void main(String[] args) {

        // 프로젝션
        // SELECT 절에 조회할 대상을 지정하는 것
        
        // 프로젝션 대상 : 엔티티, 임베디드 타입, 스칼라 타입 (숫자, 문자등 기본 데이터 타입)
        
        // SELECT m FROM Member m : 엔티티 프로젝션
        // SELECT m.Team From Member m : 엔티티 프로젝션
        // SELECT m.address From Member m : 임베디드 타입 프로젝션
        // SELECT m.username, m.age FROM Member m : 스칼라 타입 프로젝션

        // DISTINCT 로 중복 제거

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{

            Member19 m = new Member19();
            m.setUsername("테스트유저");
            em.persist(m);

            em.flush();
            em.clear();

            List<Member19> members = em.createQuery("select m from Member19 m", Member19.class).getResultList();
            Member19 findMember = members.get(0);

            findMember.setUsername("테스트유저22"); // update가 나간다 즉, 영속성 컨텍스트에서 관리가 된다는 얘기

            List<Team> teams = em.createQuery("select m.team from Member19 m", Team.class).getResultList();
            // team 조회 시 SQL은 JOIN 형태로 나간다
            // 근데 직관적이지 않아서 아래 처럼 쓰는게 낫다
            List<Team> teams2 = em.createQuery("select t from Member19 m join m.team t", Team.class).getResultList();

            em.createQuery("select m.address From Member19 m", Address19.class).getResultList();
            // address 는 임베디드 타입이라 바로 조회 가능 (db에는 어차피 Member 안에 address 필드가 포함되어 있으니)

            em.createQuery("select distinct m.username, m.id From Member19 m").getResultList();
            // 이런 식으로 기본 데이터 타입을 가져오는건 스칼라 프로젝션 이라고 함
            // 타입이 없어 받을 때가 좀 애매한데 우선 배열로 넘어온다

            List resultList = em.createQuery("select distinct m.username, m.id From Member19 m").getResultList();
            Object o = resultList.get(0);
            Object[] res = (Object[]) o; // 타입 캐스팅을 한번 해줘야 함
            System.out.println(res[0]);
            System.out.println(res[1]);

            // 좀 더 간단하게 하면
            List<Object[]> resultList2 = em.createQuery("select distinct m.username, m.id From Member19 m").getResultList();
            Object[] res2 = resultList2.get(0);
            System.out.println(res2[0]);
            System.out.println(res2[1]);

            // 근데 이 방법도 조금 아쉬운 부분이 있다 그나마 깔끔한 방법
            // 단순 값을 DTO로 바로 조회
            // SELECT new jpabook.jpql.UserDTO(m.username, m.age) FROM Member m
            // 패키지 명을 포함한 전체 클래스명 입력
            // 순서와 타입이 일치하는 생성자 필요
            List<MemberDTO> resultList3 = em.createQuery("select new jpabook.jpbshop.MemberDTO(m.username, m.id) From Member19 m", MemberDTO.class).getResultList();
            MemberDTO res3 = resultList3.get(0);
            System.out.println(res3.getUsername());
            System.out.println(res3.getId());


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
