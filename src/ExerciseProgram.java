import java.time.LocalDate;

public class ExerciseProgram extends Product {

    private String programId;
    private String instructorId;
    private String status;
    private LocalDate startDate;
    private LocalDate endDate;
    private int capacity;
    private int remainingCapacity;

    public ExerciseProgram(String productId, String productName, int price, String description,
                           String programId, String instructorId, String status,
                           int capacity, int remainingCapacity) {
        super(productId, productName, price, description, "PROGRAM");
        this.programId = programId;
        this.instructorId = instructorId;
        this.status = status;
        this.startDate = null;
        this.endDate = null;
        this.capacity = capacity;
        this.remainingCapacity = remainingCapacity;
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

    public void cancel() {}

    // Getters & Setters
    public String getProgramId() { return programId; }
    public String getInstructorId() { return instructorId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public int getCapacity() { return capacity; }
    public int getRemainingCapacity() { return remainingCapacity; }
    public void setRemainingCapacity(int remainingCapacity) { this.remainingCapacity = remainingCapacity; }
}
