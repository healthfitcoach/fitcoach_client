package db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.fitcoach.client.model.point.PointPolicy;
import db.DBA;

public class PointPolicyDao {
  private DBA dba;

  public PointPolicyDao(DBA dba) {
    this.dba = dba;
  }

  public boolean init() {
    return dba.isConnected();
  }

  public PointPolicy findById(String policyId) {
    String sql = "SELECT * FROM point_policy WHERE policy_id = ?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, policyId);
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

  public List<PointPolicy> findAll() {
    String sql = "SELECT * FROM point_policy";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<PointPolicy> list = new ArrayList<>();
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

  public PointPolicy findActive() {
    String sql = "SELECT * FROM point_policy LIMIT 1";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
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

  public boolean save(PointPolicy policy) {
    String sql = "INSERT INTO point_policy (policy_id, base_points, time_bonus_points, "
        + "exercise_time_standard, consecutive_attendance_days, consecutive_attendance_bonus) "
        + "VALUES (?, ?, ?, ?, ?, ?)";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, policy.getPolicyId());
      pstmt.setInt(2, policy.getBasePoints());
      pstmt.setInt(3, policy.getTimeBonusPoints());
      pstmt.setInt(4, policy.getExerciseTimeStandard());
      pstmt.setInt(5, policy.getConsecutiveAttendanceDays());
      pstmt.setInt(6, policy.getConsecutiveAttendanceBonus());
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      System.out.println("DB 오류: " + e.getMessage());
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  public boolean update(PointPolicy policy) {
    String sql = "UPDATE point_policy SET base_points=?, time_bonus_points=?, "
        + "exercise_time_standard=?, consecutive_attendance_days=?, consecutive_attendance_bonus=? "
        + "WHERE policy_id=?";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setInt(1, policy.getBasePoints());
      pstmt.setInt(2, policy.getTimeBonusPoints());
      pstmt.setInt(3, policy.getExerciseTimeStandard());
      pstmt.setInt(4, policy.getConsecutiveAttendanceDays());
      pstmt.setInt(5, policy.getConsecutiveAttendanceBonus());
      pstmt.setString(6, policy.getPolicyId());
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      System.out.println("DB 오류: " + e.getMessage());
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  public boolean delete(String policyId) {
    String sql = "DELETE FROM point_policy WHERE policy_id = ?";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, policyId);
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      System.out.println("DB 오류: " + e.getMessage());
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  private PointPolicy mapRow(ResultSet rs) throws SQLException {
    return new PointPolicy(
        rs.getString("policy_id"),
        rs.getInt("base_points"),
        rs.getInt("time_bonus_points"),
        rs.getInt("exercise_time_standard"),
        rs.getInt("consecutive_attendance_days"),
        rs.getInt("consecutive_attendance_bonus")
    );
  }

  private void close(ResultSet rs, PreparedStatement pstmt) {
    if (rs != null) try { rs.close(); } catch (SQLException ignored) {}
    if (pstmt != null) try { pstmt.close(); } catch (SQLException ignored) {}
  }
}
