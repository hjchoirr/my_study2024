package study.data_jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.data_jpa.entitiies.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
