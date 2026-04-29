package distbuted_programming.domain.trainer;

import java.util.ArrayList;
import java.util.List;

public class Trainer {

  private String trainerId;
  private String name;
  private String specialty;
  private String career;
  private String certificate;
  private float rating;
  private String profileImage;

  private static final List<Trainer> trainers = new ArrayList<>();

  public Trainer() {}

  public Trainer(String trainerId, String name, String specialty, String career,
      String certificate, float rating, String profileImage) {
    this.trainerId = trainerId;
    this.name = name;
    this.specialty = specialty;
    this.career = career;
    this.certificate = certificate;
    this.rating = rating;
    this.profileImage = profileImage;
  }

  public List<Trainer> getList() {
    return trainers;
  }

  public Trainer get(String trainerId) {
    for (Trainer t : trainers) {
      if (t.trainerId.equals(trainerId)) {
        return t;
      }
    }
    return null;
  }

  public List<Trainer> search(String specialty, float rating) {
    List<Trainer> result = new ArrayList<>();
    for (Trainer t : trainers) {
      boolean matchSpecialty = specialty == null || specialty.isEmpty()
          || t.specialty.contains(specialty);
      boolean matchRating = t.rating >= rating;
      if (matchSpecialty && matchRating) {
        result.add(t);
      }
    }
    return result;
  }

  public PTSchedule getSchedule(String trainerId) {
    return PTSchedule.findByTrainerId(trainerId);
  }

  public String getTrainerId() { return trainerId; }
  public String getName() { return name; }
  public String getSpecialty() { return specialty; }
  public String getCareer() { return career; }
  public String getCertificate() { return certificate; }
  public float getRating() { return rating; }
  public String getProfileImage() { return profileImage; }

  public static List<Trainer> getAll() { return trainers; }

  public static void add(Trainer t) { trainers.add(t); }

  @Override
  public String toString() {
    return "트레이너: " + name + " | 전문분야: " + specialty
        + " | 경력: " + career + " | 평점: " + rating;
  }
}
