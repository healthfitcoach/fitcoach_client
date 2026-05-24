package db.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.fitcoach.client.model.member.Member;
import db.BaseDao;
import db.DBA;

public class AuthDao extends BaseDao {
  public AuthDao(DBA dba) {
    super(dba);
  }

  public List<Member> findAll() {
    String sql = "SELECT * FROM member";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<Member> list = new ArrayList<>();
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next()) list.add(mapRow(rs));
      return list;
    } catch (SQLException e) {
      logError("AuthDao.findAll", e);
      return list;
    } finally {
      close(rs, pstmt);
    }
  }

  public Member findByLoginId(String loginId) {
    String sql = "SELECT * FROM member WHERE login_id = ?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, loginId);
      rs = pstmt.executeQuery();
      if (rs.next()) return mapRow(rs);
      return null;
    } catch (SQLException e) {
      logError("AuthDao.findByLoginId", e);
      return null;
    } finally {
      close(rs, pstmt);
    }
  }

  public boolean save(Member member) {
    String sql = "INSERT INTO member (member_id, login_id, password, name, nickname, "
        + "phone_number, birth_date, physical_info, address, profile_picture, join_date) "
        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, member.getMemberId());
      pstmt.setString(2, member.getLoginId());
      pstmt.setString(3, member.getPassword());
      pstmt.setString(4, member.getName());
      pstmt.setString(5, member.getNickname());
      pstmt.setString(6, member.getPhoneNumber());
      pstmt.setString(7, member.getBirthDate());
      pstmt.setString(8, member.getPhysicalInfo());
      pstmt.setString(9, member.getAddress());
      pstmt.setString(10, member.getProfilePicture());
      pstmt.setDate(11, Date.valueOf(member.getJoinDate()));
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      logError("AuthDao.save", e);
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  public boolean existsByLoginId(String loginId) {
    String sql = "SELECT COUNT(*) FROM member WHERE login_id = ?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, loginId);
      rs = pstmt.executeQuery();
      if (rs.next()) return rs.getInt(1) > 0;
      return false;
    } catch (SQLException e) {
      logError("AuthDao.existsByLoginId", e);
      return false;
    } finally {
      close(rs, pstmt);
    }
  }

  private Member mapRow(ResultSet rs) throws SQLException {
    return new Member(
        rs.getString("member_id"),
        rs.getString("login_id"),
        rs.getString("password"),
        rs.getString("name"),
        rs.getString("nickname"),
        rs.getString("phone_number"),
        rs.getString("birth_date"),
        rs.getString("physical_info"),
        rs.getString("address"),
        rs.getString("profile_picture"),
        rs.getDate("join_date").toLocalDate()
    );
  }
}
