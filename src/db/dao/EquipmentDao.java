package db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.fitcoach.client.model.equipment.Equipment;
import db.DBA;

public class EquipmentDao {
  private DBA dba;

  public EquipmentDao(DBA dba) {
    this.dba = dba;
  }

  public boolean init() {
    return dba.isConnected();
  }

  public Equipment findById(String equipmentId) {
    String sql = "SELECT * FROM equipment WHERE equipment_id = ?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, equipmentId);
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

  public List<Equipment> findAll() {
    String sql = "SELECT * FROM equipment";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<Equipment> list = new ArrayList<>();
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

  public List<Equipment> findByCategory(String category) {
    String sql = "SELECT * FROM equipment WHERE category = ?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<Equipment> list = new ArrayList<>();
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

  public boolean save(Equipment equipment) {
    String sql = "INSERT INTO equipment (equipment_id, name, description, category, status) "
        + "VALUES (?, ?, ?, ?, ?)";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, equipment.getEquipmentId());
      pstmt.setString(2, equipment.getName());
      pstmt.setString(3, equipment.getDescription());
      pstmt.setString(4, equipment.getCategory());
      pstmt.setString(5, equipment.getStatus());
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      System.out.println("DB 오류: " + e.getMessage());
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  public boolean update(Equipment equipment) {
    String sql = "UPDATE equipment SET name=?, description=?, category=?, status=? "
        + "WHERE equipment_id=?";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, equipment.getName());
      pstmt.setString(2, equipment.getDescription());
      pstmt.setString(3, equipment.getCategory());
      pstmt.setString(4, equipment.getStatus());
      pstmt.setString(5, equipment.getEquipmentId());
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      System.out.println("DB 오류: " + e.getMessage());
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  public boolean delete(String equipmentId) {
    String sql = "DELETE FROM equipment WHERE equipment_id = ?";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, equipmentId);
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      System.out.println("DB 오류: " + e.getMessage());
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  private Equipment mapRow(ResultSet rs) throws SQLException {
    return new Equipment(
        rs.getString("equipment_id"),
        rs.getString("name"),
        rs.getString("description"),
        rs.getString("category"),
        rs.getString("status")
    );
  }

  private void close(ResultSet rs, PreparedStatement pstmt) {
    if (rs != null) try { rs.close(); } catch (SQLException ignored) {}
    if (pstmt != null) try { pstmt.close(); } catch (SQLException ignored) {}
  }
}
