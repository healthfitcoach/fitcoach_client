# Association 구현 규칙

## 정의
두 클래스가 기능적으로 연관되어 있지만 **생명주기를 공유하지 않는** 관계.
A가 없어져도 B는 독립적으로 존재한다.

## 핵심 규칙

> **Association 대상 클래스는 멤버 변수로 객체를 절대 갖지 않는다.**
> 관계는 **ID(String)** 로 표현하고, 실제 객체는 **메서드 내부에서 Controller를 통해 조회**한다.

---

## Java 구현 패턴

### 1. 클래스 내부: ID만 보관

```java
public class PT {
  private String ptId;
  private int totalCount;
  private int remainingCount;
  private String status;

  // ✅ Association: 객체 참조 대신 ID만 보관
  private String trainerId;  // Trainer 객체 ❌, ID만 ✅
  private String memberId;   // Member 객체 ❌, ID만 ✅

  // ✅ Aggregation: PT일정은 PT 소속이므로 객체 직접 소유
  private List<PTSchedule> schedules;

  public PT(String ptId, String trainerId, String memberId, int count) {
    this.ptId = ptId;
    this.trainerId = trainerId;  // ID만 저장
    this.memberId = memberId;    // ID만 저장
    this.totalCount = count;
    this.remainingCount = count;
    this.status = "ACTIVE";
    this.schedules = new ArrayList<>();
  }

  public String getTrainerId() { return trainerId; }
  public String getMemberId() { return memberId; }
}
```

### 2. Controller: Association 조회 책임

Association 관계에서 실제 객체가 필요한 경우 **Controller가 ID를 받아 조회**한다.
연관된 객체를 사용하는 것은 메서드 내부에서 일어난다.

```java
public class PTController {
  private List<PT> ptList = new ArrayList<>();

  // PT 정보 출력 시 트레이너 이름이 필요한 경우
  public void showPTDetail(String ptId, TrainerController trainerController) {
    PT pt = findById(ptId);

    // ✅ Association: 메서드 내부에서 Controller로 조회
    Trainer trainer = trainerController.findById(pt.getTrainerId());

    System.out.println("PT ID: " + pt.getPtId());
    System.out.println("담당 트레이너: " + trainer.getName());
    System.out.println("잔여 횟수: " + pt.getRemainingCount());
  }

  public PT registerPT(String trainerId, String memberId, int count) {
    String ptId = "PT" + System.currentTimeMillis();
    PT pt = new PT(ptId, trainerId, memberId, count);
    ptList.add(pt);
    return pt;
  }

  public PT findById(String ptId) {
    return ptList.stream()
        .filter(pt -> pt.getPtId().equals(ptId))
        .findFirst()
        .orElse(null);
  }
}
```

### 3. View: Controller에 ID 전달

```java
public class PTView {
  private PTController ptController;
  private TrainerController trainerController;
  private Scanner scanner = new Scanner(System.in);

  public void registerPT() {
    System.out.print("트레이너 ID: ");
    String trainerId = scanner.nextLine();

    System.out.print("회원 ID: ");
    String memberId = scanner.nextLine();

    System.out.print("PT 횟수: ");
    int count = Integer.parseInt(scanner.nextLine());

    // Controller에 ID만 넘김 (객체 전달 ❌)
    ptController.registerPT(trainerId, memberId, count);
  }
}
```

---

## Association 판별 질문

> "A가 삭제될 때 B도 함께 삭제되어야 하는가?"

| 사례 | 판단 | 결론 |
|------|------|------|
| PT가 취소돼도 Trainer는 다른 PT에서 활동 | B는 독립 | **Association** → trainerId 보관 |
| PT가 취소돼도 Member는 다른 서비스 이용 | B는 독립 | **Association** → memberId 보관 |
| Order가 삭제돼도 Member는 다른 주문 가능 | B는 독립 | **Association** → memberId 보관 |
| 운동기록이 삭제돼도 Point는 다른 적립 내역 유지 | B는 독립 | **Association** → pointId 보관 |

---

## ❌ 하지 말아야 할 것

```java
// ❌ Association 관계인데 객체를 멤버로 갖는 것
public class PT {
  private Trainer trainer;  // 절대 안 됨!
  private Member member;    // 절대 안 됨!
}

// ❌ 생성자에서 Association 대상 객체를 new로 생성하는 것
public PT(String ptId) {
  this.trainer = new Trainer(...);  // 절대 안 됨!
}
```

---

## Client 내 Association 관계 목록

```
Order         → Member        : Order.memberId       → MemberController.findById()
Order         → Product       : Order.productId      → PurchaseController.findProductById()
PT            → Trainer       : PT.trainerId         → PTController.findTrainerById()
Payment       → Point         : Payment.pointId      → MemberController.findPointById()
ExerciseRecord→ Point         : (포인트 지급 시 Controller가 조회)
Point         → PointPolicy   : Point.policyId       → ActivityController.findPolicyById()
Member        → Notice        : Notice.readByMembers  → MemberController.findById()
Member        → Product       : (구매 시 Controller가 productId로 조회)
```
