package db.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.fitcoach.client.model.notice.Notice;
import db.DBA;

public class NoticeDao {
  private DBA dba;

  public NoticeDao(DBA dba) {
    this.dba = dba;
  }

  public boolean init() {
    return dba.isConnected();
  }

  public Notice findById(String noticeId) {
    String sql = "SELECT * FROM notice WHERE notice_id = ?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, noticeId);
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

  public List<Notice> findAll() {
    String sql = "SELECT * FROM notice ORDER BY write_date DESC";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<Notice> list = new ArrayList<>();
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

  public List<Notice> findByCategory(String category) {
    String sql = "SELECT * FROM notice WHERE category = ?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<Notice> list = new ArrayList<>();
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

  public boolean save(Notice notice) {
    String sql = "INSERT INTO notice (notice_id, title, content, category, write_date, attachment, read_by_members) "
        + "VALUES (?, ?, ?, ?, ?, ?, ?)";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, notice.getNoticeId());
      pstmt.setString(2, notice.getTitle());
      pstmt.setString(3, notice.getContent());
      pstmt.setString(4, notice.getCategory());
      pstmt.setDate(5, Date.valueOf(notice.getWriteDate()));
      pstmt.setString(6, notice.getAttachment());
      pstmt.setString(7, String.join(",", notice.getReadByMembers()));
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      System.out.println("DB 오류: " + e.getMessage());
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  public boolean update(Notice notice) {
    String sql = "UPDATE notice SET title=?, content=?, category=?, attachment=?, read_by_members=? "
        + "WHERE notice_id=?";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, notice.getTitle());
      pstmt.setString(2, notice.getContent());
      pstmt.setString(3, notice.getCategory());
      pstmt.setString(4, notice.getAttachment());
      pstmt.setString(5, String.join(",", notice.getReadByMembers()));
      pstmt.setString(6, notice.getNoticeId());
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      System.out.println("DB 오류: " + e.getMessage());
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  public boolean delete(String noticeId) {
    String sql = "DELETE FROM notice WHERE notice_id = ?";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, noticeId);
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      System.out.println("DB 오류: " + e.getMessage());
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  private Notice mapRow(ResultSet rs) throws SQLException {
    Notice notice = new Notice(
        rs.getString("notice_id"),
        rs.getString("title"),
        rs.getString("content"),
        rs.getString("category"),
        rs.getDate("write_date").toLocalDate(),
        rs.getString("attachment")
    );
    String readByStr = rs.getString("read_by_members");
    if (readByStr != null && !readByStr.isEmpty()) {
      for (String memberId : readByStr.split(",")) {
        if (!memberId.trim().isEmpty()) notice.markAsRead(memberId.trim());
      }
    }
    return notice;
  }

  private void close(ResultSet rs, PreparedStatement pstmt) {
    if (rs != null) try { rs.close(); } catch (SQLException ignored) {}
    if (pstmt != null) try { pstmt.close(); } catch (SQLException ignored) {}
  }
}
