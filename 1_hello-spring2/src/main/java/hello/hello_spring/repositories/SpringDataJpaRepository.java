package hello.hello_spring.repositories;

import hello.hello_spring.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaRepository extends JpaRepository<Member, Long>, MemberRepository {
}
