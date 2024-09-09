package jpabook.jpbshop;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaMain7 {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

    }





}
