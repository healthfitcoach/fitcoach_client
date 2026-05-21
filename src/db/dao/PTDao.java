package db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.fitcoach.client.model.product.PT;
import db.DBA;

public class PTDao {
  private DBA dba;

  public PTDao(DBA dba) {
    this.dba = dba;
  }

  public boolean init() {
    return dba.isConnected();
  }

  public PT findById(String ptId) {
    String sql = "SELECT * FROM pt WHERE pt_id = ?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, ptId);
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

  public List<PT> findAll() {
    String sql = "SELECT * FROM pt";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<PT> list = new ArrayList<>();
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

  public List<PT> findByMemberId(String memberId) {
    String sql = "SELECT * FROM pt WHERE member_id = ?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<PT> list = new ArrayList<>();
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

  public boolean save(PT pt) {
    String sql = "INSERT INTO pt (pt_id, product_id, product_name, price, description, "
        + "member_id, trainer_id, total_count, remaining_count, status) "
        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, pt.getPtId());
      pstmt.setString(2, pt.getProductId());
      pstmt.setString(3, pt.getProductName());
      pstmt.setInt(4, pt.getPrice());
      pstmt.setString(5, pt.getDescription());
      pstmt.setString(6, pt.getMemberId());
      pstmt.setString(7, pt.getTrainerId());
      pstmt.setInt(8, pt.getTotalCount());
      pstmt.setInt(9, pt.getRemainingCount());
      pstmt.setString(10, pt.getStatus());
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      System.out.println("DB 오류: " + e.getMessage());
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  public boolean update(PT pt) {
    String sql = "UPDATE pt SET remaining_count=?, status=? WHERE pt_id=?";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setInt(1, pt.getRemainingCount());
      pstmt.setString(2, pt.getStatus());
      pstmt.setString(3, pt.getPtId());
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      System.out.println("DB 오류: " + e.getMessage());
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  public boolean delete(String ptId) {
    String sql = "DELETE FROM pt WHERE pt_id = ?";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, ptId);
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      System.out.println("DB 오류: " + e.getMessage());
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  private PT mapRow(ResultSet rs) throws SQLException {
    return new PT(
        rs.getString("product_id"),
        rs.getString("product_name"),
        rs.getInt("price"),
        rs.getString("description"),
        rs.getString("pt_id"),
        rs.getString("member_id"),
        rs.getString("trainer_id"),
        rs.getInt("total_count"),
        rs.getInt("remaining_count"),
        rs.getString("status")
    );
  }

  private void close(ResultSet rs, PreparedStatement pstmt) {
    if (rs != null) try { rs.close(); } catch (SQLException ignored) {}
    if (pstmt != null) try { pstmt.close(); } catch (SQLException ignored) {}
  }
}
