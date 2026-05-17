package com.fitcoach.client.model.product;

import com.fitcoach.client.model.schedule.PTSchedule;
import java.util.ArrayList;
import java.util.List;

public class PT extends Product {

    private String ptId;
    private String memberId;
    private String trainerId;
    private int totalCount;
    private int remainingCount;
    private String status;
    private List<PTSchedule> schedules;

    public PT(String productId, String productName, int price, String description,
              String ptId, String memberId, String trainerId,
              int totalCount, int remainingCount, String status) {
        super(productId, productName, price, description, "PT");
        this.ptId = ptId;
        this.memberId = memberId;
        this.trainerId = trainerId;
        this.totalCount = totalCount;
        this.remainingCount = remainingCount;
        this.status = status;
        this.schedules = new ArrayList<>();
    }

    @Override
    public boolean init() {
        return true;
    }

    @Override
    public void purchase() {}

    @Override
    public void getDetail() {}

    @Override
    public void search() {}

    public void checkStatus() {}

    public void cancel() {}

    // Getters & Setters
    public String getPtId() { return ptId; }
    public String getMemberId() { return memberId; }
    public void setMemberId(String memberId) { this.memberId = memberId; }
    public String getTrainerId() { return trainerId; }
    public void setTrainerId(String trainerId) { this.trainerId = trainerId; }
    public int getTotalCount() { return totalCount; }
    public int getRemainingCount() { return remainingCount; }
    public void setRemainingCount(int remainingCount) { this.remainingCount = remainingCount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public List<PTSchedule> getSchedules() { return schedules; }
    public void addSchedule(PTSchedule schedule) { this.schedules.add(schedule); }
}
