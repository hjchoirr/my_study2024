package hello.hello_spring.repositories;

import hello.hello_spring.entities.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

//@Repository
public class JdbcTemplateMemberRepository implements MemberRepository{
    private final JdbcTemplate jdbcTemplate;

    //@Autowired  //@Repository 막은거 때문에 막음
    public JdbcTemplateMemberRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<Member> findMemberById(long id) {
        List<Member> result = jdbcTemplate.query("select * from member where id = ?", memberRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public Member save(Member member) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");

        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName());
        Number key = jdbcInsert.executeAndReturnKey(parameters);
        member.setId(key.longValue());
        return member;
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> members = jdbcTemplate.query("select * from member where name = ?", memberRowMapper(), name);
        return members.stream().findAny();
    }

    @Override
    public List<Member> findAll() {

        return jdbcTemplate.query("select * from member", memberRowMapper());
    }

    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> Member.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .build();
    }
}


