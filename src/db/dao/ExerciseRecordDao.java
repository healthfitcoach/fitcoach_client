package db.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.fitcoach.client.model.member.ExerciseRecord;
import db.DBA;

public class ExerciseRecordDao {
  private DBA dba;

  public ExerciseRecordDao(DBA dba) {
    this.dba = dba;
  }

  public boolean init() {
    return dba.isConnected();
  }

  public ExerciseRecord findById(String recordId) {
    String sql = "SELECT * FROM exercise_record WHERE record_id = ?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, recordId);
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

  public List<ExerciseRecord> findAll() {
    String sql = "SELECT * FROM exercise_record";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<ExerciseRecord> list = new ArrayList<>();
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

  public List<ExerciseRecord> findByMemberId(String memberId) {
    String sql = "SELECT * FROM exercise_record WHERE member_id = ?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<ExerciseRecord> list = new ArrayList<>();
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

  public boolean save(ExerciseRecord record) {
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
      System.out.println("DB 오류: " + e.getMessage());
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  public boolean update(ExerciseRecord record) {
    String sql = "UPDATE exercise_record SET exercise_type=?, exercise_time=?, sets=?, reps=?, memo=?, photo=? "
        + "WHERE record_id=?";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, record.getExerciseType());
      pstmt.setInt(2, record.getExerciseTime());
      pstmt.setInt(3, record.getSets());
      pstmt.setInt(4, record.getReps());
      pstmt.setString(5, record.getMemo());
      pstmt.setString(6, record.getPhoto());
      pstmt.setString(7, record.getRecordId());
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      System.out.println("DB 오류: " + e.getMessage());
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  public boolean delete(String recordId) {
    String sql = "DELETE FROM exercise_record WHERE record_id = ?";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, recordId);
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      System.out.println("DB 오류: " + e.getMessage());
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  private ExerciseRecord mapRow(ResultSet rs) throws SQLException {
    return new ExerciseRecord(
        rs.getString("record_id"),
        rs.getString("member_id"),
        rs.getDate("exercise_date").toLocalDate(),
        rs.getString("exercise_type"),
        rs.getInt("exercise_time"),
        rs.getInt("sets"),
        rs.getInt("reps"),
        rs.getString("memo"),
        rs.getString("photo")
    );
  }

  private void close(ResultSet rs, PreparedStatement pstmt) {
    if (rs != null) try { rs.close(); } catch (SQLException ignored) {}
    if (pstmt != null) try { pstmt.close(); } catch (SQLException ignored) {}
  }
}
