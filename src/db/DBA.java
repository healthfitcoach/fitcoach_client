package db;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DBA {
  private EntityManagerFactory emf;

  public boolean init() {
    try {
      emf = Persistence.createEntityManagerFactory("fitcoach");
      return true;
    } catch (Exception e) {
      System.out.println("JPA 초기화 실패: " + e.getMessage());
      return false;
    }
  }

  public EntityManagerFactory getEmf() { return emf; }

  public boolean isConnected() { return emf != null && emf.isOpen(); }

  public void disconnect() {
    if (emf != null && emf.isOpen()) emf.close();
  }
}
