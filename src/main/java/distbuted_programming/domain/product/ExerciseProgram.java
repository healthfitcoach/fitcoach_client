package distbuted_programming.domain.product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExerciseProgram extends Product {

  private String programId;
  private String name;
  private String description;
  private String instructorId;
  private int price;
  private int capacity;
  private int remainingCapacity;
  private LocalDate startDate;
  private LocalDate endDate;
  private String status;

  private static final List<ExerciseProgram> programs = new ArrayList<>();

  public ExerciseProgram() {}

  public ExerciseProgram(String programId, String name, String description,
      String instructorId, int price, int capacity, int remainingCapacity,
      LocalDate startDate, LocalDate endDate, String status) {
    super(programId, name, price, description, "운동프로그램");
    this.programId = programId;
    this.name = name;
    this.description = description;
    this.instructorId = instructorId;
    this.price = price;
    this.capacity = capacity;
    this.remainingCapacity = remainingCapacity;
    this.startDate = startDate;
    this.endDate = endDate;
    this.status = status;
  }

  public List<ExerciseProgram> getList() {
    return programs;
  }

  @Override
  public ExerciseProgram get() {
    return this;
  }

  public void purchase(String programId) {
    ExerciseProgram prog = findById(programId);
    if (prog == null) {
      System.out.println("[오류] 프로그램을 찾을 수 없습니다.");
      return;
    }
    if (prog.remainingCapacity <= 0) {
      System.out.println("[오류] 해당 프로그램은 현재 정원이 마감되었습니다.");
      return;
    }
    prog.remainingCapacity--;
    System.out.println("[구매] " + prog.name + " 프로그램 구매가 완료되었습니다.");
  }

  public void cancel() {
    this.remainingCapacity++;
    this.status = "취소";
    System.out.println("[취소] " + name + " 프로그램이 취소되었습니다.");
  }

  public String getProgramId() { return programId; }
  public String getName() { return name; }
  public String getDescription() { return description; }
  public String getInstructorId() { return instructorId; }
  public int getPrice() { return price; }
  public int getCapacity() { return capacity; }
  public int getRemainingCapacity() { return remainingCapacity; }
  public LocalDate getStartDate() { return startDate; }
  public LocalDate getEndDate() { return endDate; }
  public String getStatus() { return status; }

  public static List<ExerciseProgram> getAll() { return programs; }
  public static void add(ExerciseProgram p) { programs.add(p); }

  public static ExerciseProgram findById(String programId) {
    for (ExerciseProgram p : programs) {
      if (p.programId.equals(programId)) {
        return p;
      }
    }
    return null;
  }

  @Override
  public String toString() {
    return name + " | " + price + "원 | 잔여정원: " + remainingCapacity + "/" + capacity
        + " | " + startDate + " ~ " + endDate + " | 상태: " + status;
  }
}
