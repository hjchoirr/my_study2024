package study.data_jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.data_jpa.dto.MemberDto;
import study.data_jpa.entitiies.Member;

import java.util.Collection;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // spring data jpa - query method
    List<Member> findByUsernameAndAgeGreaterThanEqualOrderByUsername(String username, int age);

    // spring data jpa - named query
    @Query(name = "Member.findByUsername")
    List<Member> findByUsername(@Param("name") String username);

    @Query("select m from Member m where m.username like :name and m.age = :age")
    List<Member> findUser(@Param("name") String name, @Param("age") int age);

    @Query("select new study.data_jpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t where m.username = :username")
    List<MemberDto> findMemberDto(@Param("username") String username);

    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);

    Page<Member> findByAgeGreaterThan(int age, Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.age = m.age + :plus where m.age = :age")
    int bulkUpdate(@Param("age") int age, @Param("plus") int plus);

    @Query("select m from Member m left join fetch m.team")
    List<Member> findJoinFetch();

    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m")
    List<Member> findEntityGraph();


}
