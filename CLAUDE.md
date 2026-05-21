# 헬스장 Client 시스템 - 구현 가이드

## 코딩 컨벤션

**모든 코드 작성 전 반드시 읽고 준수한다: `gym-java-conventions.md`**

핵심 요약:
- 들여쓰기: **스페이스 2칸** (탭 금지)
- 중괄호: **K&R 스타일** (여는 중괄호 같은 줄)
- 클래스명: **UpperCamelCase**, 메서드/변수: **lowerCamelCase**
- 속성: `private` 필수 / 메서드: `public` 필수
- 문자열 비교: `==` 금지 → `.equals()` 사용
- `@Override` 오버라이딩 시 항상 명시
- import 와일드카드(`*`) 금지 — 개별 클래스만 import
- 외부 프레임워크(Spring 등) 사용 금지, 순수 Java만

---

## 프로젝트 개요

| 항목 | 내용 |
|------|------|
| 과목 | 분산 프로그래밍 (중간고사 프로젝트) |
| 담당 | 이종혁 (60221339) - client(고객) 시스템 담당 |
| 언어 | 순수 Java |
| 패턴 | MVC |
| UI | TUI (Text User Interface, Scanner + ANSI) |
| 저장 | In-memory (DB 연동 예정, 현재 미구현) |
| 범위 | 고객 client 시스템 UseCase 전체 구현 |

---

## 아키텍처

```
Main
 └─ MainView (루프)
      └─ 도메인 View (MemberView, OrderView, PTView, ...)
           └─ Controller 호출 (MemberView, ...)
                └─ Model 데이터 관리 (Member, PT, ...)
```

### 패키지 구조
```
src/
├── Main.java
├── model/          ← 19개 엔티티 클래스
├── controller/     ← 도메인별 Controller (in-memory List 관리)
├── view/           ← TUI 화면 (MainView + 도메인별 View)
└── util/           ← ConsoleUtil (ANSI), InputUtil (Scanner 래퍼)
```

---

## 클래스 관계 구현 규칙

**클래스를 작성할 때는 반드시 아래 분기를 따른다.**

### 관계 판별 흐름
```
다른 클래스를 사용하는가?
│
├── YES → 그 클래스가 이 클래스의 소멸과 함께 사라지는가?
│         │
│         ├── YES → [Aggregation]
│         │         → docs/references/aggregation.md 읽고 구현
│         │         → 멤버 변수 선언 + 생성자에서 new 초기화
│         │
│         └── NO → 공통 속성/행동으로 묶을 수 있는가?
│                  │
│                  ├── YES → [Generalization]
│                  │         → docs/references/generalization.md 읽고 구현
│                  │         → abstract 부모 클래스 + extends 자식 클래스
│                  │
│                  └── NO → [Association]
│                            → docs/references/association.md 읽고 구현
│                            → 객체 멤버 ❌, ID(String)만 보관, 메서드 내부에서 Controller로 조회
│
└── NO → 독립 클래스, 단순 필드 + getter/setter
```

### 빠른 참조

| 상황 | 읽을 파일 |
|------|----------|
| 코딩 컨벤션 / 클래스명 확인 | `gym-java-conventions.md` |
| 멤버 변수로 다른 클래스를 소유하는 경우 | `docs/references/aggregation.md` |
| 다른 클래스를 ID로만 참조하는 경우 | `docs/references/association.md` |
| 상속 구조(부모-자식)를 만드는 경우 | `docs/references/generalization.md` |
| 전체 관계 맵이 필요한 경우 | `docs/SKILL.md` |

---

## Client 클래스 목록 (19개)

### Generalization (상속)
```
Product (abstract)
├── Membership
├── AdditionalProduct
├── ExerciseProgram
├── SportEquipment
└── PT
```

### Aggregation (집합 - 생명주기 공유)
```
Member     ◆── Attendance
Order      ◆── Payment
PT         ◆── PTSchedule
Point      ◆── PointHistory
Attendance ◆── ExerciseRecord
Equipment  ◆── ExerciseMethod
```

### Association (연관)
```
Member        →  Order
Member        →  Product
Order         →  Product
Trainer       →  PT
Payment       →  Point
ExerciseRecord→  Point
Point         →  PointPolicy
Member        →  Notice
```

### Independence (독립)
```
Equipment는 Member와 독립적 — 비로그인 상태에서도 조회 가능
```

> 클래스명 전체 매핑은 `gym-java-conventions.md` 섹션 3 참조

---

## 구현 원칙 요약

1. **Association 최소화**: 객체를 멤버로 갖지 말고 ID만 보관
2. **Aggregation 최대화**: 생명주기가 같은 관계는 반드시 멤버 변수 + 생성자 new
3. **Generalization 적극 발굴**: 공통 명사가 있으면 부모 클래스로 추출
4. **Controller가 Association 조회**: 연관 객체가 필요할 때는 Controller에서 ID로 조회
5. **DB 연동 대비**: Controller의 List<Model> 부분만 나중에 Repository로 교체

---

## 도메인별 UseCase 범위

| Controller | 담당 UC | 주요 기능 |
|-----------|--------|---------|
| AuthController | UC01, 로그인/로그아웃 | 회원가입, 로그인, 로그아웃 |
| PurchaseController | UC02~UC07 | 카테고리 선택, 회원권/프로그램/운동용품/PT 구매, 결제 |
| ActivityController | UC08~UC10 | 헬스장 출석, 운동 기록, 포인트 지급 |
| InfoController | UC11~UC13 | 기구 검색, 운동방법 조회, 공지사항 확인 |
| MemberController | UC14~UC19 | 내정보 조회/수정, 회원권 관리, 부가상품 관리, 포인트 내역 |
| PTController | UC20 | PT 일정 예약 |
