package hello.hello_spring.repositories;

import hello.hello_spring.entities.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

//@Repository
@RequiredArgsConstructor
public class JpaMemberRepository implements MemberRepository{
    private final EntityManager em;

    @Override
    public Optional<Member> findMemberById(long id) {
        return Optional.ofNullable(em.find(Member.class, id));
    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> members = em.createQuery("select m from Member m where m.name = :name", Member.class).setParameter("name", name).getResultList();
        return members.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }
}
