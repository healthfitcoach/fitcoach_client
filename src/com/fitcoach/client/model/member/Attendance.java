package com.fitcoach.client.model.member;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Attendance {

    private String attendanceId;
    private String memberId;
    private LocalDateTime attendanceDateTime;
    private String authMethod;
    private List<ExerciseRecord> exerciseRecords;

    public Attendance(String attendanceId, String memberId,
                      LocalDateTime attendanceDateTime, String authMethod) {
        this.attendanceId = attendanceId;
        this.memberId = memberId;
        this.attendanceDateTime = attendanceDateTime;
        this.authMethod = authMethod;
        this.exerciseRecords = new ArrayList<>();
    }

    public boolean init() {
        return true;
    }

    public void checkAttendance() {}

    public void search() {}

    // Getters & Setters
    public String getAttendanceId() { return attendanceId; }
    public String getMemberId() { return memberId; }
    public LocalDateTime getAttendanceDateTime() { return attendanceDateTime; }
    public String getAuthMethod() { return authMethod; }
    public List<ExerciseRecord> getExerciseRecords() { return exerciseRecords; }
    public void addExerciseRecord(ExerciseRecord record) { this.exerciseRecords.add(record); }
}
