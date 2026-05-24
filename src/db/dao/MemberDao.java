package db.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import com.fitcoach.client.model.member.Member;
import com.fitcoach.client.model.order.Order;
import com.fitcoach.client.model.point.Point;
import com.fitcoach.client.model.point.PointHistory;
import com.fitcoach.client.model.product.AdditionalProduct;
import com.fitcoach.client.model.product.Membership;
import db.BaseDao;
import db.DBA;

public class MemberDao extends BaseDao {
  public MemberDao(DBA dba) {
    super(dba);
  }

  // ===================== 회원 정보 =====================

  public Member findById(String memberId) {
    String sql = "SELECT * FROM member WHERE member_id = ?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, memberId);
      rs = pstmt.executeQuery();
      if (rs.next()) return mapMember(rs);
      return null;
    } catch (SQLException e) {
      logError("MemberDao.findById", e);
      return null;
    } finally {
      close(rs, pstmt);
    }
  }

  public Member findByPhone(String phoneNumber) {
    String sql = "SELECT * FROM member WHERE phone_number = ?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, phoneNumber);
      rs = pstmt.executeQuery();
      if (rs.next()) return mapMember(rs);
      return null;
    } catch (SQLException e) {
      logError("MemberDao.findByPhone", e);
      return null;
    } finally {
      close(rs, pstmt);
    }
  }

  public boolean updateMember(Member member) {
    String sql = "UPDATE member SET login_id=?, password=?, name=?, nickname=?, "
        + "phone_number=?, birth_date=?, physical_info=?, address=?, profile_picture=? "
        + "WHERE member_id=?";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, member.getLoginId());
      pstmt.setString(2, member.getPassword());
      pstmt.setString(3, member.getName());
      pstmt.setString(4, member.getNickname());
      pstmt.setString(5, member.getPhoneNumber());
      pstmt.setString(6, member.getBirthDate());
      pstmt.setString(7, member.getPhysicalInfo());
      pstmt.setString(8, member.getAddress());
      pstmt.setString(9, member.getProfilePicture());
      pstmt.setString(10, member.getMemberId());
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      logError("MemberDao.updateMember", e);
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  // ===================== 회원권 =====================

  public List<Membership> findMembershipsByMemberId(String memberId) {
    String sql = "SELECT * FROM membership WHERE member_id = ?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<Membership> list = new ArrayList<>();
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, memberId);
      rs = pstmt.executeQuery();
      while (rs.next()) list.add(mapMembership(rs));
      return list;
    } catch (SQLException e) {
      logError("MemberDao.findMembershipsByMemberId", e);
      return list;
    } finally {
      close(rs, pstmt);
    }
  }

  // ===================== 주문 =====================

  public List<Order> findOrdersByMemberId(String memberId) {
    String sql = "SELECT * FROM orders WHERE member_id = ? ORDER BY order_date_time DESC";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<Order> list = new ArrayList<>();
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, memberId);
      rs = pstmt.executeQuery();
      while (rs.next()) list.add(mapOrder(rs));
      return list;
    } catch (SQLException e) {
      logError("MemberDao.findOrdersByMemberId", e);
      return list;
    } finally {
      close(rs, pstmt);
    }
  }

  // ===================== 부가상품 =====================

  public List<AdditionalProduct> findPurchasedProductsByMemberId(String memberId) {
    String sql = "SELECT * FROM additional_product WHERE member_id = ?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<AdditionalProduct> list = new ArrayList<>();
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, memberId);
      rs = pstmt.executeQuery();
      while (rs.next()) list.add(mapAdditionalProduct(rs));
      return list;
    } catch (SQLException e) {
      logError("MemberDao.findPurchasedProductsByMemberId", e);
      return list;
    } finally {
      close(rs, pstmt);
    }
  }

  // ===================== 포인트 =====================

  public Point findPointByMemberId(String memberId) {
    String sql = "SELECT * FROM point WHERE member_id = ?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, memberId);
      rs = pstmt.executeQuery();
      if (rs.next()) return mapPoint(rs);
      return null;
    } catch (SQLException e) {
      logError("MemberDao.findPointByMemberId", e);
      return null;
    } finally {
      close(rs, pstmt);
    }
  }

  public List<PointHistory> findPointHistoryByMemberId(String memberId) {
    String sql = "SELECT * FROM point_history WHERE member_id = ? ORDER BY date DESC";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<PointHistory> list = new ArrayList<>();
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, memberId);
      rs = pstmt.executeQuery();
      while (rs.next()) list.add(mapPointHistory(rs));
      return list;
    } catch (SQLException e) {
      logError("MemberDao.findPointHistoryByMemberId", e);
      return list;
    } finally {
      close(rs, pstmt);
    }
  }

  // ===================== mapRow 헬퍼 =====================

  private Member mapMember(ResultSet rs) throws SQLException {
    return new Member(
        rs.getString("member_id"),
        rs.getString("login_id"),
        rs.getString("password"),
        rs.getString("name"),
        rs.getString("nickname"),
        rs.getString("phone_number"),
        rs.getString("birth_date"),
        rs.getString("physical_info"),
        rs.getString("address"),
        rs.getString("profile_picture"),
        rs.getDate("join_date").toLocalDate()
    );
  }

  private Membership mapMembership(ResultSet rs) throws SQLException {
    Date startDate = rs.getDate("start_date");
    Date endDate = rs.getDate("end_date");
    return new Membership(
        rs.getString("product_id"),
        rs.getString("product_name"),
        rs.getInt("price"),
        rs.getString("description"),
        rs.getString("membership_id"),
        rs.getString("member_id"),
        rs.getString("status"),
        startDate != null ? startDate.toLocalDate() : null,
        endDate != null ? endDate.toLocalDate() : null
    );
  }

  private Order mapOrder(ResultSet rs) throws SQLException {
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

  private AdditionalProduct mapAdditionalProduct(ResultSet rs) throws SQLException {
    return new AdditionalProduct(
        rs.getString("product_id"),
        rs.getString("product_name"),
        rs.getInt("price"),
        rs.getString("description"),
        rs.getString("additional_product_id"),
        rs.getString("member_id"),
        rs.getString("status"),
        rs.getInt("usage_period")
    );
  }

  private Point mapPoint(ResultSet rs) throws SQLException {
    return new Point(
        rs.getString("point_id"),
        rs.getString("member_id"),
        rs.getInt("balance"),
        rs.getDate("expiry_date").toLocalDate()
    );
  }

  private PointHistory mapPointHistory(ResultSet rs) throws SQLException {
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
}
