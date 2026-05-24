package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class BaseDao {
  protected DBA dba;

  protected BaseDao(DBA dba) {
    this.dba = dba;
  }

  public boolean init() {
    return dba.isConnected();
  }

  protected void close(ResultSet rs, PreparedStatement pstmt) {
    if (rs != null) try { rs.close(); } catch (SQLException ignored) {}
    if (pstmt != null) try { pstmt.close(); } catch (SQLException ignored) {}
  }

  protected void logError(String context, SQLException e) {
    System.out.println("[DB 오류] " + context + ": " + e.getMessage());
  }
}
