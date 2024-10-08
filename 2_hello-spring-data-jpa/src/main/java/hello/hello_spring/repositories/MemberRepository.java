package hello.hello_spring.repositories;

import hello.hello_spring.entities.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Optional<Member> findMemberById(long id) ;
    Member save(Member member);
    Optional<Member> findByName(String name);
    List<Member> findAll();

}
