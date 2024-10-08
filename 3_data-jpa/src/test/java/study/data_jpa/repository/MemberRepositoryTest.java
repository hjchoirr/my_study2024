package study.data_jpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.data_jpa.entitiies.Member;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
@Rollback(false)
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void testMemberRepository() {
        Member member = Member.builder().username("member3").build();
        Member saved = memberRepository.save(member);
        Optional<Member> found = memberRepository.findById(member.getId());
        Assertions.assertThat(member.getId()).isEqualTo(found.get().getId());
        Assertions.assertThat(member.getUsername()).isEqualTo(found.get().getUsername());
        Assertions.assertThat(member).isEqualTo(saved);

    }

}