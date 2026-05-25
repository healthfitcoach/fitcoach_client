package db.dao;

import com.fitcoach.client.model.member.Member;
import com.fitcoach.client.model.order.Order;
import com.fitcoach.client.model.point.Point;
import com.fitcoach.client.model.point.PointHistory;
import com.fitcoach.client.model.product.AdditionalProduct;
import com.fitcoach.client.model.product.Membership;
import db.BaseDao;
import db.DBA;
import jakarta.persistence.EntityManager;
import java.util.List;

public class MemberDao extends BaseDao {
  public MemberDao(DBA dba) {
    super(dba);
  }

  // ===================== 회원 정보 =====================

  public Member findById(String memberId) {
    try (EntityManager em = openEm()) {
      return em.find(Member.class, memberId);
    }
  }

  public Member findByPhone(String phoneNumber) {
    try (EntityManager em = openEm()) {
      return em.createQuery(
              "FROM Member m WHERE m.phoneNumber = :phone", Member.class)
          .setParameter("phone", phoneNumber)
          .getResultStream().findFirst().orElse(null);
    }
  }

  public boolean updateMember(Member member) {
    try (EntityManager em = openEm()) {
      em.getTransaction().begin();
      em.createQuery(
              "UPDATE Member m SET m.loginId = :lid, m.password = :pw, m.name = :name, "
                  + "m.nickname = :nick, m.phoneNumber = :phone, m.birthDate = :birth, "
                  + "m.physicalInfo = :physical, m.address = :addr, m.profilePicture = :pic "
                  + "WHERE m.memberId = :mid")
          .setParameter("lid", member.getLoginId())
          .setParameter("pw", member.getPassword())
          .setParameter("name", member.getName())
          .setParameter("nick", member.getNickname())
          .setParameter("phone", member.getPhoneNumber())
          .setParameter("birth", member.getBirthDate())
          .setParameter("physical", member.getPhysicalInfo())
          .setParameter("addr", member.getAddress())
          .setParameter("pic", member.getProfilePicture())
          .setParameter("mid", member.getMemberId())
          .executeUpdate();
      em.getTransaction().commit();
      return true;
    } catch (Exception e) {
      System.out.println("[JPA 오류] MemberDao.updateMember: " + e.getMessage());
      return false;
    }
  }

  // ===================== 회원권 =====================

  public List<Membership> findMembershipsByMemberId(String memberId) {
    try (EntityManager em = openEm()) {
      return em.createQuery(
              "FROM Membership m WHERE m.memberId = :mid", Membership.class)
          .setParameter("mid", memberId)
          .getResultList();
    }
  }

  // ===================== 주문 =====================

  public List<Order> findOrdersByMemberId(String memberId) {
    try (EntityManager em = openEm()) {
      return em.createQuery(
              "FROM Order o WHERE o.memberId = :mid ORDER BY o.orderDateTime DESC",
              Order.class)
          .setParameter("mid", memberId)
          .getResultList();
    }
  }

  // ===================== 부가상품 =====================

  public List<AdditionalProduct> findPurchasedProductsByMemberId(String memberId) {
    try (EntityManager em = openEm()) {
      return em.createQuery(
              "FROM AdditionalProduct a WHERE a.memberId = :mid", AdditionalProduct.class)
          .setParameter("mid", memberId)
          .getResultList();
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

  public List<PointHistory> findPointHistoryByMemberId(String memberId) {
    try (EntityManager em = openEm()) {
      return em.createQuery(
              "FROM PointHistory p WHERE p.memberId = :mid ORDER BY p.date DESC",
              PointHistory.class)
          .setParameter("mid", memberId)
          .getResultList();
    }
  }
}
