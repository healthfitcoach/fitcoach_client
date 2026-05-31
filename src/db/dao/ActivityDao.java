package db.dao;

import com.fitcoach.client.model.member.Attendance;
import com.fitcoach.client.model.member.ExerciseRecord;
import com.fitcoach.client.model.point.Point;
import com.fitcoach.client.model.point.PointHistory;
import com.fitcoach.client.model.point.PointPolicy;
import com.fitcoach.client.model.product.MemberProduct;
import db.BaseDao;
import db.DBA;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

public class ActivityDao extends BaseDao {
  public ActivityDao(DBA dba) {
    super(dba);
  }

  // ===================== 출석 =====================

  public boolean hasCheckedInToday(String memberId) {
    try (EntityManager em = openEm()) {
      Long count = em.createQuery(
              "SELECT COUNT(a) FROM Attendance a "
                  + "WHERE a.memberId = :mid AND FUNCTION('DATE', a.attendanceDateTime) = CURRENT_DATE",
              Long.class)
          .setParameter("mid", memberId)
          .getSingleResult();
      return count > 0;
    }
  }

  public List<Attendance> findAttendancesByMemberId(String memberId) {
    try (EntityManager em = openEm()) {
      return em.createQuery(
              "FROM Attendance a WHERE a.memberId = :mid ORDER BY a.attendanceDateTime DESC",
              Attendance.class)
          .setParameter("mid", memberId)
          .getResultList();
    }
  }

  public boolean hasEarnedPointsToday(String memberId) {
    try (EntityManager em = openEm()) {
      Long count = em.createQuery(
              "SELECT COUNT(p) FROM PointHistory p "
                  + "WHERE p.memberId = :mid AND p.date = :today AND p.type = '운동적립'",
              Long.class)
          .setParameter("mid", memberId)
          .setParameter("today", LocalDate.now())
          .getSingleResult();
      return count > 0;
    }
  }

  public boolean saveAttendance(Attendance attendance) {
    try (EntityManager em = openEm()) {
      em.getTransaction().begin();
      em.persist(attendance);
      em.getTransaction().commit();
      return true;
    } catch (Exception e) {
      System.out.println("[JPA 오류] ActivityDao.saveAttendance: " + e.getMessage());
      return false;
    }
  }

  // ===================== 운동 기록 =====================

  public boolean saveExerciseRecord(ExerciseRecord record) {
    try (EntityManager em = openEm()) {
      em.getTransaction().begin();
      em.persist(record);
      em.getTransaction().commit();
      return true;
    } catch (Exception e) {
      System.out.println("[JPA 오류] ActivityDao.saveExerciseRecord: " + e.getMessage());
      return false;
    }
  }

  // ===================== 포인트 =====================

  public Point findPointByMemberId(String memberId) {
    try (EntityManager em = openEm()) {
      return em.createQuery(
              "FROM Point p WHERE p.memberId = :mid", Point.class)
          .setParameter("mid", memberId)
          .getResultStream().findFirst().orElse(null);
    }
  }

  public boolean savePoint(Point point) {
    try (EntityManager em = openEm()) {
      em.getTransaction().begin();
      em.persist(point);
      em.getTransaction().commit();
      return true;
    } catch (Exception e) {
      System.out.println("[JPA 오류] ActivityDao.savePoint: " + e.getMessage());
      return false;
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
      System.out.println("[JPA 오류] ActivityDao.updatePoint: " + e.getMessage());
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
      System.out.println("[JPA 오류] ActivityDao.savePointHistory: " + e.getMessage());
      return false;
    }
  }

  // ===================== 포인트 정책 =====================

  public PointPolicy findCurrentPolicy() {
    try (EntityManager em = openEm()) {
      return em.createQuery(
              "FROM PointPolicy p ORDER BY p.policyId DESC", PointPolicy.class)
          .setMaxResults(1)
          .getResultStream().findFirst().orElse(null);
    }
  }

  // ===================== 회원권 =====================

  public List<MemberProduct> findActiveMembershipsByMemberId(String memberId) {
    try (EntityManager em = openEm()) {
      return em.createQuery(
              "FROM MemberProduct mp WHERE mp.memberId = :mid"
                  + " AND mp.productType = 'MEMBERSHIP' AND mp.status = 'ACTIVE'",
              MemberProduct.class)
          .setParameter("mid", memberId)
          .getResultList();
    }
  }
}
