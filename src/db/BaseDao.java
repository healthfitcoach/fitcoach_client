package db;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public abstract class BaseDao {
  protected EntityManagerFactory emf;

  protected BaseDao(DBA dba) {
    this.emf = dba.getEmf();
  }

  public boolean init() {
    return emf != null && emf.isOpen();
  }

  protected EntityManager openEm() {
    return emf.createEntityManager();
  }
}
