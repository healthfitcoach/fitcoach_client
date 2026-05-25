package db.dao;

import com.fitcoach.client.model.member.Member;
import db.BaseDao;
import db.DBA;
import jakarta.persistence.EntityManager;
import java.util.List;

public class AuthDao extends BaseDao {
  public AuthDao(DBA dba) {
    super(dba);
  }

  public List<Member> findAll() {
    try (EntityManager em = openEm()) {
      return em.createQuery("FROM Member", Member.class).getResultList();
    }
  }

  public Member findByLoginId(String loginId) {
    try (EntityManager em = openEm()) {
      return em.createQuery(
              "FROM Member m WHERE m.loginId = :loginId", Member.class)
          .setParameter("loginId", loginId)
          .getResultStream().findFirst().orElse(null);
    }
  }

  public boolean save(Member member) {
    try (EntityManager em = openEm()) {
      em.getTransaction().begin();
      em.persist(member);
      em.getTransaction().commit();
      return true;
    } catch (Exception e) {
      System.out.println("[JPA 오류] AuthDao.save: " + e.getMessage());
      return false;
    }
  }

  public boolean existsByLoginId(String loginId) {
    try (EntityManager em = openEm()) {
      Long count = em.createQuery(
              "SELECT COUNT(m) FROM Member m WHERE m.loginId = :loginId", Long.class)
          .setParameter("loginId", loginId)
          .getSingleResult();
      return count > 0;
    }
  }
}
