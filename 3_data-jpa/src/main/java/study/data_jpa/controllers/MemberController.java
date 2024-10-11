package study.data_jpa.controllers;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import study.data_jpa.dto.MemberDto;
import study.data_jpa.entitiies.Member;
import study.data_jpa.entitiies.Team;
import study.data_jpa.repository.MemberRepository;
import study.data_jpa.repository.TeamRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;

    @ResponseBody
    @GetMapping("/member/{id}")
    public Optional<Member> getMember(@PathVariable("id") long id) {
        return memberRepository.findById(id);
    }

    @ResponseBody
    @GetMapping("/members")
    public Page<MemberDto> list(@PageableDefault(size=5, sort={"id","username"}, direction=Sort.Direction.DESC) Pageable pageable) {

        //PageRequest request = PageRequest.of(1, 3, Sort.by(Sort.Direction.DESC, "id"));
    /*
        Page<MemberDto> members = memberRepository.findAll(pageable).map(m -> {
            return new MemberDto(m.getId(), m.getUsername(), m.getTeam().getName());
        });
     */
        return memberRepository.findAll(pageable).map(MemberDto::new);
    }


    //@PostConstruct
    public void init() {

        Team team = teamRepository.save(Team.builder().name("TeamA").build());

        for(int i = 0 ; i < 100; i++) {
            memberRepository.save(Member.builder().username("member" + i).age(i).team(team).build());

        }
    }
}
