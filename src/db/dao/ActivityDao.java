package db.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;
import com.fitcoach.client.model.member.Attendance;
import com.fitcoach.client.model.member.ExerciseRecord;
import com.fitcoach.client.model.point.Point;
import com.fitcoach.client.model.point.PointHistory;
import com.fitcoach.client.model.point.PointPolicy;
import com.fitcoach.client.model.product.Membership;
import db.BaseDao;
import db.DBA;

public class ActivityDao extends BaseDao {
  public ActivityDao(DBA dba) {
    super(dba);
  }

  // ===================== 출석 =====================

  public boolean hasCheckedInToday(String memberId) {
    String sql = "SELECT COUNT(*) FROM attendance "
        + "WHERE member_id = ? AND DATE(attendance_date_time) = CURDATE()";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, memberId);
      rs = pstmt.executeQuery();
      if (rs.next()) return rs.getInt(1) > 0;
      return false;
    } catch (SQLException e) {
      logError("ActivityDao.hasCheckedInToday", e);
      return false;
    } finally {
      close(rs, pstmt);
    }
  }

  public List<Attendance> findAttendancesByMemberId(String memberId) {
    String sql = "SELECT * FROM attendance WHERE member_id = ? ORDER BY attendance_date_time DESC";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<Attendance> list = new ArrayList<>();
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, memberId);
      rs = pstmt.executeQuery();
      while (rs.next()) list.add(mapAttendance(rs));
      return list;
    } catch (SQLException e) {
      logError("ActivityDao.findAttendancesByMemberId", e);
      return list;
    } finally {
      close(rs, pstmt);
    }
  }

  public boolean hasEarnedPointsToday(String memberId) {
    String sql = "SELECT COUNT(*) FROM point_history "
        + "WHERE member_id = ? AND date = CURDATE() AND `type` = '운동적립'";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, memberId);
      rs = pstmt.executeQuery();
      if (rs.next()) return rs.getInt(1) > 0;
      return false;
    } catch (SQLException e) {
      logError("ActivityDao.hasEarnedPointsToday", e);
      return false;
    } finally {
      close(rs, pstmt);
    }
  }

  public boolean saveAttendance(Attendance attendance) {
    String sql = "INSERT INTO attendance (attendance_id, member_id, attendance_date_time, auth_method) "
        + "VALUES (?, ?, ?, ?)";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, attendance.getAttendanceId());
      pstmt.setString(2, attendance.getMemberId());
      pstmt.setTimestamp(3, Timestamp.valueOf(attendance.getAttendanceDateTime()));
      pstmt.setString(4, attendance.getAuthMethod());
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      logError("ActivityDao.saveAttendance", e);
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  // ===================== 운동 기록 =====================

  public boolean saveExerciseRecord(ExerciseRecord record) {
    String sql = "INSERT INTO exercise_record (record_id, member_id, exercise_date, exercise_type, "
        + "exercise_time, sets, reps, memo, photo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, record.getRecordId());
      pstmt.setString(2, record.getMemberId());
      pstmt.setDate(3, Date.valueOf(record.getExerciseDate()));
      pstmt.setString(4, record.getExerciseType());
      pstmt.setInt(5, record.getExerciseTime());
      pstmt.setInt(6, record.getSets());
      pstmt.setInt(7, record.getReps());
      pstmt.setString(8, record.getMemo());
      pstmt.setString(9, record.getPhoto());
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      logError("ActivityDao.saveExerciseRecord", e);
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  // ===================== 포인트 =====================

  public Point findPointByMemberId(String memberId) {
    String sql = "SELECT * FROM point WHERE member_id = ?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, memberId);
      rs = pstmt.executeQuery();
      if (rs.next()) return mapPoint(rs);
      return null;
    } catch (SQLException e) {
      logError("ActivityDao.findPointByMemberId", e);
      return null;
    } finally {
      close(rs, pstmt);
    }
  }

  public boolean savePoint(Point point) {
    String sql = "INSERT INTO point (point_id, member_id, balance, expiry_date) VALUES (?, ?, ?, ?)";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, point.getPointId());
      pstmt.setString(2, point.getMemberId());
      pstmt.setInt(3, point.getBalance());
      pstmt.setDate(4, Date.valueOf(point.getExpiryDate()));
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      logError("ActivityDao.savePoint", e);
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  public boolean updatePoint(Point point) {
    String sql = "UPDATE point SET balance=?, expiry_date=? WHERE point_id=?";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setInt(1, point.getBalance());
      pstmt.setDate(2, Date.valueOf(point.getExpiryDate()));
      pstmt.setString(3, point.getPointId());
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      logError("ActivityDao.updatePoint", e);
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  public boolean savePointHistory(PointHistory history) {
    String sql = "INSERT INTO point_history (history_id, member_id, `type`, amount, reason, date, balance_after) "
        + "VALUES (?, ?, ?, ?, ?, ?, ?)";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, history.getHistoryId());
      pstmt.setString(2, history.getMemberId());
      pstmt.setString(3, history.getType());
      pstmt.setInt(4, history.getAmount());
      pstmt.setString(5, history.getReason());
      pstmt.setDate(6, Date.valueOf(history.getDate()));
      pstmt.setInt(7, history.getBalanceAfter());
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      logError("ActivityDao.savePointHistory", e);
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  // ===================== 포인트 정책 =====================

  public PointPolicy findCurrentPolicy() {
    String sql = "SELECT * FROM point_policy ORDER BY policy_id DESC LIMIT 1";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      rs = pstmt.executeQuery();
      if (rs.next()) return mapPointPolicy(rs);
      return null;
    } catch (SQLException e) {
      logError("ActivityDao.findCurrentPolicy", e);
      return null;
    } finally {
      close(rs, pstmt);
    }
  }

  // ===================== 회원권 =====================

  public List<Membership> findActiveMembershipsByMemberId(String memberId) {
    String sql = "SELECT * FROM membership WHERE member_id = ? AND status = 'ACTIVE'";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<Membership> list = new ArrayList<>();
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, memberId);
      rs = pstmt.executeQuery();
      while (rs.next()) list.add(mapMembership(rs));
      return list;
    } catch (SQLException e) {
      logError("ActivityDao.findActiveMembershipsByMemberId", e);
      return list;
    } finally {
      close(rs, pstmt);
    }
  }

  // ===================== mapRow 헬퍼 =====================

  private Attendance mapAttendance(ResultSet rs) throws SQLException {
    return new Attendance(
        rs.getString("attendance_id"),
        rs.getString("member_id"),
        rs.getTimestamp("attendance_date_time").toLocalDateTime(),
        rs.getString("auth_method")
    );
  }

  private Point mapPoint(ResultSet rs) throws SQLException {
    return new Point(
        rs.getString("point_id"),
        rs.getString("member_id"),
        rs.getInt("balance"),
        rs.getDate("expiry_date").toLocalDate()
    );
  }

  private PointPolicy mapPointPolicy(ResultSet rs) throws SQLException {
    return new PointPolicy(
        rs.getString("policy_id"),
        rs.getInt("base_points"),
        rs.getInt("time_bonus_points"),
        rs.getInt("exercise_time_standard"),
        rs.getInt("consecutive_attendance_days"),
        rs.getInt("consecutive_attendance_bonus")
    );
  }

  private Membership mapMembership(ResultSet rs) throws SQLException {
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
}
