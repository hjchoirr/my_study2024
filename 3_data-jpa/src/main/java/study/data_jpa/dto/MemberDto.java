package study.data_jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import study.data_jpa.entitiies.Member;

@Data
@AllArgsConstructor
public class MemberDto {
    private Long id;
    private String username;
    private String team;

    public MemberDto(Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
        this.team = member.getTeam().getName();

    }
}
