package db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.fitcoach.client.model.equipment.ExerciseMethod;
import db.DBA;

public class ExerciseMethodDao {
  private DBA dba;

  public ExerciseMethodDao(DBA dba) {
    this.dba = dba;
  }

  public boolean init() {
    return dba.isConnected();
  }

  public ExerciseMethod findById(String methodId) {
    String sql = "SELECT * FROM exercise_method WHERE method_id = ?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, methodId);
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

  public List<ExerciseMethod> findAll() {
    String sql = "SELECT * FROM exercise_method";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<ExerciseMethod> list = new ArrayList<>();
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

  public List<ExerciseMethod> findByEquipmentId(String equipmentId) {
    String sql = "SELECT * FROM exercise_method WHERE equipment_id = ?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<ExerciseMethod> list = new ArrayList<>();
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, equipmentId);
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

  public boolean save(ExerciseMethod method) {
    String sql = "INSERT INTO exercise_method (method_id, equipment_id, exercise_name, target_body_part, "
        + "difficulty, preparation_pose, step_by_step_method, image, video_url) "
        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, method.getMethodId());
      pstmt.setString(2, method.getEquipmentId());
      pstmt.setString(3, method.getExerciseName());
      pstmt.setString(4, method.getTargetBodyPart());
      pstmt.setString(5, method.getDifficulty());
      pstmt.setString(6, method.getPreparationPose());
      pstmt.setString(7, method.getStepByStepMethod());
      pstmt.setString(8, method.getImage());
      pstmt.setString(9, method.getVideoUrl());
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      System.out.println("DB 오류: " + e.getMessage());
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  public boolean update(ExerciseMethod method) {
    String sql = "UPDATE exercise_method SET exercise_name=?, target_body_part=?, difficulty=?, "
        + "preparation_pose=?, step_by_step_method=?, image=?, video_url=? "
        + "WHERE method_id=?";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, method.getExerciseName());
      pstmt.setString(2, method.getTargetBodyPart());
      pstmt.setString(3, method.getDifficulty());
      pstmt.setString(4, method.getPreparationPose());
      pstmt.setString(5, method.getStepByStepMethod());
      pstmt.setString(6, method.getImage());
      pstmt.setString(7, method.getVideoUrl());
      pstmt.setString(8, method.getMethodId());
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      System.out.println("DB 오류: " + e.getMessage());
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  public boolean delete(String methodId) {
    String sql = "DELETE FROM exercise_method WHERE method_id = ?";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, methodId);
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      System.out.println("DB 오류: " + e.getMessage());
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  private ExerciseMethod mapRow(ResultSet rs) throws SQLException {
    return new ExerciseMethod(
        rs.getString("method_id"),
        rs.getString("equipment_id"),
        rs.getString("exercise_name"),
        rs.getString("target_body_part"),
        rs.getString("difficulty"),
        rs.getString("preparation_pose"),
        rs.getString("step_by_step_method"),
        rs.getString("image"),
        rs.getString("video_url")
    );
  }

  private void close(ResultSet rs, PreparedStatement pstmt) {
    if (rs != null) try { rs.close(); } catch (SQLException ignored) {}
    if (pstmt != null) try { pstmt.close(); } catch (SQLException ignored) {}
  }
}
