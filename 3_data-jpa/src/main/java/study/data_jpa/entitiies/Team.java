package study.data_jpa.entitiies;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;

@Entity
public class Team {
    @Id
    @GeneratedValue
    @Column(name="team_id")
    private Long id;
    private String name;
}
