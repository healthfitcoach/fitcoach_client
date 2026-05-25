package db.dao;

import com.fitcoach.client.model.equipment.Equipment;
import com.fitcoach.client.model.equipment.ExerciseMethod;
import com.fitcoach.client.model.notice.Notice;
import db.BaseDao;
import db.DBA;
import jakarta.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;

public class InfoDao extends BaseDao {
  public InfoDao(DBA dba) {
    super(dba);
  }

  // ===================== 기구 =====================

  public List<Equipment> findAllEquipments() {
    try (EntityManager em = openEm()) {
      return em.createQuery("FROM Equipment", Equipment.class).getResultList();
    }
  }

  public List<Equipment> searchEquipments(String keyword) {
    try (EntityManager em = openEm()) {
      String pattern = "%" + keyword + "%";
      return em.createQuery(
              "FROM Equipment e WHERE e.name LIKE :kw OR e.category LIKE :kw",
              Equipment.class)
          .setParameter("kw", pattern)
          .getResultList();
    }
  }

  public List<ExerciseMethod> findAllExerciseMethods() {
    try (EntityManager em = openEm()) {
      return em.createQuery("FROM ExerciseMethod", ExerciseMethod.class).getResultList();
    }
  }

  public List<ExerciseMethod> findExerciseMethodsByEquipmentId(String equipmentId) {
    try (EntityManager em = openEm()) {
      return em.createQuery(
              "FROM ExerciseMethod m WHERE m.equipmentId = :eid", ExerciseMethod.class)
          .setParameter("eid", equipmentId)
          .getResultList();
    }
  }

  // ===================== 공지사항 =====================

  public List<Notice> findAllNotices() {
    try (EntityManager em = openEm()) {
      List<Notice> notices = em.createQuery(
              "FROM Notice n ORDER BY n.writeDate DESC", Notice.class)
          .getResultList();
      // DB의 쉼표 구분 문자열 → Java List 변환
      for (Notice n : notices) {
        String raw = n.getReadByMembersRaw();
        if (raw != null && !raw.isEmpty()) {
          Arrays.stream(raw.split(","))
              .map(String::trim)
              .forEach(id -> n.getReadByMembers().add(id));
        }
      }
      return notices;
    }
  }

  public boolean updateNoticeReadStatus(String noticeId, String memberId) {
    // read_by_members는 쉼표 구분 문자열 — JPQL로 CONCAT 처리
    try (EntityManager em = openEm()) {
      em.getTransaction().begin();
      Notice notice = em.find(Notice.class, noticeId);
      if (notice == null) { em.getTransaction().rollback(); return false; }
      String current = notice.getReadByMembersRaw();
      String updated = (current == null || current.isEmpty())
          ? memberId
          : current + "," + memberId;
      notice.setReadByMembersRaw(updated);
      em.getTransaction().commit();
      return true;
    } catch (Exception e) {
      System.out.println("[JPA 오류] InfoDao.updateNoticeReadStatus: " + e.getMessage());
      return false;
    }
  }
}
