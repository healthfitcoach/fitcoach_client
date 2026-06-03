package com.fitcoach.client.model.schedule;

public class Trainer {

    private String trainerId;
    private String name;
    private String career;
    private String certification;
    private String specialty;
    private double rating;
    private String profilePicture;

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

    public boolean init() {
        return true;
    }

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
