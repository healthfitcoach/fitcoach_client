package db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import com.fitcoach.client.model.order.Order;
import db.DBA;

public class OrderDao {
  private DBA dba;

  public OrderDao(DBA dba) {
    this.dba = dba;
  }

  public boolean init() {
    return dba.isConnected();
  }

  public Order findById(String orderId) {
    String sql = "SELECT * FROM orders WHERE order_id = ?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, orderId);
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

  public List<Order> findAll() {
    String sql = "SELECT * FROM orders";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<Order> list = new ArrayList<>();
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

  public List<Order> findByMemberId(String memberId) {
    String sql = "SELECT * FROM orders WHERE member_id = ?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<Order> list = new ArrayList<>();
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

  public boolean save(Order order) {
    String sql = "INSERT INTO orders (order_id, member_id, product_id, quantity, total_amount, "
        + "shipping_address, status, order_date_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, order.getOrderId());
      pstmt.setString(2, order.getMemberId());
      pstmt.setString(3, order.getProductId());
      pstmt.setInt(4, order.getQuantity());
      pstmt.setInt(5, order.getTotalAmount());
      pstmt.setString(6, order.getShippingAddress());
      pstmt.setString(7, order.getStatus());
      pstmt.setTimestamp(8, Timestamp.valueOf(order.getOrderDateTime()));
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      System.out.println("DB 오류: " + e.getMessage());
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  public boolean update(Order order) {
    String sql = "UPDATE orders SET status=? WHERE order_id=?";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, order.getStatus());
      pstmt.setString(2, order.getOrderId());
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      System.out.println("DB 오류: " + e.getMessage());
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  public boolean delete(String orderId) {
    String sql = "DELETE FROM orders WHERE order_id = ?";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, orderId);
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      System.out.println("DB 오류: " + e.getMessage());
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  private Order mapRow(ResultSet rs) throws SQLException {
    return new Order(
        rs.getString("order_id"),
        rs.getString("member_id"),
        rs.getString("product_id"),
        rs.getInt("quantity"),
        rs.getInt("total_amount"),
        rs.getString("shipping_address"),
        rs.getString("status"),
        rs.getTimestamp("order_date_time").toLocalDateTime()
    );
  }

  private void close(ResultSet rs, PreparedStatement pstmt) {
    if (rs != null) try { rs.close(); } catch (SQLException ignored) {}
    if (pstmt != null) try { pstmt.close(); } catch (SQLException ignored) {}
  }
}
