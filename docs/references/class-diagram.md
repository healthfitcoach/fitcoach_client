# 직원 시스템 Class Diagram 레퍼런스

> PDF 원본: "분산프로그래밍 중간고사 보고서.pdf" — 2.3.5 직원시스템 Class List 기반
> 이 파일로 클래스 구현 전 필드명·타입·관계를 확인한다.

---

## 관계 전체 맵 (한눈에 보기)

```
[Generalization]
Product (abstract)
  ├── MembershipProduct   (회원권상품)
  ├── PTProduct           (PT상품)    ← trainerId (Association→Trainer)
  ├── ExerciseProgram     (운동프로그램) ← instructorId (Association→ExternalInstructor)
  ├── SportEquipment      (운동용품)
  └── AdditionalProduct   (부가상품)

Employee (직원)
  └── Trainer (트레이너)

[Aggregation]
Employee          ◆──  List<Salary>
Trainer           ◆──  List<PTProduct>
PT                ◆──  List<PTSchedule>
ExerciseProgram   ◆──  List<ProgramReservation>
Apparatus         ◆──  List<ApparatusInspectionHistory>
MembershipProduct ◆──  List<Event>
Vendor            ◆──  List<Contract>
Member            ◆──  List<Membership>

[Association — ID만 보관]
PT.trainerId              → Trainer
PT.memberId               → Member
PTSchedule.trainerId      → Trainer
PTSchedule.memberId       → Member
Contract.equipmentId      → SportEquipment
ExerciseProgram.instructorId → ExternalInstructor
Notice.authorEmployeeId   → Employee
FinancialRecord.handlerEmployeeId → Employee
```

---

## 1. Generalization 계층 — Product (abstract)

```java
// 부모 (abstract): Product
// 공통 필드 — 자식이 super()로 초기화
protected String productId;   // 상품ID
protected String name;        // 이름(상품명)
protected int    price;       // 가격(정가)
protected String status;      // 판매상태 ("ON_SALE", "DISCONTINUED" 등)
protected String description; // 설명
protected String type;        // 자식 구분자 (예: "MEMBERSHIP", "PT" 등)
```

### 1-1. MembershipProduct (회원권상품)

| 한글 속성 | Java 필드명 | 타입 |
|-----------|-------------|------|
| 상품ID    | productId   | String (부모) |
| 이름      | name        | String (부모) |
| 정가      | price       | int (부모) |
| 판매상태  | saleStatus  | String (부모 status 활용) |
| 개월수    | monthCount  | int |

```
[Aggregation] List<Event> events  ← 생성자에서 new ArrayList<>()
```

### 1-2. PTProduct (PT상품)

| 한글 속성 | Java 필드명 | 타입 |
|-----------|-------------|------|
| 상품ID    | productId   | String (부모) |
| 상품명    | name        | String (부모) |
| 가격      | price       | int (부모) |
| 판매상태  | saleStatus  | String (부모 status 활용) |
| 횟수      | sessionCount | int |
| PR메시지  | prMessage   | String |
| 트레이너ID | trainerId  | String [Association→Trainer] |

### 1-3. ExerciseProgram (운동프로그램)

| 한글 속성 | Java 필드명 | 타입 |
|-----------|-------------|------|
| 프로그램ID | programId  | String (부모 productId 활용 가능) |
| 이름      | name        | String (부모) |
| 가격      | price       | int (부모) |
| 상태      | status      | String (부모) |
| 설명      | description | String (부모) |
| 시작일    | startDate   | String |
| 종료일    | endDate     | String |
| 정원      | capacity    | int |
| 잔여정원  | remainingCapacity | int |
| 강사ID    | instructorId | String [Association→ExternalInstructor] |

```
[Aggregation] List<ProgramReservation> reservations  ← 생성자에서 new ArrayList<>()
```

### 1-4. SportEquipment (운동용품)

| 한글 속성 | Java 필드명 | 타입 |
|-----------|-------------|------|
| 상품ID    | productId   | String (부모) |
| 이름      | name        | String (부모) |
| 가격      | price       | int (부모) |
| 설명      | description | String (부모) |
| 재고      | stock       | int |
| 카테고리  | category    | String |

### 1-5. AdditionalProduct (부가상품)

| 한글 속성 | Java 필드명 | 타입 |
|-----------|-------------|------|
| 부가상품ID | productId  | String (부모) |
| 이름       | name       | String (부모) |
| 가격       | price      | int (부모) |
| 상태       | status     | String (부모) |
| 이용기간   | usagePeriodDays | int |

---

## 2. Generalization 계층 — Employee

