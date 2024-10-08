package hello.hello_spring.services;

import hello.hello_spring.controllers.RequestJoin;
import hello.hello_spring.entities.Member;
import hello.hello_spring.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository repository;


    public Long join(RequestJoin form) {
        Member member = Member.builder().name(form.getName()).build();

        duplicateCheck(form);

        Member savedMember = repository.save(member);
        return savedMember.getId();
    }

    private void duplicateCheck(RequestJoin form) {
        Optional<Member> byName = repository.findByName(form.getName());
        byName.ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다");
        });
    }

    public List<Member> findMembers() {
        return repository.findAll();
    }
    public Member findMemberById(Long id)  {
        try {
            Optional<Member> member = repository.findMemberById(id).stream().findAny();
            return member.orElse(null);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Member findMemberByName(String name)  {
        try {
            Optional<Member> member = repository.findByName(name).stream().findAny();
            return member.orElse(null);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
