package study.data_jpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.data_jpa.entitiies.Member;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberJpaRepositoryTest {

    @Autowired
    MemberJpaRepository repository;

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
    void find() {
    }
}