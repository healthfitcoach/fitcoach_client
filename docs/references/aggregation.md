# Aggregation 구현 규칙

## 정의
**생명주기를 함께하는 포함 관계.**
부모(Whole)가 소멸하면 자식(Part)도 함께 소멸한다.
단, 자식이 소멸해도 부모는 영향받지 않는다.

## 핵심 규칙

> **자식 클래스를 멤버 변수로 선언하고, 생성자에서 직접 `new`로 초기화한다.**
> 자식 객체는 오직 이 클래스 안에서만 생성되며, 외부에서 주입받지 않는다.

---

## Java 구현 패턴

### 1. 단일 List Aggregation (가장 일반적)

```java
public class Employee {
  private String employeeId;
  private String name;
  private String phone;
  private String position;

  // ✅ Aggregation: 급여 내역은 직원 소속, 직원 삭제 시 함께 삭제
  private List<Salary> salaries;

  public Employee(String employeeId, String name, String phone, String position) {
    this.employeeId = employeeId;
    this.name = name;
    this.phone = phone;
    this.position = position;
    this.salaries = new ArrayList<>();  // ✅ 생성자에서 직접 초기화
  }

  // 급여 추가: 내부에서 Salary 객체를 직접 생성
  public void addSalary(int amount, String month, String note) {
    String salaryId = "SAL" + System.currentTimeMillis();
    this.salaries.add(new Salary(salaryId, this.employeeId, amount, month, note));
  }

  public List<Salary> getSalaries() { return Collections.unmodifiableList(salaries); }
  public String getEmployeeId() { return employeeId; }
  public String getName() { return name; }
}
```

### 2. 다중 Aggregation (한 클래스가 여러 Aggregation 보유)

```java
// PT는 PT일정을 Aggregation으로 가지면서,
// Trainer/Member는 Association으로 ID만 보관
public class PT {
  private String ptId;
  private String trainerId;  // Association: ID만
  private String memberId;   // Association: ID만
  private int totalCount;
  private int remainingCount;
  private String status;

  // ✅ Aggregation: PT일정은 PT 소속
  private List<PTSchedule> schedules;

  public PT(String ptId, String trainerId, String memberId, int count) {
    this.ptId = ptId;
    this.trainerId = trainerId;
    this.memberId = memberId;
    this.totalCount = count;
    this.remainingCount = count;
    this.status = "ACTIVE";
    this.schedules = new ArrayList<>();  // ✅ 직접 초기화
  }

  // 일정 추가: 내부에서 PTSchedule 직접 생성
  public void addSchedule(String date, String time) {
    String scheduleId = "SCH" + System.currentTimeMillis();
    this.schedules.add(new PTSchedule(scheduleId, this.ptId, date, time, "SCHEDULED"));
  }

  public void cancelSchedule(String scheduleId) {
    schedules.stream()
        .filter(s -> s.getScheduleId().equals(scheduleId))
        .findFirst()
        .ifPresent(s -> s.setStatus("CANCELLED"));
  }

  public List<PTSchedule> getSchedules() { return Collections.unmodifiableList(schedules); }
}
```

### 3. 기구-운동방법 Aggregation 예시

```java
public class Equipment {
  private String equipmentId;
  private String name;
  private String description;
  private String category;
  private String status;

  // ✅ Aggregation: 운동방법은 기구 소속, 기구 삭제 시 함께 삭제
  private List<ExerciseMethod> exerciseMethods;

  public Equipment(String equipmentId, String name, String description,
                   String category, String status) {
    this.equipmentId = equipmentId;
    this.name = name;
    this.description = description;
    this.category = category;
    this.status = status;
    this.exerciseMethods = new ArrayList<>();  // ✅ 직접 초기화
  }

  public void addExerciseMethod(ExerciseMethod method) {
    this.exerciseMethods.add(method);
  }

  public List<ExerciseMethod> getExerciseMethods() {
    return Collections.unmodifiableList(exerciseMethods);
  }
}
```

### 4. 포인트-포인트내역 Aggregation 예시

```java
public class Point {
  private String pointId;
  private String memberId;   // Association: ID만 보관
  private int balance;
  private LocalDate expiryDate;

  // ✅ Aggregation: 포인트 내역은 포인트 소속, 포인트 삭제 시 함께 삭제
  private List<PointHistory> histories;

  public Point(String pointId, String memberId, int balance, LocalDate expiryDate) {
    this.pointId = pointId;
    this.memberId = memberId;
    this.balance = balance;
    this.expiryDate = expiryDate;
    this.histories = new ArrayList<>();  // ✅ 직접 초기화
  }

  public void addHistory(PointHistory history) {
    this.histories.add(history);
  }

  public List<PointHistory> getHistories() {
    return Collections.unmodifiableList(histories);
  }
}
```

---

## Aggregation 판별 질문

> "A가 삭제될 때 B도 함께 삭제되는 것이 자연스러운가?"

| 사례 | 판단 | 결론 |
|------|------|------|
| Member 탈퇴 → 해당 회원의 Attendance 기록 삭제 | 함께 소멸 | **Aggregation** |
| Attendance 삭제 → 해당 출석의 ExerciseRecord 삭제 | 함께 소멸 | **Aggregation** |
| PT 취소 → 해당 PT의 모든 PTSchedule 삭제 | 함께 소멸 | **Aggregation** |
| Order 취소 → 해당 주문의 Payment 삭제 | 함께 소멸 | **Aggregation** |
| Point 삭제 → 해당 포인트의 PointHistory 삭제 | 함께 소멸 | **Aggregation** |
| Equipment 폐기 → 해당 기구의 ExerciseMethod 삭제 | 함께 소멸 | **Aggregation** |

---

## Client 내 Aggregation 관계 목록

```
Member(회원)
  └── List<Attendance>  (출석 기록)

Attendance(출석)
  └── List<ExerciseRecord>  (운동 기록)

PT(PT이용권)
  └── List<PTSchedule>  (PT 일정)

Order(주문)
  └── Payment  (결제)

Point(포인트)
  └── List<PointHistory>  (포인트 내역)

Equipment(기구)
  └── List<ExerciseMethod>  (운동 방법)
```

---

## ❌ 하지 말아야 할 것

```java
// ❌ 외부에서 자식 객체를 주입받는 것 (Aggregation이 아닌 Association이 됨)
public Employee(String employeeId, List<Salary> salaries) {
  this.salaries = salaries;  // 안 됨! 내부에서 직접 new로 만들어야 함
}

// ❌ Controller에서 자식 객체를 직접 생성해서 부모에 전달하는 것
Salary salary = new Salary(...);
employee.setSalaries(salary);  // 안 됨! employee.addSalary() 메서드를 통해야 함
```

---

## Controller에서의 Aggregation 처리

```java
public class ActivityController {
  private List<Member> memberList = new ArrayList<>();

  // 출석 추가: Member 객체에게 위임 (Controller가 Attendance를 직접 new하지 않음)
  public void checkAttendance(String memberId, LocalDateTime dateTime) {
    Member member = findById(memberId);
    if (member != null) {
      String attendanceId = "att-" + System.currentTimeMillis();
      member.addAttendance(new Attendance(attendanceId, memberId, dateTime, "QR"));
    }
  }

  public List<Attendance> getAttendanceHistory(String memberId) {
    Member member = findById(memberId);
    return member != null ? member.getAttendances() : Collections.emptyList();
  }

  public Member findById(String memberId) {
    return memberList.stream()
        .filter(m -> m.getMemberId().equals(memberId))
        .findFirst()
        .orElse(null);
  }
}
```
