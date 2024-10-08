package hello.hello_spring.repositories;

import hello.hello_spring.entities.Member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

//@Repository
public class MemoryMemberRepository implements MemberRepository{

    private static HashMap<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {

        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;

    }

    @Override
    public Optional<Member> findMemberById(long id) {

        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {

        System.out.println("store.values()" + store.values());
        Optional<Member> anyMember = store.values()
                .stream()
                .filter(m -> m.getName().equals(name))
                .findAny();
        return anyMember;
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }
}

