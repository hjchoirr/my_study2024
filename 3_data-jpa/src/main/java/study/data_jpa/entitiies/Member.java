package study.data_jpa.entitiies;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter @Setter
@ToString(of={"id", "username", "age", "team"})
@NamedQuery(name="Member.findByUsername", query = "select m from Member m where m.username = :name ")
//public class Member extends BaseJpaEntity {
public class Member extends BaseEntity {
    @Id @GeneratedValue
    @Column(name="member_id")
    private Long id;

    private String username;

    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="team_id")
    private Team team;
/*
    public void addTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }
 */
}
