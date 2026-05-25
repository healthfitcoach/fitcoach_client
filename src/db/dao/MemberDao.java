package db.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.fitcoach.client.model.member.Member;
import db.DBA;

public class MemberDao {
  private DBA dba;

  public MemberDao(DBA dba) {
    this.dba = dba;
  }

  public boolean init() {
    return dba.isConnected();
  }

  public Member findById(String memberId) {
    String sql = "SELECT * FROM member WHERE member_id = ?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      Connection conn = dba.getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, memberId);
      rs = pstmt.executeQuery();
      if (rs.next()) return mapRow(rs);
      return null;
    } catch (SQLException e) {
      System.out.println("DB 오류: " + e.getMessage());
      return null;
    } finally {
      close(rs, pstmt);
    }
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
      System.out.println("DB 오류: " + e.getMessage());
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
      System.out.println("DB 오류: " + e.getMessage());
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
      System.out.println("DB 오류: " + e.getMessage());
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  public boolean update(Member member) {
    String sql = "UPDATE member SET login_id=?, password=?, name=?, nickname=?, "
        + "phone_number=?, birth_date=?, physical_info=?, address=?, profile_picture=? "
        + "WHERE member_id=?";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, member.getLoginId());
      pstmt.setString(2, member.getPassword());
      pstmt.setString(3, member.getName());
      pstmt.setString(4, member.getNickname());
      pstmt.setString(5, member.getPhoneNumber());
      pstmt.setString(6, member.getBirthDate());
      pstmt.setString(7, member.getPhysicalInfo());
      pstmt.setString(8, member.getAddress());
      pstmt.setString(9, member.getProfilePicture());
      pstmt.setString(10, member.getMemberId());
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      System.out.println("DB 오류: " + e.getMessage());
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  public boolean delete(String memberId) {
    String sql = "DELETE FROM member WHERE member_id = ?";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, memberId);
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      System.out.println("DB 오류: " + e.getMessage());
      return false;
    } finally {
      close(null, pstmt);
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

  private void close(ResultSet rs, PreparedStatement pstmt) {
    if (rs != null) try { rs.close(); } catch (SQLException ignored) {}
    if (pstmt != null) try { pstmt.close(); } catch (SQLException ignored) {}
  }
}
