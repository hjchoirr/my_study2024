package study.data_jpa.entitiies;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter @Setter
@ToString(of={"id", "name"})
public class Team extends BaseJpaEntity {

    @Id @GeneratedValue
    @Column(name="team_id")
    private Long id;
    private String name;

    @OneToMany(mappedBy = "team")
    private List<Member> members ;
    //private List<Member> members = new ArrayList<>();
}
