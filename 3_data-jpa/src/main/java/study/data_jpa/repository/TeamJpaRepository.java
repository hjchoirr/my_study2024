package study.data_jpa.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import study.data_jpa.entitiies.Team;

import java.util.List;
import java.util.Optional;

@Repository
public class TeamJpaRepository {
    @PersistenceContext
    private EntityManager em;

    public Team save(Team team) {
        em.persist(team);
        return team;
    }
    public void delete(Team team) {
        em.remove(team);
    }

    public Optional<Team> findById(int id) {
        return Optional.ofNullable(em.find(Team.class, id));
    }
    public List<Team> findAll() {
        return em.createQuery("select t from Team t", Team.class).getResultList();
    }
    public long count() {
        return em.createQuery("select count(t) from Team m", Long.class).getSingleResult();
    }
}
