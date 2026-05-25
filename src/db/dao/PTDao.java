package db.dao;

import com.fitcoach.client.model.product.PT;
import com.fitcoach.client.model.schedule.PTSchedule;
import com.fitcoach.client.model.schedule.Trainer;
import db.BaseDao;
import db.DBA;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class PTDao extends BaseDao {
  public PTDao(DBA dba) {
    super(dba);
  }

  // ===================== 트레이너 =====================

  public List<Trainer> findAllTrainers() {
    try (EntityManager em = openEm()) {
      return em.createQuery("FROM Trainer", Trainer.class).getResultList();
    }
  }

  public List<Trainer> searchTrainersBySpecialty(String keyword) {
    try (EntityManager em = openEm()) {
      return em.createQuery(
              "FROM Trainer t WHERE t.specialty LIKE :kw", Trainer.class)
          .setParameter("kw", "%" + keyword + "%")
          .getResultList();
    }
  }

  public Trainer findTrainerById(String trainerId) {
    try (EntityManager em = openEm()) {
      return em.find(Trainer.class, trainerId);
    }
  }

  // ===================== PT =====================

  public List<PT> findActivePTsByMemberId(String memberId) {
    try (EntityManager em = openEm()) {
      return em.createQuery(
              "FROM PT p WHERE p.memberId = :mid AND p.status = 'ACTIVE'", PT.class)
          .setParameter("mid", memberId)
          .getResultList();
    }
  }

  public boolean updatePT(PT pt) {
    try (EntityManager em = openEm()) {
      em.getTransaction().begin();
      em.createQuery(
              "UPDATE PT p SET p.remainingCount = :count, p.status = :status WHERE p.ptId = :id")
          .setParameter("count", pt.getRemainingCount())
          .setParameter("status", pt.getStatus())
          .setParameter("id", pt.getPtId())
          .executeUpdate();
      em.getTransaction().commit();
      return true;
    } catch (Exception e) {
      System.out.println("[JPA 오류] PTDao.updatePT: " + e.getMessage());
      return false;
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
      System.out.println("[JPA 오류] PTDao.savePTSchedule: " + e.getMessage());
      return false;
    }
  }

  public List<PTSchedule> findSchedulesByTrainerAndDate(String trainerId, LocalDate date) {
    try (EntityManager em = openEm()) {
      return em.createQuery(
              "FROM PTSchedule s WHERE s.trainerId = :tid AND s.date = :date ORDER BY s.time ASC",
              PTSchedule.class)
          .setParameter("tid", trainerId)
          .setParameter("date", date)
          .getResultList();
    }
  }

  public boolean isSlotBooked(String trainerId, LocalDateTime dateTime) {
    try (EntityManager em = openEm()) {
      Long count = em.createQuery(
              "SELECT COUNT(s) FROM PTSchedule s "
                  + "WHERE s.trainerId = :tid AND s.date = :date AND s.time = :time "
                  + "AND s.status != 'CANCELLED'", Long.class)
          .setParameter("tid", trainerId)
          .setParameter("date", dateTime.toLocalDate())
          .setParameter("time", dateTime.toLocalTime())
          .getSingleResult();
      return count > 0;
    }
  }
}
