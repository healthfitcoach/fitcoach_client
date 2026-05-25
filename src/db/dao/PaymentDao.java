package db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import com.fitcoach.client.model.order.Payment;
import db.DBA;

public class PaymentDao {
  private DBA dba;

  public PaymentDao(DBA dba) {
    this.dba = dba;
  }

  public boolean init() {
    return dba.isConnected();
  }

  public Payment findById(String paymentId) {
    String sql = "SELECT * FROM payment WHERE payment_id = ?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, paymentId);
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

  public List<Payment> findAll() {
    String sql = "SELECT * FROM payment";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<Payment> list = new ArrayList<>();
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

  public List<Payment> findByMemberId(String memberId) {
    String sql = "SELECT * FROM payment WHERE member_id = ?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<Payment> list = new ArrayList<>();
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

  public boolean save(Payment payment) {
    String sql = "INSERT INTO payment (payment_id, member_id, product_id, product_type, "
        + "payment_method, amount, used_points, status, payment_date_time) "
        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, payment.getPaymentId());
      pstmt.setString(2, payment.getMemberId());
      pstmt.setString(3, payment.getProductId());
      pstmt.setString(4, payment.getProductType());
      pstmt.setString(5, payment.getPaymentMethod());
      pstmt.setInt(6, payment.getAmount());
      pstmt.setInt(7, payment.getUsedPoints());
      pstmt.setString(8, payment.getStatus());
      pstmt.setTimestamp(9, Timestamp.valueOf(payment.getPaymentDateTime()));
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      System.out.println("DB 오류: " + e.getMessage());
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  public boolean update(Payment payment) {
    String sql = "UPDATE payment SET status=? WHERE payment_id=?";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, payment.getStatus());
      pstmt.setString(2, payment.getPaymentId());
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      System.out.println("DB 오류: " + e.getMessage());
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  public boolean delete(String paymentId) {
    String sql = "DELETE FROM payment WHERE payment_id = ?";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, paymentId);
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      System.out.println("DB 오류: " + e.getMessage());
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  private Payment mapRow(ResultSet rs) throws SQLException {
    return new Payment(
        rs.getString("payment_id"),
        rs.getString("member_id"),
        rs.getString("product_id"),
        rs.getString("product_type"),
        rs.getString("payment_method"),
        rs.getInt("amount"),
        rs.getInt("used_points"),
        rs.getString("status"),
        rs.getTimestamp("payment_date_time").toLocalDateTime()
    );
  }

  private void close(ResultSet rs, PreparedStatement pstmt) {
    if (rs != null) try { rs.close(); } catch (SQLException ignored) {}
    if (pstmt != null) try { pstmt.close(); } catch (SQLException ignored) {}
  }
}
