package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBA {
  private String host;
  private int port;
  private String dbName;
  private String user;
  private String password;
  private Connection conn;

  public DBA(String host, int port, String dbName, String user, String password) {
    this.host = host;
    this.port = port;
    this.dbName = dbName;
    this.user = user;
    this.password = password;
    this.conn = null;
  }

  public boolean init() {
    if (isConnected()) return true;
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName
          + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Seoul";
      conn = DriverManager.getConnection(url, user, password);
      return true;
    } catch (ClassNotFoundException e) {
      System.out.println("JDBC 드라이버를 찾을 수 없습니다: " + e.getMessage());
      return false;
    } catch (SQLException e) {
      System.out.println("DB 연결에 실패했습니다: " + e.getMessage());
      return false;
    }
  }

  public Connection getConnection() {
    return conn;
  }

  public void disconnect() {
    if (conn != null) {
      try { conn.close(); } catch (SQLException ignored) {}
      conn = null;
    }
  }

  public boolean isConnected() {
    try {
      return conn != null && !conn.isClosed();
    } catch (SQLException e) {
      return false;
    }
  }
}
