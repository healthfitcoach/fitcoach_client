package db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.fitcoach.client.model.product.SportEquipment;
import db.DBA;

public class SportEquipmentDao {
  private DBA dba;

  public SportEquipmentDao(DBA dba) {
    this.dba = dba;
  }

  public boolean init() {
    return dba.isConnected();
  }

  public SportEquipment findById(String productId) {
    String sql = "SELECT * FROM sport_equipment WHERE product_id = ?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, productId);
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

  public List<SportEquipment> findAll() {
    String sql = "SELECT * FROM sport_equipment";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<SportEquipment> list = new ArrayList<>();
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

  public List<SportEquipment> findByCategory(String category) {
    String sql = "SELECT * FROM sport_equipment WHERE category = ?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<SportEquipment> list = new ArrayList<>();
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, category);
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

  public boolean save(SportEquipment product) {
    String sql = "INSERT INTO sport_equipment (product_id, product_name, price, description, stock, category) "
        + "VALUES (?, ?, ?, ?, ?, ?)";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, product.getProductId());
      pstmt.setString(2, product.getProductName());
      pstmt.setInt(3, product.getPrice());
      pstmt.setString(4, product.getDescription());
      pstmt.setInt(5, product.getStock());
      pstmt.setString(6, product.getCategory());
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      System.out.println("DB 오류: " + e.getMessage());
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  public boolean update(SportEquipment product) {
    String sql = "UPDATE sport_equipment SET product_name=?, price=?, description=?, stock=?, category=? "
        + "WHERE product_id=?";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, product.getProductName());
      pstmt.setInt(2, product.getPrice());
      pstmt.setString(3, product.getDescription());
      pstmt.setInt(4, product.getStock());
      pstmt.setString(5, product.getCategory());
      pstmt.setString(6, product.getProductId());
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      System.out.println("DB 오류: " + e.getMessage());
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  public boolean delete(String productId) {
    String sql = "DELETE FROM sport_equipment WHERE product_id = ?";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, productId);
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      System.out.println("DB 오류: " + e.getMessage());
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  private SportEquipment mapRow(ResultSet rs) throws SQLException {
    return new SportEquipment(
        rs.getString("product_id"),
        rs.getString("product_name"),
        rs.getInt("price"),
        rs.getString("description"),
        rs.getInt("stock"),
        rs.getString("category")
    );
  }

  private void close(ResultSet rs, PreparedStatement pstmt) {
    if (rs != null) try { rs.close(); } catch (SQLException ignored) {}
    if (pstmt != null) try { pstmt.close(); } catch (SQLException ignored) {}
  }
}