```java
// 부모: Employee
// 공통 필드
protected String employeeId;    // 직원ID
protected String name;          // 이름
protected String phone;         // 연락처
protected String position;      // 직군
protected String email;         // (email은 컨벤션 기준 추가 — 연락처에 포함 가능)
protected int    baseSalary;    // 기본급
protected int    age;           // 나이
protected String gender;        // 성별
protected double incentiveRate; // 인센티브비율
protected String status;        // 상태 ("ACTIVE", "RESIGNED")

[Aggregation] List<Salary> salaries  ← 생성자에서 new ArrayList<>()
```

### 2-1. Trainer (트레이너) extends Employee

| 한글 속성 | Java 필드명 | 타입 |
|-----------|-------------|------|
| (직원 공통 상속) | — | — |
| 트레이너ID | employeeId | String (부모, 동일 필드 사용) |
| 전문분야  | specialty   | String |
| 자격증    | certification | String |
| 평점      | rating      | double |
| 경력      | experienceYears | int |
| 프로필사진 | profileImage | String |

```
[Aggregation] List<PTProduct> ptProducts  ← 생성자에서 new ArrayList<>()
```

---

## 3. 독립 클래스 (23개 중 나머지)

### 3-1. Member (회원)

| 한글 속성 | Java 필드명 | 타입 |
|-----------|-------------|------|
| 회원ID    | memberId    | String |
| 이름      | name        | String |
| 아이디    | loginId     | String |
| 비밀번호  | password    | String |
| 전화번호  | phone       | String |
| 주소      | address     | String |
| 생년월일  | birthDate   | String |
| 닉네임    | nickname    | String |
| 신체정보  | bodyInfo    | String |
| 프로필사진 | profileImage | String |
| 가입일    | joinDate    | String |

```
[Aggregation] List<Membership> memberships  ← 생성자에서 new ArrayList<>()
```

### 3-2. Membership (회원권) — Member에 Aggregation됨

| 한글 속성 | Java 필드명 | 타입 |
|-----------|-------------|------|
| 회원권ID  | membershipId | String |
| 회원ID    | memberId    | String (부모 참조) |
| 타입      | type        | String ("1개월" 등) |
| 가격      | price       | int |
| 상태      | status      | String ("ACTIVE", "PAUSED", "EXPIRED") |
| 시작일    | startDate   | String |
| 종료일    | endDate     | String |
| 일시정지일 | pauseDate  | String |
| 재개예정일 | resumeDate | String |

### 3-3. PT

| 한글 속성 | Java 필드명 | 타입 |
|-----------|-------------|------|
| PTID      | ptId        | String |
| 상태      | status      | String ("ACTIVE", "CANCELLED") |
| 횟수      | totalCount  | int |
| 잔여횟수  | remainingCount | int |
| 가격      | price       | int |
| 트레이너ID | trainerId  | String [Association→Trainer] |
| 회원ID    | memberId    | String [Association→Member] |

```
[Aggregation] List<PTSchedule> schedules  ← 생성자에서 new ArrayList<>()
```

### 3-4. PTSchedule (PT일정) — PT에 Aggregation됨

| 한글 속성 | Java 필드명 | 타입 |
|-----------|-------------|------|
| 일정ID    | scheduleId  | String |
| PTID      | ptId        | String (부모 참조) |
| 날짜      | date        | String |
| 시간      | time        | String |
| 상태      | status      | String ("SCHEDULED", "COMPLETED", "CANCELLED") |
| 트레이너ID | trainerId  | String [Association→Trainer] |
| 회원ID    | memberId    | String [Association→Member] |

### 3-5. ProgramReservation (프로그램예약) — ExerciseProgram에 Aggregation됨

| 한글 속성 | Java 필드명 | 타입 |
|-----------|-------------|------|
| 예약ID    | reservationId | String |
| 프로그램ID | programId   | String (부모 참조) |
| 예약일정  | reservationDate | String |
| 예약상태  | status      | String ("CONFIRMED", "CANCELLED") |
| 회원ID    | memberId    | String [Association→Member] |

### 3-6. ExternalInstructor (외부강사)

| 한글 속성 | Java 필드명 | 타입 |
|-----------|-------------|------|
| 강사ID    | instructorId | String |
| 이름      | name        | String |
| 연락처    | phone       | String |
| 전문분야  | specialty   | String |
| 상태      | status      | String ("ACTIVE", "INACTIVE") |

### 3-7. Apparatus (기구)

| 한글 속성 | Java 필드명 | 타입 |
|-----------|-------------|------|
| 기구ID    | apparatusId | String |
| 이름      | name        | String |
| 카테고리  | category    | String |
| 상태      | status      | String ("ACTIVE", "BROKEN", "DISPOSED") |
| 수량      | quantity    | int |
| 제조사    | manufacturer | String |
| 구입일    | purchaseDate | String |
| 최근점검일 | lastInspectionDate | String |

```
[Aggregation] List<ApparatusInspectionHistory> inspectionHistories  ← 생성자에서 new ArrayList<>()
```

