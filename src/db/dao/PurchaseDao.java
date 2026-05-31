package db.dao;

import com.fitcoach.client.model.order.Order;
import com.fitcoach.client.model.order.Payment;
import com.fitcoach.client.model.point.Point;
import com.fitcoach.client.model.point.PointHistory;
import com.fitcoach.client.model.product.AdditionalProduct;
import com.fitcoach.client.model.product.ExerciseProgram;
import com.fitcoach.client.model.product.MemberProduct;
import com.fitcoach.client.model.product.Membership;
import com.fitcoach.client.model.product.PT;
import com.fitcoach.client.model.product.SportEquipment;
import com.fitcoach.client.model.schedule.PTSchedule;
import com.fitcoach.client.model.schedule.Trainer;
import db.BaseDao;
import db.DBA;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

public class PurchaseDao extends BaseDao {
  public PurchaseDao(DBA dba) {
    super(dba);
  }

  // ===================== 카탈로그 조회 =====================

  public List<Membership> findAllMemberships() {
    try (EntityManager em = openEm()) {
      return em.createQuery("FROM Membership", Membership.class).getResultList();
    }
  }

  public List<ExerciseProgram> findAllPrograms() {
    try (EntityManager em = openEm()) {
      return em.createQuery("FROM ExerciseProgram", ExerciseProgram.class).getResultList();
    }
  }

  public List<SportEquipment> findAllSportEquipments() {
    try (EntityManager em = openEm()) {
      return em.createQuery("FROM SportEquipment", SportEquipment.class).getResultList();
    }
  }

  public List<AdditionalProduct> findAllAdditionalProducts() {
    try (EntityManager em = openEm()) {
      return em.createQuery("FROM AdditionalProduct", AdditionalProduct.class).getResultList();
    }
  }

  public List<PT> findAllPTs() {
    try (EntityManager em = openEm()) {
      return em.createQuery("FROM PT", PT.class).getResultList();
    }
  }

  // ===================== 구매 저장 =====================

  public boolean saveOrder(Order order) {
    try (EntityManager em = openEm()) {
      em.getTransaction().begin();
      em.persist(order);
      em.getTransaction().commit();
      return true;
    } catch (Exception e) {
      System.out.println("[JPA 오류] PurchaseDao.saveOrder: " + e.getMessage());
      return false;
    }
  }

  public boolean savePayment(Payment payment) {
    try (EntityManager em = openEm()) {
      em.getTransaction().begin();
      em.persist(payment);
      em.getTransaction().commit();
      return true;
    } catch (Exception e) {
      System.out.println("[JPA 오류] PurchaseDao.savePayment: " + e.getMessage());
      return false;
    }
  }

  public boolean saveMemberProduct(MemberProduct mp) {
    try (EntityManager em = openEm()) {
      em.getTransaction().begin();
      em.persist(mp);
      em.getTransaction().commit();
      return true;
    } catch (Exception e) {
      System.out.println("[JPA 오류] PurchaseDao.saveMemberProduct: " + e.getMessage());
      return false;
    }
  }

  // ===================== 포인트 =====================

  public Point findPointByMemberId(String memberId) {
    try (EntityManager em = openEm()) {
      return em.createQuery(
              "FROM Point p WHERE p.memberId = :memberId", Point.class)
          .setParameter("memberId", memberId)
          .getResultStream().findFirst().orElse(null);
    }
  }

  public boolean updatePoint(Point point) {
    try (EntityManager em = openEm()) {
      em.getTransaction().begin();
      em.createQuery(
              "UPDATE Point p SET p.balance = :bal, p.expiryDate = :exp WHERE p.pointId = :id")
          .setParameter("bal", point.getBalance())
          .setParameter("exp", point.getExpiryDate())
          .setParameter("id", point.getPointId())
          .executeUpdate();
      em.getTransaction().commit();
      return true;
    } catch (Exception e) {
      System.out.println("[JPA 오류] PurchaseDao.updatePoint: " + e.getMessage());
      return false;
    }
  }

  public boolean savePointHistory(PointHistory history) {
    try (EntityManager em = openEm()) {
      em.getTransaction().begin();
      em.persist(history);
      em.getTransaction().commit();
      return true;
    } catch (Exception e) {
      System.out.println("[JPA 오류] PurchaseDao.savePointHistory: " + e.getMessage());
      return false;
    }
  }

  // ===================== 트레이너 =====================

  public List<Trainer> findAllTrainers() {
    try (EntityManager em = openEm()) {
      return em.createQuery("FROM Trainer", Trainer.class).getResultList();
    }
  }

  public Trainer findTrainerById(String trainerId) {
    try (EntityManager em = openEm()) {
      return em.find(Trainer.class, trainerId);
    }
  }

  // ===================== PT 일정 =====================

  public boolean savePTSchedule(PTSchedule schedule) {
    try (EntityManager em = openEm()) {
      em.getTransaction().begin();
      em.persist(schedule);
      em.getTransaction().commit();
      return true;
    } catch (Exception e) {
      System.out.println("[JPA 오류] PurchaseDao.savePTSchedule: " + e.getMessage());
      return false;
    }
  }

  public boolean isSlotBooked(String trainerId, LocalDateTime dateTime) {
    try (EntityManager em = openEm()) {
      Long count = em.createQuery(
              "SELECT COUNT(p) FROM PTSchedule p "
                  + "WHERE p.trainerId = :tid AND p.date = :date AND p.time = :time "
                  + "AND p.status != 'CANCELLED'", Long.class)
          .setParameter("tid", trainerId)
          .setParameter("date", dateTime.toLocalDate())
          .setParameter("time", dateTime.toLocalTime())
          .getSingleResult();
      return count > 0;
    }
  }

  // ===================== 비즈니스 조회 =====================

  public MemberProduct findActiveMembershipByMemberId(String memberId) {
    try (EntityManager em = openEm()) {
      return em.createQuery(
              "FROM MemberProduct mp WHERE mp.memberId = :mid"
                  + " AND mp.productType = 'MEMBERSHIP' AND mp.status = 'ACTIVE'",
              MemberProduct.class)
          .setParameter("mid", memberId)
          .setMaxResults(1)
          .getResultStream().findFirst().orElse(null);
    }
  }
}
