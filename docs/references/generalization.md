# Generalization 구현 규칙

## 정의
공통 속성과 행동을 부모 클래스로 추출하는 상속 관계.
"is-a" 관계가 성립할 때 사용한다. (Membership is-a Product, PT is-a Product)

## 핵심 규칙

> **복합 명사에서 중복 명사를 찾아 부모 클래스로 만들고, `extends`로 상속한다.**
> 공통 속성은 부모에, 고유 속성은 자식에 둔다.
> 부모 클래스는 가능하면 `abstract`로 선언한다.

---

## Generalization 발굴 방법

```
Step 1. 도메인 명사를 나열한다
        회원권, 운동프로그램, 운동용품, 부가상품, PT이용권

Step 2. 복합 명사를 분해한다
        회원권(상품) / 운동-프로그램(상품) / 운동-용품(상품) / 부가-상품 / PT이용권(상품)

Step 3. 중복 명사 = 부모 클래스
        "상품"이 반복 → Product (abstract)

Step 4. 다른 명사 = 자식 클래스
        Membership, ExerciseProgram, SportEquipment, AdditionalProduct, PT
```

---

## Java 구현 패턴

### 1. Product 계층 (상품 Generalization)

```java
// ✅ 부모 클래스: abstract로 선언 (직접 인스턴스화 불필요)
public abstract class Product {
  private String productId;
  private String productName;
  private int price;
  private String description;
  private String type;  // 자식 클래스 구분자

  public Product(String productId, String productName, int price,
      String description, String type) {
    this.productId = productId;
    this.productName = productName;
    this.price = price;
    this.description = description;
    this.type = type;
  }

  public String getProductId() { return productId; }
  public String getProductName() { return productName; }
  public int getPrice() { return price; }
  public String getType() { return type; }
  public String getDescription() { return description; }

  // 자식이 반드시 구현해야 하는 메서드
  public abstract boolean init();
  public abstract void purchase();
  public abstract void getDetail();
  public abstract void search();
}

// ✅ 자식 클래스: 회원권
public class Membership extends Product {
  private String membershipId;
  private String memberId;      // Association: ID만 보관
  private String status;
  private LocalDate startDate;
  private LocalDate endDate;

  public Membership(String productId, String productName, int price,
      String description, String membershipId, String memberId,
      String status, LocalDate startDate, LocalDate endDate) {
    super(productId, productName, price, description, "MEMBERSHIP");
    this.membershipId = membershipId;
    this.memberId = memberId;
    this.status = status;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  @Override
  public boolean init() { return true; }

  @Override
  public void purchase() {}

  @Override
  public void getDetail() {}

  @Override
  public void search() {}

  public String getMembershipId() { return membershipId; }
  public String getMemberId() { return memberId; }
  public String getStatus() { return status; }
  public LocalDate getStartDate() { return startDate; }
  public LocalDate getEndDate() { return endDate; }
}

// ✅ 자식 클래스: PT이용권
public class PT extends Product {
  private String ptId;
  private String memberId;    // Association: ID만 보관
  private String trainerId;   // Association: ID만 보관
  private int totalCount;
  private int remainingCount;
  private String status;

  // ✅ Aggregation: PT일정은 PT 소속
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
    this.schedules = new ArrayList<>();  // ✅ Aggregation 초기화
  }

  @Override
  public boolean init() { return true; }

  @Override
  public void purchase() {}

  @Override
  public void getDetail() {}

  @Override
  public void search() {}

  public void addSchedule(PTSchedule schedule) { this.schedules.add(schedule); }
  public List<PTSchedule> getSchedules() { return Collections.unmodifiableList(schedules); }
  public String getPtId() { return ptId; }
  public String getTrainerId() { return trainerId; }
  public int getRemainingCount() { return remainingCount; }
  public void setRemainingCount(int count) { this.remainingCount = count; }
}

// ✅ 자식 클래스: 운동용품
public class SportEquipment extends Product {
  private int stock;
  private String category;

  public SportEquipment(String productId, String productName, int price,
      String description, int stock, String category) {
    super(productId, productName, price, description, "SPORT_EQUIPMENT");
    this.stock = stock;
    this.category = category;
  }

  @Override public boolean init() { return true; }
  @Override public void purchase() {}
  @Override public void getDetail() {}
  @Override public void search() {}

  public int getStock() { return stock; }
  public void setStock(int stock) { this.stock = stock; }
  public String getCategory() { return category; }
}

// ✅ 자식 클래스: 부가상품
public class AdditionalProduct extends Product {
  private String additionalProductId;
  private String memberId;   // Association: ID만 보관
  private String status;
  private int usagePeriod;

  public AdditionalProduct(String productId, String productName, int price,
      String description, String additionalProductId,
      String memberId, String status, int usagePeriod) {
    super(productId, productName, price, description, "ADDITIONAL");
    this.additionalProductId = additionalProductId;
    this.memberId = memberId;
    this.status = status;
    this.usagePeriod = usagePeriod;
  }

  @Override public boolean init() { return true; }
  @Override public void purchase() {}
  @Override public void getDetail() {}
  @Override public void search() {}

  public int getUsagePeriod() { return usagePeriod; }
  public String getStatus() { return status; }
}

// ✅ 자식 클래스: 운동프로그램
public class ExerciseProgram extends Product {
  private String programId;
  private String instructorId;  // Association: ID만 보관
  private String status;
  private int capacity;
  private int remainingCapacity;

  public ExerciseProgram(String productId, String productName, int price,
      String description, String programId, String instructorId,
      String status, int capacity, int remainingCapacity) {
    super(productId, productName, price, description, "PROGRAM");
    this.programId = programId;
    this.instructorId = instructorId;
    this.status = status;
    this.capacity = capacity;
    this.remainingCapacity = remainingCapacity;
  }

  @Override public boolean init() { return true; }
  @Override public void purchase() {}
  @Override public void getDetail() {}
  @Override public void search() {}

  public int getRemainingCapacity() { return remainingCapacity; }
  public void setRemainingCapacity(int cap) { this.remainingCapacity = cap; }
  public String getInstructorId() { return instructorId; }
}

---

## 주의사항

### 자식에서 부모 생성자 호출
```java
// ✅ super()로 부모 공통 속성 초기화 (반드시 첫 줄)
public Membership(String productId, String productName, int price, ...) {
  super(productId, productName, price, description, "MEMBERSHIP");
  // 이후 자식 고유 속성 초기화
}
```

### @Override 반드시 명시
```java
// ✅ 오버라이딩 시 항상 @Override 붙이기
@Override
public String getDetailInfo() {
  return ...;
}
```

### Controller에서 다형성 활용
```java
public class PurchaseController {
  private List<Product> catalogList = new ArrayList<>();  // 부모 타입으로 관리

  public void addProduct(Product product) {
    catalogList.add(product);  // Membership, SportEquipment, PT 등 모두 수용
  }

  public List<Product> getProductsByType(String type) {
    return catalogList.stream()
        .filter(p -> p.getType().equals(type))
        .collect(Collectors.toList());
  }

  public Product findProductById(String productId) {
    return catalogList.stream()
        .filter(p -> p.getProductId().equals(productId))
        .findFirst()
        .orElse(null);
  }
}
```

---

## Client 내 Generalization 관계 목록

```
Product (abstract)
  ├── Membership       ─ Association → memberId (Member)
  ├── PT               ─ Aggregation → List<PTSchedule>
  │                    ─ Association → memberId (Member), trainerId (Trainer)
  ├── ExerciseProgram  ─ Association → instructorId (Trainer)
  ├── SportEquipment
  └── AdditionalProduct─ Association → memberId (Member)
```