### 3-8. ApparatusInspectionHistory (기구점검이력) — Apparatus에 Aggregation됨

| 한글 속성 | Java 필드명 | 타입 |
|-----------|-------------|------|
| 이력ID    | historyId   | String |
| 기구ID    | apparatusId | String (부모 참조) |
| 점검일    | inspectionDate | String |
| 점검자    | inspector   | String |
| 특이사항  | notes       | String |

### 3-9. Notice (공지사항)

| 한글 속성 | Java 필드명 | 타입 |
|-----------|-------------|------|
| 공지ID    | noticeId    | String |
| 제목      | title       | String |
| 내용      | content     | String |
| 카테고리  | category    | String |
| 작성일    | createdDate | String |
| 첨부파일  | attachment  | String |
| 작성직원ID | authorEmployeeId | String [Association→Employee] |

### 3-10. Attendance (출석)

| 한글 속성 | Java 필드명 | 타입 |
|-----------|-------------|------|
| 출석ID    | attendanceId | String |
| 회원ID    | memberId    | String (Member 참조) |
| 출석일시  | attendanceDateTime | String |
| 인증방법  | authMethod  | String |

### 3-11. Vendor (업체)

| 한글 속성 | Java 필드명 | 타입 |
|-----------|-------------|------|
| 업체ID    | vendorId    | String |
| 업체명    | name        | String |
| 담당자    | contactPerson | String |
| 연락처    | phone       | String |

```
[Aggregation] List<Contract> contracts  ← 생성자에서 new ArrayList<>()
```

### 3-12. Contract (계약) — Vendor에 Aggregation됨

| 한글 속성 | Java 필드명 | 타입 |
|-----------|-------------|------|
| 계약ID    | contractId  | String |
| 업체ID    | vendorId    | String (부모 참조) |
| 계약시작일 | startDate  | String |
| 계약종료일 | endDate    | String |
| 납품주기  | deliveryCycle | String |
| 공급단가  | unitPrice   | int |
| 상태      | status      | String |
| 상품목록  | productList | String |
| 운동용품ID | equipmentId | String [Association→SportEquipment] |

### 3-13. Event (이벤트) — MembershipProduct에 Aggregation됨

| 한글 속성 | Java 필드명 | 타입 |
|-----------|-------------|------|
| 이벤트ID  | eventId     | String |
| 이름      | name        | String |
| 대상상품ID | productId  | String (부모 MembershipProduct 참조) |
| 상태      | status      | String |
| 적용시작일 | startDate  | String |
| 적용종료일 | endDate    | String |
| 할인율    | discountRate | int |
| 할인유형  | discountType | String ("RATE", "AMOUNT") |

### 3-14. FinancialRecord (재정내역)

| 한글 속성 | Java 필드명 | 타입 |
|-----------|-------------|------|
| 내역ID    | recordId    | String |
| 기간시작일 | periodStartDate | String |
| 기간종료일 | periodEndDate   | String |
| 총수입    | totalIncome | int |
| 총지출    | totalExpense | int |
| 순수익    | netProfit   | int |
| 정산상태  | settlementStatus | String |
| 담당직원ID | handlerEmployeeId | String [Association→Employee] |

### 3-15. Salary (급여) — Employee에 Aggregation됨

| 한글 속성 | Java 필드명 | 타입 |
|-----------|-------------|------|
| 급여ID    | salaryId    | String |
| 직원ID    | employeeId  | String (부모 참조) |
| 기본급    | baseSalary  | int |
| 인센티브  | incentive   | int |
| 총급여    | totalSalary | int |
| 지급월    | paymentMonth | String |
| 지급상태  | paymentStatus | String ("PENDING", "PAID") |

### 3-16. Supply (비품)

| 한글 속성 | Java 필드명 | 타입 |
|-----------|-------------|------|
| 비품ID    | supplyId    | String |
| 이름      | name        | String |
| 수량      | quantity    | int |
| 상태      | status      | String |
| 최소재고기준 | minStock  | int |

---

## 4. 구현 시 체크리스트

```
클래스 작성 전 확인:
□ 이 클래스가 다른 클래스를 멤버 변수로 갖는가?
  → YES: Aggregation — 생성자에서 new ArrayList<>()로 초기화
□ 이 클래스가 다른 클래스를 ID로만 참조하는가?
  → YES: Association — String xxxId 필드만 선언, 객체 ❌
□ 이 클래스가 공통 부모를 공유하는가?
  → YES: Generalization — extends + super() 첫 줄 호출

필드 규칙:
□ 모든 필드: private
□ 모든 메서드: public
□ ID 생성: "PREFIX" + System.currentTimeMillis() 형식
□ 문자열 비교: .equals() 사용, == 금지
□ 들여쓰기: 스페이스 2칸
□ import: 와일드카드(*) 금지, 개별 클래스만
```
