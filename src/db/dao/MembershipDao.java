package db.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.fitcoach.client.model.product.Membership;
import db.DBA;

public class MembershipDao {
  private DBA dba;

  public MembershipDao(DBA dba) {
    this.dba = dba;
  }

  public boolean init() {
    return dba.isConnected();
  }

  public Membership findById(String membershipId) {
    String sql = "SELECT * FROM membership WHERE membership_id = ?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, membershipId);
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

  public List<Membership> findAll() {
    String sql = "SELECT * FROM membership";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<Membership> list = new ArrayList<>();
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

  public List<Membership> findByMemberId(String memberId) {
    String sql = "SELECT * FROM membership WHERE member_id = ?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<Membership> list = new ArrayList<>();
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, memberId);
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

  public boolean save(Membership membership) {
    String sql = "INSERT INTO membership (membership_id, product_id, product_name, price, description, "
        + "member_id, status, start_date, end_date, pause_date, resume_date) "
        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, membership.getMembershipId());
      pstmt.setString(2, membership.getProductId());
      pstmt.setString(3, membership.getProductName());
      pstmt.setInt(4, membership.getPrice());
      pstmt.setString(5, membership.getDescription());
      pstmt.setString(6, membership.getMemberId());
      pstmt.setString(7, membership.getStatus());
      pstmt.setDate(8, membership.getStartDate() != null ? Date.valueOf(membership.getStartDate()) : null);
      pstmt.setDate(9, membership.getEndDate() != null ? Date.valueOf(membership.getEndDate()) : null);
      pstmt.setDate(10, membership.getPauseDate() != null ? Date.valueOf(membership.getPauseDate()) : null);
      pstmt.setDate(11, membership.getResumeDate() != null ? Date.valueOf(membership.getResumeDate()) : null);
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      System.out.println("DB 오류: " + e.getMessage());
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  public boolean update(Membership membership) {
    String sql = "UPDATE membership SET status=?, start_date=?, end_date=?, pause_date=?, resume_date=? "
        + "WHERE membership_id=?";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, membership.getStatus());
      pstmt.setDate(2, membership.getStartDate() != null ? Date.valueOf(membership.getStartDate()) : null);
      pstmt.setDate(3, membership.getEndDate() != null ? Date.valueOf(membership.getEndDate()) : null);
      pstmt.setDate(4, membership.getPauseDate() != null ? Date.valueOf(membership.getPauseDate()) : null);
      pstmt.setDate(5, membership.getResumeDate() != null ? Date.valueOf(membership.getResumeDate()) : null);
      pstmt.setString(6, membership.getMembershipId());
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      System.out.println("DB 오류: " + e.getMessage());
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  public boolean delete(String membershipId) {
    String sql = "DELETE FROM membership WHERE membership_id = ?";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, membershipId);
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      System.out.println("DB 오류: " + e.getMessage());
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  private Membership mapRow(ResultSet rs) throws SQLException {
    Date startDate = rs.getDate("start_date");
    Date endDate = rs.getDate("end_date");
    return new Membership(
        rs.getString("product_id"),
        rs.getString("product_name"),
        rs.getInt("price"),
        rs.getString("description"),
        rs.getString("membership_id"),
        rs.getString("member_id"),
        rs.getString("status"),
        startDate != null ? startDate.toLocalDate() : null,
        endDate != null ? endDate.toLocalDate() : null
    );
  }

  private void close(ResultSet rs, PreparedStatement pstmt) {
    if (rs != null) try { rs.close(); } catch (SQLException ignored) {}
    if (pstmt != null) try { pstmt.close(); } catch (SQLException ignored) {}
  }
}
