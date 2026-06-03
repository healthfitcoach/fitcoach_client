package db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.fitcoach.client.model.schedule.Trainer;
import db.DBA;

public class TrainerDao {
  private DBA dba;

  public TrainerDao(DBA dba) {
    this.dba = dba;
  }

  public boolean init() {
    return dba.isConnected();
  }

  public Trainer findById(String trainerId) {
    String sql = "SELECT * FROM trainer WHERE trainer_id = ?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, trainerId);
      rs = pstmt.executeQuery();
      if (rs.next()) return mapRow(rs);
      return null;
    } catch (SQLException e) {
      System.out.println("DB 오류: " + e.getMessage());
      return null;
    } finally {
      close(rs, pstmt);
    }
  }

  public List<Trainer> findAll() {
    String sql = "SELECT * FROM trainer";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<Trainer> list = new ArrayList<>();
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next()) list.add(mapRow(rs));
      return list;
    } catch (SQLException e) {
      System.out.println("DB 오류: " + e.getMessage());
      return list;
    } finally {
      close(rs, pstmt);
    }
  }

  public List<Trainer> findBySpecialty(String keyword) {
    String sql = "SELECT * FROM trainer WHERE specialty LIKE ?";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<Trainer> list = new ArrayList<>();
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, "%" + keyword + "%");
      rs = pstmt.executeQuery();
      while (rs.next()) list.add(mapRow(rs));
      return list;
    } catch (SQLException e) {
      System.out.println("DB 오류: " + e.getMessage());
      return list;
    } finally {
      close(rs, pstmt);
    }
  }

  public boolean save(Trainer trainer) {
    String sql = "INSERT INTO trainer (trainer_id, name, career, certification, specialty, rating, profile_picture) "
        + "VALUES (?, ?, ?, ?, ?, ?, ?)";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, trainer.getTrainerId());
      pstmt.setString(2, trainer.getName());
      pstmt.setString(3, trainer.getCareer());
      pstmt.setString(4, trainer.getCertification());
      pstmt.setString(5, trainer.getSpecialty());
      pstmt.setDouble(6, trainer.getRating());
      pstmt.setString(7, trainer.getProfilePicture());
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      System.out.println("DB 오류: " + e.getMessage());
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  public boolean update(Trainer trainer) {
    String sql = "UPDATE trainer SET name=?, career=?, certification=?, specialty=?, rating=?, profile_picture=? "
        + "WHERE trainer_id=?";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, trainer.getName());
      pstmt.setString(2, trainer.getCareer());
      pstmt.setString(3, trainer.getCertification());
      pstmt.setString(4, trainer.getSpecialty());
      pstmt.setDouble(5, trainer.getRating());
      pstmt.setString(6, trainer.getProfilePicture());
      pstmt.setString(7, trainer.getTrainerId());
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      System.out.println("DB 오류: " + e.getMessage());
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  public boolean delete(String trainerId) {
    String sql = "DELETE FROM trainer WHERE trainer_id = ?";
    PreparedStatement pstmt = null;
    try {
      pstmt = dba.getConnection().prepareStatement(sql);
      pstmt.setString(1, trainerId);
      return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
      System.out.println("DB 오류: " + e.getMessage());
      return false;
    } finally {
      close(null, pstmt);
    }
  }

  private Trainer mapRow(ResultSet rs) throws SQLException {
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

  private void close(ResultSet rs, PreparedStatement pstmt) {
    if (rs != null) try { rs.close(); } catch (SQLException ignored) {}
    if (pstmt != null) try { pstmt.close(); } catch (SQLException ignored) {}
  }
}
