package db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.fitcoach.client.model.product.ExerciseProgram;
import db.DBA;

public class ExerciseProgramDao {
  private DBA dba;

  public ExerciseProgramDao(DBA dba) {
    this.dba = dba;
  }

  public boolean init() {
    return dba.isConnected();
  }

  public ExerciseProgram findById(String programId) {
    String sql = "SELECT * FROM exercise_program WHERE program_id = ?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, programId);
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

  public List<ExerciseProgram> findAll() {
    String sql = "SELECT * FROM exercise_program";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<ExerciseProgram> list = new ArrayList<>();
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

  public List<ExerciseProgram> findByInstructorId(String instructorId) {
    String sql = "SELECT * FROM exercise_program WHERE instructor_id = ?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<ExerciseProgram> list = new ArrayList<>();
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, instructorId);
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

  public boolean save(ExerciseProgram program) {
    String sql = "INSERT INTO exercise_program (program_id, product_id, product_name, price, description, "
        + "instructor_id, status, capacity, remaining_capacity) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, program.getProgramId());
      pstmt.setString(2, program.getProductId());
      pstmt.setString(3, program.getProductName());
      pstmt.setInt(4, program.getPrice());
      pstmt.setString(5, program.getDescription());
      pstmt.setString(6, program.getInstructorId());
      pstmt.setString(7, program.getStatus());
      pstmt.setInt(8, program.getCapacity());
      pstmt.setInt(9, program.getRemainingCapacity());
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      System.out.println("DB 오류: " + e.getMessage());
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  public boolean update(ExerciseProgram program) {
    String sql = "UPDATE exercise_program SET status=?, remaining_capacity=? WHERE program_id=?";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, program.getStatus());
      pstmt.setInt(2, program.getRemainingCapacity());
      pstmt.setString(3, program.getProgramId());
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      System.out.println("DB 오류: " + e.getMessage());
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  public boolean delete(String programId) {
    String sql = "DELETE FROM exercise_program WHERE program_id = ?";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, programId);
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      System.out.println("DB 오류: " + e.getMessage());
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  private ExerciseProgram mapRow(ResultSet rs) throws SQLException {
    return new ExerciseProgram(
        rs.getString("product_id"),
        rs.getString("product_name"),
        rs.getInt("price"),
        rs.getString("description"),
        rs.getString("program_id"),
        rs.getString("instructor_id"),
        rs.getString("status"),
        rs.getInt("capacity"),
        rs.getInt("remaining_capacity")
    );
  }

  private void close(ResultSet rs, PreparedStatement pstmt) {
    if (rs != null) try { rs.close(); } catch (SQLException ignored) {}
    if (pstmt != null) try { pstmt.close(); } catch (SQLException ignored) {}
  }
}
