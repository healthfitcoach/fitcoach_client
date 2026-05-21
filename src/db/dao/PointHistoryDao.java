package db.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.fitcoach.client.model.point.PointHistory;
import db.DBA;

public class PointHistoryDao {
  private DBA dba;

  public PointHistoryDao(DBA dba) {
    this.dba = dba;
  }

  public boolean init() {
    return dba.isConnected();
  }

  public PointHistory findById(String historyId) {
    String sql = "SELECT * FROM point_history WHERE history_id = ?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, historyId);
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

  public List<PointHistory> findAll() {
    String sql = "SELECT * FROM point_history ORDER BY date DESC";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<PointHistory> list = new ArrayList<>();
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

  public List<PointHistory> findByMemberId(String memberId) {
    String sql = "SELECT * FROM point_history WHERE member_id = ? ORDER BY date DESC";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<PointHistory> list = new ArrayList<>();
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

  public boolean save(PointHistory history) {
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
      System.out.println("DB 오류: " + e.getMessage());
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  public boolean update(PointHistory history) {
    String sql = "UPDATE point_history SET `type`=?, amount=?, reason=?, balance_after=? WHERE history_id=?";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, history.getType());
      pstmt.setInt(2, history.getAmount());
      pstmt.setString(3, history.getReason());
      pstmt.setInt(4, history.getBalanceAfter());
      pstmt.setString(5, history.getHistoryId());
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      System.out.println("DB 오류: " + e.getMessage());
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  public boolean delete(String historyId) {
    String sql = "DELETE FROM point_history WHERE history_id = ?";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, historyId);
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      System.out.println("DB 오류: " + e.getMessage());
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  private PointHistory mapRow(ResultSet rs) throws SQLException {
    return new PointHistory(
        rs.getString("history_id"),
        rs.getString("member_id"),
        rs.getString("type"),
        rs.getInt("amount"),
        rs.getString("reason"),
        rs.getDate("date").toLocalDate(),
        rs.getInt("balance_after")
    );
  }

  private void close(ResultSet rs, PreparedStatement pstmt) {
    if (rs != null) try { rs.close(); } catch (SQLException ignored) {}
    if (pstmt != null) try { pstmt.close(); } catch (SQLException ignored) {}
  }
}
