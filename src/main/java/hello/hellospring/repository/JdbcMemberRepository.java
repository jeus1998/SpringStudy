package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcMemberRepository implements MemberRepository {
    private final DataSource dataSource;
    public JdbcMemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Override
    public Member save(Member member) {
        String sql = "insert into member(name) values(?)";
        Connection conn = null; // 데이터베이스 연결 객체
        PreparedStatement pstmt = null; // JDBC에서 SQL문을 실행하기 위한 준비된 문( PreparedStatement)를 나타내는 변수
        ResultSet rs = null; // 결과를 받는 용도
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //  Statement.RETURN_GENERATED_KEYS
            //  일반적으로 INSERT 문을 실행하고, 데이터베이스가 자동으로 생성한 기본 키 값을 얻고 싶을 때 사용됩니다.

            pstmt.setString(1, member.getName());
            pstmt.executeUpdate(); // DB에 쿼리를 날린다.
            rs = pstmt.getGeneratedKeys(); // Statement.RETURN_GENERATED_KEYS(실행한 SQL 쿼리)에 의해 생성된 키를 반환하는 메서드

            if (rs.next()) { // Iterator.hasNext() 같은 메서드 rs에 값이 있으면
                member.setId(rs.getLong(1));
            }
            else {
                throw new SQLException("id 조회 실패");
            }

            return member;
        }
        catch (Exception e) {
            throw new IllegalStateException(e);
        }
        finally { // finally -> 예외가 발생하든 발생하지 않든 항상 실행하는 코드 블록을 정의 주로 리소스 해제, 정리 작업 코드 실행 보장에 사용
            close(conn, pstmt, rs); // 리소스 해제
        }
    }
    @Override
    public Optional<Member> findById(Long id) {
        String sql = "select * from member where id = ?";
        Connection conn = null; PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery(); // 조회는 executeQuery() // executeUpdate
            if(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            }
            else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
    @Override
    public List<Member> findAll() {
        String sql = "select * from member";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            List<Member> members = new ArrayList<>();
            while(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                members.add(member);
            }
            return members;
        }
        catch (Exception e) {
            throw new IllegalStateException(e);
        }
        finally {
            close(conn, pstmt, rs);
        }
    }
    @Override
    public Optional<Member> findByName(String name) {
        String sql = "select * from member where name = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    // 리소스 해제 메서드
    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}
