package hello.hello_spring.repositories;

import hello.hello_spring.entities.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@Repository
@RequiredArgsConstructor
public class JdbcMemberRepository implements MemberRepository {
    private final DataSource dataSource;

    @Override
    public Optional<Member> findMemberById(long id)  {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String sql = "select * from member where id = ?";
        try {
            //conn = dataSource.getConnection();
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                Member member = Member.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .build();
                return Optional.ofNullable(member);
            }
            return Optional.empty();

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            close(rs, stmt, conn);
        }

        return Optional.empty();
    }

    private static void close(ResultSet rs, PreparedStatement stmt, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                conn.close();
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    @Override
    public Member save(Member member) {

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            conn = getConnection();
            String sql = "insert into member (name) values (?)";
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, member.getName());
            stmt.executeUpdate();
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                member.setId(rs.getLong("id"));
                return Member.builder().id(rs.getLong("id"))
                        .name(member.getName()).build();
            }
            return null;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs, stmt, conn);
        }
        return null;
    }

    @Override
    public Optional<Member> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public List<Member> findAll() {

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "select * from member";

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            List<Member> members = new ArrayList<>();
            while(rs.next()) {
                members.add(Member.builder().id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .build());
            }
            return members;
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            close(rs, stmt, conn);
        }
      return null;
    }
}
