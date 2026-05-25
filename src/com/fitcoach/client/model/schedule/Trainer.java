package com.fitcoach.client.model.schedule;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "trainer")
public class Trainer {

  @Id
  @Column(name = "trainer_id")
  private String trainerId;

  @Column(name = "name")
  private String name;

  @Column(name = "career")
  private String career;

  @Column(name = "certification")
  private String certification;

  @Column(name = "specialty")
  private String specialty;

  @Column(name = "rating")
  private double rating;

  @Column(name = "profile_picture")
  private String profilePicture;

  public Trainer() {}  // JPA 필수 no-arg 생성자

  public Trainer(String trainerId, String name, String career, String certification,
      String specialty, double rating, String profilePicture) {
    this.trainerId = trainerId;
    this.name = name;
    this.career = career;
    this.certification = certification;
    this.specialty = specialty;
    this.rating = rating;
    this.profilePicture = profilePicture;
  }

  public boolean init() { return true; }

  public void search() {}

  public void listAll() {}

  public void checkSchedule() {}

  public void searchDetail() {}

  // Getters & Setters
  public String getTrainerId() { return trainerId; }
  public String getName() { return name; }
  public String getCareer() { return career; }
  public String getCertification() { return certification; }
  public String getSpecialty() { return specialty; }
  public double getRating() { return rating; }
  public String getProfilePicture() { return profilePicture; }
}
