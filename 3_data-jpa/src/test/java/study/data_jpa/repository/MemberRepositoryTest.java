package study.data_jpa.repository;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.data_jpa.dto.MemberDto;
import study.data_jpa.entitiies.Member;
import study.data_jpa.entitiies.Team;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private EntityManager em;

    void init() {
        Team team = Team.builder().name("teamA").build();
        teamRepository.save(team);
        Member member1 = Member.builder().username("member").age(9).team(team).build();
        Member member2 = Member.builder().username("member").age(10).team(team).build();
        Member member3 = Member.builder().username("member").age(12).team(team).build();

        List<Member> members = List.of(member1, member2, member3);
        memberRepository.saveAll(members);
    }
    @Test
    public void testMemberRepository() {
        Member member = Member.builder().username("member3").build();
        Member saved = memberRepository.save(member);
        Optional<Member> found = memberRepository.findById(member.getId());
        Assertions.assertThat(member.getId()).isEqualTo(found.get().getId());
        Assertions.assertThat(member.getUsername()).isEqualTo(found.get().getUsername());
        Assertions.assertThat(member).isEqualTo(saved);

    }

    @Test
    void basicCRUD() {

        Team team = Team.builder().name("teamB").build();
        teamRepository.save(team);
        Member member = Member.builder().username("memberA").age(10).team(team).build();
        Member saved = memberRepository.save(member);
        Member found = memberRepository.findById(saved.getId()).get();
        //save 검증
        Assertions.assertThat(saved.getUsername()).isEqualTo(found.getUsername());

        //단건 조회 검증
        Member member1 = memberRepository.findById(saved.getId()).get();
        Assertions.assertThat(member1.getUsername()).isEqualTo(saved.getUsername());

        //리스트 검증
        Member member2 = Member.builder().username("memberB").age(20).team(team).build();
        Member saved2 = memberRepository.save(member2);

        List<Member> all = memberRepository.findAll();
        Assertions.assertThat(all.size()).isEqualTo(2);

        //삭제 검증
        memberRepository.delete(member2);

        Assertions.assertThat(memberRepository.count()).isEqualTo(1);
    }

    @Test
    void testQueryMethod() {
        init();
        List<Member> found = memberRepository.findByUsernameAndAgeGreaterThanEqualOrderByUsername("member", 10);
        Assertions.assertThat(found.size()).isEqualTo(2);

        System.out.println(found);
    }
    @Test
    void testNamedQuery() {
        init();
        List<Member> found = memberRepository.findByUsername("member");
        System.out.println("found: " + found);
        Assertions.assertThat(found.size()).isEqualTo(3);
    }

    @Test
    void testJpql(){
        init();
        List<Member> found = memberRepository.findUser("member", 10);
        System.out.println("found: " + found);
    }

    @Test
    void testMemberDto() {
        init();
        List<MemberDto> memberDtos = memberRepository.findMemberDto("member");
        memberDtos.forEach(System.out::println);
    }

    @Test
    void testFindByNames() {

        addTestData();

        List<String> names = List.of("member1", "member2", "member3");
        List<Member> found = memberRepository.findByNames(names);
        for(Member f : found) {
            System.out.println(f);
        }
    }
    @Test
    void testPage() {

        addTestData();
        PageRequest pageRequest = PageRequest.of(3, 2, Sort.by(Sort.Direction.DESC, "age"));
        Page<Member> page = memberRepository.findByAgeGreaterThan(5, pageRequest);
        List<Member> members = page.getContent();
        System.out.println(page);

        System.out.println(("page.getTotalElements() : " + page.getTotalElements()));
        System.out.println(("page.getTotalPages() : " + page.getTotalPages()));
        System.out.println(("page.getContent() : " + page.getContent()));
        System.out.println(("page.getNumber() : " + page.getNumber()));
        System.out.println(("members.size() : " + members.size()));
        System.out.println(("page.getSize() : " + page.getSize()));
        System.out.println(("page.getNumberOfElements(): " + page.getNumberOfElements()));
        System.out.println(("page.getSort() : " + page.getSort()));
        System.out.println(("page.getPageable(): " + page.getPageable()));
        System.out.println(("page.nextOrLastPageable(): " + page.nextOrLastPageable()));
        System.out.println(("page.previousOrFirstPageable(): " + page.previousOrFirstPageable()));

        System.out.println("page.isFirst() : " + page.isFirst());
        System.out.println("page.isLast() : " + page.isLast());
        System.out.println("page.isLast() : " + page.isLast());
        System.out.println("page.hasNext() : " + page.hasNext());
        System.out.println("page.hasContent() : " + page.hasContent());


        Page<MemberDto> pageDto = page.map(m -> new MemberDto(m.getId(), m.getUsername(), m.getTeam().getName()));
        System.out.println(pageDto.getContent());
    }
    @Test
    void bulkUpdate() {
        addTestData2();
        int age = 10;
        int plus = 1;

        int cnt = memberRepository.bulkUpdate(age, plus);
        Assertions.assertThat(cnt).isEqualTo(4);
        //em.clear();   // @Modifying(clearAutomatically = true) repository에 명시 했으므로

        Member member3 = memberRepository.findByUsername("member3").get(0);
        Assertions.assertThat(member3.getAge()).isEqualTo(11);
    }

    @Test
    public void findLazy() {
        init();
        List<Member> members = memberRepository.findAll();
        //List<Member> members = memberRepository.findJoinFetch();

        System.out.println("members.get(0).getTeam().getName() = " + members.get(0).getTeam().getName());
        System.out.println("members.get(0).getTeam().getClass() = " + members.get(0).getTeam().getClass());
    }

    void addTestData() {
        Team team = Team.builder().name("teamB").build();
        teamRepository.save(team);
        Member member0 = Member.builder().username("member2").age(7).team(team).build();
        Member member1 = Member.builder().username("member2").age(7).team(team).build();
        Member member2 = Member.builder().username("member3").age(17).team(team).build();
        Member member3 = Member.builder().username("member1").age(27).team(team).build();

        List<Member> members = List.of(member0, member1, member2, member3);
        memberRepository.saveAll(members);

    }
    void addTestData2() {
        Team team = Team.builder().name("teamB").build();
        teamRepository.save(team);
        Member member0 = Member.builder().username("member0").age(10).team(team).build();
        Member member1 = Member.builder().username("member1").age(10).team(team).build();
        Member member2 = Member.builder().username("member2").age(10).team(team).build();
        Member member3 = Member.builder().username("member3").age(10).team(team).build();
        Member member4 = Member.builder().username("member4").age(9).team(team).build();

        List<Member> members = List.of(member0, member1, member2, member3, member4);
        memberRepository.saveAll(members);

    }
    @Test
    void testBaseEntity() throws InterruptedException {

        Team team = Team.builder().name("teamB").build();
        teamRepository.save(team);
        Member member0 = Member.builder().username("member0").age(10).team(team).build();
        Member member1 = Member.builder().username("member1").age(10).team(team).build();

        memberRepository.save(member0);
        memberRepository.save(member1);


        Thread.sleep(10000);

        member0.setUsername("Member00");
        memberRepository.save(member0);
        em.flush();  // 이 위치에 꼭 필요함
        em.clear();  // 이 위치에 꼭 필요함 없으면 아래 조회 내용 틀림

        Member member00 = memberRepository.findById(member0.getId()).get();
        Assertions.assertThat(member00.getCreatedDt()).isNotEqualTo(member00.getLastModifiedDt());
        Member member11 = memberRepository.findById(member1.getId()).get();
        Assertions.assertThat(member1.getCreatedDt()).isEqualTo(member1.getLastModifiedDt());
    }

}