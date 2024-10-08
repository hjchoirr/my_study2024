package hello.hello_spring.services;

import hello.hello_spring.controllers.RequestJoin;
import hello.hello_spring.entities.Member;
import hello.hello_spring.repositories.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    private MemberRepository memberRepository;

    @Test
    @Commit
    public void 회원가입() throws Exception {
        //Given
        RequestJoin form = RequestJoin.builder().name("나나나").build();
        //When
        Long saveId = memberService.join(form);
        System.out.println("saveId: " + saveId);
        //Then
        Member _member = memberService.findMemberById(saveId);
        assertEquals(form.getName(), _member.getName());
    }
    @Test
    public void 중복_회원_예외() throws Exception {
        //Given
        RequestJoin form1 = RequestJoin.builder().name("spring3").build();
        RequestJoin form2 = RequestJoin.builder().name("spring3").build();
        //When
        memberService.join(form1);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(form2));//예외가 발생해야 한다.
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");
    }

    @Test
    public void 이름으로_찾기() {
        Member member = memberService.findMemberByName("나나나");
        System.out.println(member);
    }

    /*
    @Test
    public void 이름과_아이디로_찾기() {
        Optional<Member> member = memberRepository.findByNameAndId("나나나", 54L);
        Member _member = member.get();
        System.out.println("member: " + member);
        System.out.println("_member: " +_member);
    }
    */
}