package db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import com.fitcoach.client.model.member.Attendance;
import db.DBA;

public class AttendanceDao {
  private DBA dba;

  public AttendanceDao(DBA dba) {
    this.dba = dba;
  }

  public boolean init() {
    return dba.isConnected();
  }

  public Attendance findById(String attendanceId) {
    String sql = "SELECT * FROM attendance WHERE attendance_id = ?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, attendanceId);
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

  public List<Attendance> findAll() {
    String sql = "SELECT * FROM attendance";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<Attendance> list = new ArrayList<>();
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

  public List<Attendance> findByMemberId(String memberId) {
    String sql = "SELECT * FROM attendance WHERE member_id = ?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<Attendance> list = new ArrayList<>();
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

  public boolean save(Attendance attendance) {
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
      System.out.println("DB 오류: " + e.getMessage());
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  public boolean update(Attendance attendance) {
    String sql = "UPDATE attendance SET auth_method=? WHERE attendance_id=?";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, attendance.getAuthMethod());
      pstmt.setString(2, attendance.getAttendanceId());
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      System.out.println("DB 오류: " + e.getMessage());
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  public boolean delete(String attendanceId) {
    String sql = "DELETE FROM attendance WHERE attendance_id = ?";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, attendanceId);
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      System.out.println("DB 오류: " + e.getMessage());
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  private Attendance mapRow(ResultSet rs) throws SQLException {
    return new Attendance(
        rs.getString("attendance_id"),
        rs.getString("member_id"),
        rs.getTimestamp("attendance_date_time").toLocalDateTime(),
        rs.getString("auth_method")
    );
  }

  private void close(ResultSet rs, PreparedStatement pstmt) {
    if (rs != null) try { rs.close(); } catch (SQLException ignored) {}
    if (pstmt != null) try { pstmt.close(); } catch (SQLException ignored) {}
  }
}
