package db.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.fitcoach.client.model.product.PT;
import com.fitcoach.client.model.schedule.PTSchedule;
import com.fitcoach.client.model.schedule.Trainer;
import db.BaseDao;
import db.DBA;

public class PTDao extends BaseDao {
  public PTDao(DBA dba) {
    super(dba);
  }

  // ===================== 트레이너 =====================

  public List<Trainer> findAllTrainers() {
    String sql = "SELECT * FROM trainer";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<Trainer> list = new ArrayList<>();
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next()) list.add(mapTrainer(rs));
      return list;
    } catch (SQLException e) {
      logError("PTDao.findAllTrainers", e);
      return list;
    } finally {
      close(rs, pstmt);
    }
  }

  public List<Trainer> searchTrainersBySpecialty(String keyword) {
    String sql = "SELECT * FROM trainer WHERE specialty LIKE ?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<Trainer> list = new ArrayList<>();
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, "%" + keyword + "%");
      rs = pstmt.executeQuery();
      while (rs.next()) list.add(mapTrainer(rs));
      return list;
    } catch (SQLException e) {
      logError("PTDao.searchTrainersBySpecialty", e);
      return list;
    } finally {
      close(rs, pstmt);
    }
  }

  public Trainer findTrainerById(String trainerId) {
    String sql = "SELECT * FROM trainer WHERE trainer_id = ?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, trainerId);
      rs = pstmt.executeQuery();
      if (rs.next()) return mapTrainer(rs);
      return null;
    } catch (SQLException e) {
      logError("PTDao.findTrainerById", e);
      return null;
    } finally {
      close(rs, pstmt);
    }
  }

  // ===================== PT =====================

  public List<PT> findActivePTsByMemberId(String memberId) {
    String sql = "SELECT * FROM pt WHERE member_id = ? AND status = 'ACTIVE'";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<PT> list = new ArrayList<>();
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, memberId);
      rs = pstmt.executeQuery();
      while (rs.next()) list.add(mapPT(rs));
      return list;
    } catch (SQLException e) {
      logError("PTDao.findActivePTsByMemberId", e);
      return list;
    } finally {
      close(rs, pstmt);
    }
  }

  public boolean updatePT(PT pt) {
    String sql = "UPDATE pt SET remaining_count=?, status=? WHERE pt_id=?";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setInt(1, pt.getRemainingCount());
      pstmt.setString(2, pt.getStatus());
      pstmt.setString(3, pt.getPtId());
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      logError("PTDao.updatePT", e);
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  // ===================== PT 일정 =====================

  public boolean savePTSchedule(PTSchedule schedule) {
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
      logError("PTDao.savePTSchedule", e);
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  public List<PTSchedule> findSchedulesByTrainerAndDate(String trainerId, LocalDate date) {
    String sql = "SELECT * FROM pt_schedule WHERE trainer_id = ? AND date = ? ORDER BY time ASC";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<PTSchedule> list = new ArrayList<>();
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, trainerId);
      pstmt.setDate(2, Date.valueOf(date));
      rs = pstmt.executeQuery();
      while (rs.next()) list.add(mapPTSchedule(rs));
      return list;
    } catch (SQLException e) {
      logError("PTDao.findSchedulesByTrainerAndDate", e);
      return list;
    } finally {
      close(rs, pstmt);
    }
  }

  public boolean isSlotBooked(String trainerId, LocalDateTime dateTime) {
    String sql = "SELECT COUNT(*) FROM pt_schedule "
        + "WHERE trainer_id = ? AND date = ? AND time = ? AND status != 'CANCELLED'";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, trainerId);
      pstmt.setDate(2, Date.valueOf(dateTime.toLocalDate()));
      pstmt.setTime(3, Time.valueOf(dateTime.toLocalTime()));
      rs = pstmt.executeQuery();
      if (rs.next()) return rs.getInt(1) > 0;
      return false;
    } catch (SQLException e) {
      logError("PTDao.isSlotBooked", e);
      return false;
    } finally {
      close(rs, pstmt);
    }
  }

  // ===================== mapRow 헬퍼 =====================

  private Trainer mapTrainer(ResultSet rs) throws SQLException {
    return new Trainer(
        rs.getString("trainer_id"),
        rs.getString("name"),
        rs.getString("career"),
        rs.getString("certification"),
        rs.getString("specialty"),
        rs.getDouble("rating"),
        rs.getString("profile_picture")
    );
  }

  private PT mapPT(ResultSet rs) throws SQLException {
    return new PT(
        rs.getString("product_id"),
        rs.getString("product_name"),
        rs.getInt("price"),
        rs.getString("description"),
        rs.getString("pt_id"),
        rs.getString("member_id"),
        rs.getString("trainer_id"),
        rs.getInt("total_count"),
        rs.getInt("remaining_count"),
        rs.getString("status")
    );
  }

  private PTSchedule mapPTSchedule(ResultSet rs) throws SQLException {
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
}
