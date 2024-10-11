package study.data_jpa.entitiies;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberTest {

    @Autowired
    private EntityManager em;

    @Test
    void testMember() {
        Team team1 = Team.builder().name("team1").build();
        Team team2 = Team.builder().name("team2").build();
        em.persist(team1);
        em.persist(team2);

        Member memberA = Member.builder().username("사용자1").age(10).team(team1).build();
        Member memberB = Member.builder().username("사용자2").age(20).team(team1).build();
        Member memberC = Member.builder().username("사용자3").age(30).team(team2).build();
        Member memberD = Member.builder().username("사용자4").age(40).team(team2).build();

        em.persist(memberA);
        em.persist(memberB);
        em.persist(memberC);
        em.persist(memberD);

        em.flush();
        em.clear();

        /*
        memberD.addTeam(team1);
        em.persist(memberD);
        em.flush();
        em.clear();
         */

        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
        members.forEach(System.out::println);
    }

}