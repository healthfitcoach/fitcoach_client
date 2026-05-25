package db.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import com.fitcoach.client.model.schedule.PTSchedule;
import db.DBA;

public class PTScheduleDao {
  private DBA dba;

  public PTScheduleDao(DBA dba) {
    this.dba = dba;
  }

  public boolean init() {
    return dba.isConnected();
  }

  public PTSchedule findById(String scheduleId) {
    String sql = "SELECT * FROM pt_schedule WHERE schedule_id = ?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, scheduleId);
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

  public List<PTSchedule> findAll() {
    String sql = "SELECT * FROM pt_schedule";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<PTSchedule> list = new ArrayList<>();
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

  public List<PTSchedule> findByMemberId(String memberId) {
    String sql = "SELECT * FROM pt_schedule WHERE member_id = ?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<PTSchedule> list = new ArrayList<>();
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

  public List<PTSchedule> findByTrainerId(String trainerId) {
    String sql = "SELECT * FROM pt_schedule WHERE trainer_id = ?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<PTSchedule> list = new ArrayList<>();
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, trainerId);
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

  public boolean save(PTSchedule schedule) {
    String sql = "INSERT INTO pt_schedule (schedule_id, pt_id, member_id, trainer_id, date, time, status) "
        + "VALUES (?, ?, ?, ?, ?, ?, ?)";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, schedule.getScheduleId());
      pstmt.setString(2, schedule.getPtId());
      pstmt.setString(3, schedule.getMemberId());
      pstmt.setString(4, schedule.getTrainerId());
      pstmt.setDate(5, Date.valueOf(schedule.getDate()));
      pstmt.setTime(6, Time.valueOf(schedule.getTime()));
      pstmt.setString(7, schedule.getStatus());
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      System.out.println("DB 오류: " + e.getMessage());
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  public boolean update(PTSchedule schedule) {
    String sql = "UPDATE pt_schedule SET status=? WHERE schedule_id=?";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, schedule.getStatus());
      pstmt.setString(2, schedule.getScheduleId());
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      System.out.println("DB 오류: " + e.getMessage());
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  public boolean delete(String scheduleId) {
    String sql = "DELETE FROM pt_schedule WHERE schedule_id = ?";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, scheduleId);
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      System.out.println("DB 오류: " + e.getMessage());
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  private PTSchedule mapRow(ResultSet rs) throws SQLException {
    return new PTSchedule(
        rs.getString("schedule_id"),
        rs.getString("pt_id"),
        rs.getString("member_id"),
        rs.getString("trainer_id"),
        rs.getDate("date").toLocalDate(),
        rs.getTime("time").toLocalTime(),
        rs.getString("status")
    );
  }

  private void close(ResultSet rs, PreparedStatement pstmt) {
    if (rs != null) try { rs.close(); } catch (SQLException ignored) {}
    if (pstmt != null) try { pstmt.close(); } catch (SQLException ignored) {}
  }
}
