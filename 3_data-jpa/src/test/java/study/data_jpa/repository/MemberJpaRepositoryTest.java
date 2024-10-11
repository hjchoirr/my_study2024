package study.data_jpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.data_jpa.entitiies.Member;
import study.data_jpa.entitiies.Team;

import java.util.List;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberJpaRepositoryTest {

    @Autowired
    private MemberJpaRepository repository; //순수 JPA

    @Autowired
    private TeamJpaRepository teamJpaRepository;

    @Test
    //@Commit
    void save() {
        Member member = Member.builder().username("memberB").build();
        Member saved = repository.save(member);

        Member found = repository.find(saved.getId());
        Assertions.assertThat(found.getId()).isEqualTo(saved.getId());
        Assertions.assertThat(found.getUsername()).isEqualTo(saved.getUsername());
        Assertions.assertThat(found).isEqualTo(saved);
    }

    @Test
    void basicCRUD() {

        Team team = Team.builder().name("teamB").build();
        teamJpaRepository.save(team);
        Member member = Member.builder().username("memberA").age(10).team(team).build();
        Member saved = repository.save(member);
        Member found = repository.findById(saved.getId()).get();
        //save 검증
        Assertions.assertThat(saved.getUsername()).isEqualTo(found.getUsername());

        //단건 조회 검증
        Member member1 = repository.findById(saved.getId()).get();
        Assertions.assertThat(member1.getUsername()).isEqualTo(saved.getUsername());

        //리스트 검증
        Member member2 = Member.builder().username("memberB").age(20).team(team).build();
        Member saved2 = repository.save(member2);

        List<Member> all = repository.findAll();
        Assertions.assertThat(all.size()).isEqualTo(2);

        //삭제 검증
        repository.delete(member2);

        Assertions.assertThat(repository.count()).isEqualTo(1);
    }

    @Test
    public void paging() {
        //given
        addTestData1();
        int age = 10;
        int offset = 3;
        int limit = 3;

        //when
        List<Member> members = repository.findByPage(age, offset, limit);
        long totalCount = repository.totalCount(age);

        //then
        for(Member member : members) {
            System.out.println(member);
        }
        Assertions.assertThat(members.size()).isEqualTo(1);
        Assertions.assertThat(totalCount).isEqualTo(4);
    }

    @Test
    public void bulkTest() {
        addTestData1();

        int age = 10;
        int plus = 2;
        int cnt = repository.bulkUpdate(10, 2);

        Assertions.assertThat(cnt).isEqualTo(4);

    }

    void addTestData1() {
        Team team = Team.builder().name("team1").build();
        teamJpaRepository.save(team);
        Member memberA = Member.builder().username("memberA").age(10).team(team).build();
        Member memberB = Member.builder().username("memberB").age(10).team(team).build();
        Member memberC = Member.builder().username("memberC").age(10).team(team).build();
        Member memberD = Member.builder().username("memberD").age(10).team(team).build();

        repository.save(memberA);
        repository.save(memberB);
        repository.save(memberC);
        repository.save(memberD);
    }

}