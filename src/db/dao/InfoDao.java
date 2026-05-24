package db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.fitcoach.client.model.equipment.Equipment;
import com.fitcoach.client.model.equipment.ExerciseMethod;
import com.fitcoach.client.model.notice.Notice;
import db.BaseDao;
import db.DBA;

public class InfoDao extends BaseDao {
  public InfoDao(DBA dba) {
    super(dba);
  }

  // ===================== 기구 =====================

  public List<Equipment> findAllEquipments() {
    String sql = "SELECT * FROM equipment";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<Equipment> list = new ArrayList<>();
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next()) list.add(mapEquipment(rs));
      return list;
    } catch (SQLException e) {
      logError("InfoDao.findAllEquipments", e);
      return list;
    } finally {
      close(rs, pstmt);
    }
  }

  public List<Equipment> searchEquipments(String keyword) {
    String sql = "SELECT * FROM equipment WHERE name LIKE ? OR category LIKE ?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<Equipment> list = new ArrayList<>();
    try {
      String pattern = "%" + keyword + "%";
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, pattern);
      pstmt.setString(2, pattern);
      rs = pstmt.executeQuery();
      while (rs.next()) list.add(mapEquipment(rs));
      return list;
    } catch (SQLException e) {
      logError("InfoDao.searchEquipments", e);
      return list;
    } finally {
      close(rs, pstmt);
    }
  }

  public List<ExerciseMethod> findAllExerciseMethods() {
    String sql = "SELECT * FROM exercise_method";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<ExerciseMethod> list = new ArrayList<>();
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next()) list.add(mapExerciseMethod(rs));
      return list;
    } catch (SQLException e) {
      logError("InfoDao.findAllExerciseMethods", e);
      return list;
    } finally {
      close(rs, pstmt);
    }
  }

  public List<ExerciseMethod> findExerciseMethodsByEquipmentId(String equipmentId) {
    String sql = "SELECT * FROM exercise_method WHERE equipment_id = ?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<ExerciseMethod> list = new ArrayList<>();
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, equipmentId);
      rs = pstmt.executeQuery();
      while (rs.next()) list.add(mapExerciseMethod(rs));
      return list;
    } catch (SQLException e) {
      logError("InfoDao.findExerciseMethodsByEquipmentId", e);
      return list;
    } finally {
      close(rs, pstmt);
    }
  }

  // ===================== 공지사항 =====================

  public List<Notice> findAllNotices() {
    String sql = "SELECT * FROM notice ORDER BY write_date DESC";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<Notice> list = new ArrayList<>();
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next()) list.add(mapNotice(rs));
      return list;
    } catch (SQLException e) {
      logError("InfoDao.findAllNotices", e);
      return list;
    } finally {
      close(rs, pstmt);
    }
  }

  public boolean updateNoticeReadStatus(String noticeId, String memberId) {
    String sql = "UPDATE notice SET read_by_members = "
        + "CASE WHEN read_by_members IS NULL OR read_by_members = '' "
        + "THEN ? ELSE CONCAT(read_by_members, ',', ?) END "
        + "WHERE notice_id = ?";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, memberId);
      pstmt.setString(2, memberId);
      pstmt.setString(3, noticeId);
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      logError("InfoDao.updateNoticeReadStatus", e);
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  // ===================== mapRow 헬퍼 =====================

  private Equipment mapEquipment(ResultSet rs) throws SQLException {
    return new Equipment(
        rs.getString("equipment_id"),
        rs.getString("name"),
        rs.getString("description"),
        rs.getString("category"),
        rs.getString("status")
    );
  }

  private ExerciseMethod mapExerciseMethod(ResultSet rs) throws SQLException {
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

  private Notice mapNotice(ResultSet rs) throws SQLException {
    Notice notice = new Notice(
        rs.getString("notice_id"),
        rs.getString("title"),
        rs.getString("content"),
        rs.getString("category"),
        rs.getDate("write_date").toLocalDate(),
        rs.getString("attachment")
    );
    String readByMembers = rs.getString("read_by_members");
    if (readByMembers != null && !readByMembers.isEmpty()) {
      Arrays.stream(readByMembers.split(","))
          .forEach(id -> notice.getReadByMembers().add(id.trim()));
    }
    return notice;
  }
}
