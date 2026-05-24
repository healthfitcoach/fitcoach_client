package db.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.fitcoach.client.model.order.Order;
import com.fitcoach.client.model.order.Payment;
import com.fitcoach.client.model.point.Point;
import com.fitcoach.client.model.point.PointHistory;
import com.fitcoach.client.model.product.AdditionalProduct;
import com.fitcoach.client.model.product.ExerciseProgram;
import com.fitcoach.client.model.product.Membership;
import com.fitcoach.client.model.product.PT;
import com.fitcoach.client.model.product.SportEquipment;
import com.fitcoach.client.model.schedule.PTSchedule;
import com.fitcoach.client.model.schedule.Trainer;
import db.BaseDao;
import db.DBA;

public class PurchaseDao extends BaseDao {
  public PurchaseDao(DBA dba) {
    super(dba);
  }

  // ===================== 카탈로그 조회 =====================

  public List<Membership> findAllMemberships() {
    String sql = "SELECT * FROM membership WHERE member_id IS NULL";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<Membership> list = new ArrayList<>();
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next()) list.add(mapMembership(rs));
      return list;
    } catch (SQLException e) {
      logError("PurchaseDao.findAllMemberships", e);
      return list;
    } finally {
      close(rs, pstmt);
    }
  }

  public List<ExerciseProgram> findAllPrograms() {
    String sql = "SELECT * FROM exercise_program";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<ExerciseProgram> list = new ArrayList<>();
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next()) list.add(mapExerciseProgram(rs));
      return list;
    } catch (SQLException e) {
      logError("PurchaseDao.findAllPrograms", e);
      return list;
    } finally {
      close(rs, pstmt);
    }
  }

  public List<SportEquipment> findAllSportEquipments() {
    String sql = "SELECT * FROM sport_equipment";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<SportEquipment> list = new ArrayList<>();
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next()) list.add(mapSportEquipment(rs));
      return list;
    } catch (SQLException e) {
      logError("PurchaseDao.findAllSportEquipments", e);
      return list;
    } finally {
      close(rs, pstmt);
    }
  }

  public List<AdditionalProduct> findAllAdditionalProducts() {
    String sql = "SELECT * FROM additional_product WHERE member_id IS NULL";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<AdditionalProduct> list = new ArrayList<>();
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next()) list.add(mapAdditionalProduct(rs));
      return list;
    } catch (SQLException e) {
      logError("PurchaseDao.findAllAdditionalProducts", e);
      return list;
    } finally {
      close(rs, pstmt);
    }
  }

  public List<PT> findAllPTs() {
    String sql = "SELECT * FROM pt WHERE member_id IS NULL";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<PT> list = new ArrayList<>();
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next()) list.add(mapPT(rs));
      return list;
    } catch (SQLException e) {
      logError("PurchaseDao.findAllPTs", e);
      return list;
    } finally {
      close(rs, pstmt);
    }
  }

  // ===================== 구매 저장 =====================

  public boolean saveOrder(Order order) {
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
      logError("PurchaseDao.saveOrder", e);
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  public boolean savePayment(Payment payment) {
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
      logError("PurchaseDao.savePayment", e);
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  public boolean saveMemberMembership(Membership membership) {
    String sql = "INSERT INTO membership (membership_id, product_id, product_name, price, "
        + "description, member_id, status, start_date, end_date) "
        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, membership.getMembershipId());
      pstmt.setString(2, membership.getProductId());
      pstmt.setString(3, membership.getProductName());
      pstmt.setInt(4, membership.getPrice());
      pstmt.setString(5, membership.getDescription());
      pstmt.setString(6, membership.getMemberId());
      pstmt.setString(7, membership.getStatus());
      pstmt.setDate(8, Date.valueOf(membership.getStartDate()));
      pstmt.setDate(9, Date.valueOf(membership.getEndDate()));
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      logError("PurchaseDao.saveMemberMembership", e);
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  public boolean saveMemberPT(PT pt) {
    String sql = "INSERT INTO pt (pt_id, product_id, product_name, price, description, "
        + "member_id, trainer_id, total_count, remaining_count, status) "
        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, pt.getPtId());
      pstmt.setString(2, pt.getProductId());
      pstmt.setString(3, pt.getProductName());
      pstmt.setInt(4, pt.getPrice());
      pstmt.setString(5, pt.getDescription());
      pstmt.setString(6, pt.getMemberId());
      pstmt.setString(7, pt.getTrainerId());
      pstmt.setInt(8, pt.getTotalCount());
      pstmt.setInt(9, pt.getRemainingCount());
      pstmt.setString(10, pt.getStatus());
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      logError("PurchaseDao.saveMemberPT", e);
      return false;
    } finally {
      close(null, pstmt);
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
      logError("PurchaseDao.findPointByMemberId", e);
      return null;
    } finally {
      close(rs, pstmt);
    }
  }

  public boolean updatePoint(Point point) {
    String sql = "UPDATE point SET balance=?, expiry_date=? WHERE point_id=?";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setInt(1, point.getBalance());
      pstmt.setDate(2, Date.valueOf(point.getExpiryDate()));
      pstmt.setString(3, point.getPointId());
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      logError("PurchaseDao.updatePoint", e);
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  public boolean savePointHistory(PointHistory history) {
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
      logError("PurchaseDao.savePointHistory", e);
      return false;
    } finally {
      close(null, pstmt);
    }
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
      logError("PurchaseDao.findAllTrainers", e);
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
      logError("PurchaseDao.findTrainerById", e);
      return null;
    } finally {
      close(rs, pstmt);
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
      logError("PurchaseDao.savePTSchedule", e);
      return false;
    } finally {
      close(null, pstmt);
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
      logError("PurchaseDao.isSlotBooked", e);
      return false;
    } finally {
      close(rs, pstmt);
    }
  }

  // ===================== 비즈니스 조회 =====================

  public Membership findActiveMembershipByMemberId(String memberId) {
    String sql = "SELECT * FROM membership WHERE member_id = ? AND status = 'ACTIVE' LIMIT 1";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, memberId);
      rs = pstmt.executeQuery();
      if (rs.next()) return mapMembership(rs);
      return null;
    } catch (SQLException e) {
      logError("PurchaseDao.findActiveMembershipByMemberId", e);
      return null;
    } finally {
      close(rs, pstmt);
    }
  }

  // ===================== mapRow 헬퍼 =====================

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

  private ExerciseProgram mapExerciseProgram(ResultSet rs) throws SQLException {
    return new ExerciseProgram(
        rs.getString("product_id"),
        rs.getString("product_name"),
        rs.getInt("price"),
        rs.getString("description"),
        rs.getString("program_id"),
        rs.getString("instructor_id"),
        rs.getString("status"),
        rs.getInt("capacity"),
        rs.getInt("remaining_capacity")
    );
  }

  private SportEquipment mapSportEquipment(ResultSet rs) throws SQLException {
    return new SportEquipment(
        rs.getString("product_id"),
        rs.getString("product_name"),
        rs.getInt("price"),
        rs.getString("description"),
        rs.getInt("stock"),
        rs.getString("category")
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

  private Point mapPoint(ResultSet rs) throws SQLException {
    return new Point(
        rs.getString("point_id"),
        rs.getString("member_id"),
        rs.getInt("balance"),
        rs.getDate("expiry_date").toLocalDate()
    );
  }

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
