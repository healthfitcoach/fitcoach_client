package db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.fitcoach.client.model.product.AdditionalProduct;
import db.DBA;

public class AdditionalProductDao {
  private DBA dba;

  public AdditionalProductDao(DBA dba) {
    this.dba = dba;
  }

  public boolean init() {
    return dba.isConnected();
  }

  public AdditionalProduct findById(String additionalProductId) {
    String sql = "SELECT * FROM additional_product WHERE additional_product_id = ?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, additionalProductId);
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

  public List<AdditionalProduct> findAll() {
    String sql = "SELECT * FROM additional_product";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<AdditionalProduct> list = new ArrayList<>();
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

  public List<AdditionalProduct> findByMemberId(String memberId) {
    String sql = "SELECT * FROM additional_product WHERE member_id = ?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<AdditionalProduct> list = new ArrayList<>();
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

  public boolean save(AdditionalProduct product) {
    String sql = "INSERT INTO additional_product (additional_product_id, product_id, product_name, price, "
        + "description, member_id, status, usage_period) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, product.getAdditionalProductId());
      pstmt.setString(2, product.getProductId());
      pstmt.setString(3, product.getProductName());
      pstmt.setInt(4, product.getPrice());
      pstmt.setString(5, product.getDescription());
      pstmt.setString(6, product.getMemberId());
      pstmt.setString(7, product.getStatus());
      pstmt.setInt(8, product.getUsagePeriod());
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      System.out.println("DB 오류: " + e.getMessage());
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  public boolean update(AdditionalProduct product) {
    String sql = "UPDATE additional_product SET status=?, usage_period=? WHERE additional_product_id=?";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, product.getStatus());
      pstmt.setInt(2, product.getUsagePeriod());
      pstmt.setString(3, product.getAdditionalProductId());
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      System.out.println("DB 오류: " + e.getMessage());
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  public boolean delete(String additionalProductId) {
    String sql = "DELETE FROM additional_product WHERE additional_product_id = ?";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, additionalProductId);
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      System.out.println("DB 오류: " + e.getMessage());
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  private AdditionalProduct mapRow(ResultSet rs) throws SQLException {
    return new AdditionalProduct(
        rs.getString("product_id"),
        rs.getString("product_name"),
        rs.getInt("price"),
        rs.getString("description"),
        rs.getString("additional_product_id"),
        rs.getString("member_id"),
        rs.getString("status"),
        rs.getInt("usage_period")
    );
  }

  private void close(ResultSet rs, PreparedStatement pstmt) {
    if (rs != null) try { rs.close(); } catch (SQLException ignored) {}
    if (pstmt != null) try { pstmt.close(); } catch (SQLException ignored) {}
  }
}
