package com.fitcoach.client.model.schedule;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "trainer")
public class Trainer {

  // trainer_id는 ERP의 employee 테이블과 JOINED 상속 관계 — AUTO_INCREMENT 아님
  @Id
  @Column(name = "trainer_id")
  private Long trainerId;

  @Column(name = "specialty")
  private String specialty;

  @Column(name = "certification")
  private String certification;

  @Column(name = "rating")
  private double rating;

  @Column(name = "experience_years")
  private int experienceYears;

  @Column(name = "profile_image")
  private String profileImage;

  public Trainer() {}

  public Trainer(Long trainerId, String specialty, String certification,
      double rating, int experienceYears, String profileImage) {
    this.trainerId = trainerId;
    this.specialty = specialty;
    this.certification = certification;
    this.rating = rating;
    this.experienceYears = experienceYears;
    this.profileImage = profileImage;
  }

  public Long getTrainerId() { return trainerId; }
  public String getSpecialty() { return specialty; }
  public String getCertification() { return certification; }
  public double getRating() { return rating; }
  public int getExperienceYears() { return experienceYears; }
  public String getProfileImage() { return profileImage; }
}
