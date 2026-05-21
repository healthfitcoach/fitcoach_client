---
name: gym-client-java-reference
description: >
  헬스장 Client 시스템 Java 클래스 구현 시 반드시 참조하는 스킬.
  클래스 작성, 관계 구현(Association/Aggregation/Generalization), MVC 구조 코딩 요청 시 항상 사용.
  "클래스 만들어", "모델 구현", "관계 코딩", "클라이언트 자바 코드", "MVC 작성" 등에 반드시 트리거.
---

# 헬스장 Client Java 구현 스킬

클래스를 구현하기 전에 반드시 아래 순서를 따른다.

---

## Step 1. 관계 판별

구현할 클래스가 다른 클래스와 어떤 관계인지 먼저 판별한다.

```
해당 클래스가 다른 클래스를 사용하는가?
│
├── NO  → 독립 클래스, 단순 필드 + getter/setter 구현
│
└── YES → 그 객체가 이 클래스의 소멸과 함께 사라지는가?
          │
          ├── YES → [Aggregation]
          │         references/aggregation.md 를 읽고 구현
          │
          └── NO  → 이 클래스와 같은 도메인 계층에 속하는가?
                    │
                    ├── YES (공통 속성/행동 존재) → [Generalization]
                    │                              references/generalization.md 를 읽고 구현
                    │
                    └── NO  → [Association]
                              references/association.md 를 읽고 구현
```

---

## Step 2. 해당 레퍼런스 읽기

| 관계 | 읽을 파일 | 핵심 키워드 |
|------|----------|-----------|
| Association | `references/association.md` | 메서드 내부 호출, ID 저장, 객체 멤버 ❌ |
| Aggregation | `references/aggregation.md` | 멤버 변수, 생성자 new, 생명주기 공유 |
| Generalization | `references/generalization.md` | extends, abstract, 복합명사 발굴 |

레퍼런스를 읽은 후 해당 패턴대로 구현한다.

---

## Step 3. Client 전체 관계 맵 (빠른 참조)

### Aggregation (멤버 변수 + 생성자 new)
```
Member(회원)     ◆──  List<Attendance>(출석)
Attendance(출석) ◆──  List<ExerciseRecord>(운동기록)
PT(PT이용권)     ◆──  List<PTSchedule>(PT일정)
Order(주문)      ◆──  Payment(결제)
Point(포인트)    ◆──  List<PointHistory>(포인트내역)
Equipment(기구)  ◆──  List<ExerciseMethod>(운동방법)
```

### Association (ID 저장 + 메서드 내부에서 Controller로 조회)
```
Order         →  Member       (memberId String 보관)
Order         →  Product      (productId String 보관)
PT            →  Trainer      (trainerId String 보관)
PT            →  Member       (memberId String 보관)
Payment       →  Point        (pointId String 보관)
ExerciseRecord→  Point        (포인트 지급 시 Controller 조회)
Point         →  PointPolicy  (policyId String 보관)
Member        →  Notice       (readByMembers List<String>)
```

### Generalization (extends)
```
Product (abstract)
  ├── Membership      (회원권)
  ├── PT              (PT이용권)
  ├── ExerciseProgram (운동프로그램)
  ├── SportEquipment  (운동용품)
  └── AdditionalProduct (부가상품)
```

> 클래스명 전체 매핑: `gym-java-conventions.md` 섹션 3 참조

---

## Step 4. 프로젝트 구조 참고

```
MVC 패턴:
Main → MainView(루프) → 도메인 View → Controller → Model 데이터 관리

패키지:
com/fitcoach/client/
  model/      ← 19개 클래스 (이 스킬 규칙대로 구현)
    member/   ← Member, Attendance, ExerciseRecord
    product/  ← Product, Membership, PT, ExerciseProgram, SportEquipment, AdditionalProduct
    schedule/ ← Trainer, PTSchedule
    order/    ← Order, Payment
    point/    ← Point, PointPolicy, PointHistory
    equipment/← Equipment, ExerciseMethod
    notice/   ← Notice
  controller/ ← 도메인별 Controller, Association 조회 담당
  view/       ← MainView + 도메인별 View (TUI)
  util/       ← ConsoleUtil, InputUtil
```

---

---

## Step 5. UseCase / 클래스 다이어그램 참조

| 필요한 정보 | 읽을 파일 |
|------------|----------|
| 클래스 필드명·타입·관계 전체 | `system_design.md` |
| 초기 데이터 세팅 값 | `initial_data.md` |
| UC01 회원가입 | `usecases/UC01_SignUp.md` |
| UC02 구매 | `usecases/UC02_Purchase.md` |
| UC03 회원권 구매 | `usecases/UC03_PurchaseMembership.md` |
| UC04 헬스 프로그램 구매 | `usecases/UC04_PurchaseProgram.md` |
| UC05 운동용품 구매 | `usecases/UC05_PurchaseEquipment.md` |
| UC06 PT 구매 | `usecases/UC06_PurchasePT.md` |
| UC07 결제 | `usecases/UC07_Pay.md` |
| UC08 헬스장 출석 | `usecases/UC08_CheckAttendance.md` |
| UC09 운동 기록 | `usecases/UC09_RecordExercise.md` |
| UC10 포인트 지급 | `usecases/UC10_EarnPoints.md` |
| UC11 기구 검색 | `usecases/UC11_SearchEquipment.md` |
| UC12 운동방법 조회 | `usecases/UC12_ViewExerciseMethod.md` |
| UC13 공지사항 확인 | `usecases/UC13_ViewNotice.md` |
| UC14 내정보 관리 | `usecases/UC14_ManageMyInfo.md` |
| UC15 내정보 수정 | `usecases/UC15_UpdateMyInfo.md` |
| UC16 회원권 관리 | `usecases/UC16_ManageMembership.md` |
| UC17 잔여기간 조회 | `usecases/UC17_CheckRemainingPeriod.md` |
| UC18 부가상품 관리 | `usecases/UC18_ManageAdditionalProduct.md` |
| UC19 포인트 내역 조회 | `usecases/UC19_ViewPointHistory.md` |
| UC20 PT 일정 예약 | `usecases/UC20_SchedulePT.md` |

---

> 세부 구현 규칙은 반드시 `references/` 안의 해당 파일을 읽고 따른다.
