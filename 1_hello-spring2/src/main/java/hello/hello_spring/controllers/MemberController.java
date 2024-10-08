package hello.hello_spring.controllers;

import hello.hello_spring.entities.Member;
import hello.hello_spring.services.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService service;

    @GetMapping("/new")
    public String newMember() {
        return "member/new.html";
    }
    @PostMapping("/new")
    public String newMember(RequestJoin form) {
        System.out.println("form" + form);
        service.join(form);
        return "redirect:/";
    }

    @GetMapping("/list")
    public String listMember(Model model) {
        List<Member> members = service.findMembers();
        model.addAttribute("members", members);
        return "/member/list";
    }
}
