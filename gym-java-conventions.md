# 헬스장 관리 시스템 — Java 코딩 컨벤션

**Google Java Style Guide** 기준. 코드 작성 전 반드시 읽고 모든 규칙을 준수하라.

---

## 1. 소스 파일 기본 규칙

| 항목 | 규칙 |
|------|------|
| 인코딩 | UTF-8 |
| 파일명 | 포함된 최상위 클래스명과 동일 (대소문자 구분) |
| 파일 구조 | package 선언 → import 선언 → 클래스 정의 순서 |
| import | 와일드카드(`import java.util.*`) 사용 금지 — 개별 클래스만 import |

---

## 2. 들여쓰기 & 포맷

- **들여쓰기**: 스페이스 **2칸** (탭 금지)
- **줄 길이**: 최대 **100자**
- **중괄호 스타일**: K&R (Egyptian brackets) — 여는 중괄호는 같은 줄 끝에 위치

```java
// ✅ 올바른 예
if (condition) {
  doSomething();
} else {
  doOther();
}

// ❌ 잘못된 예 (새 줄에 여는 중괄호)
if (condition)
{
  doSomething();
}
```

- **빈 블록**: 단일 줄 `{}` 허용 (예외: if/else/try 등 멀티 블록 구문은 각 줄 사용)
- **한 줄에 하나의 statement**만 작성
- 연산자 앞뒤에 공백 하나

---

## 3. 네이밍 컨벤션

| 대상 | 규칙 | 예시 |
|------|------|------|
| 패키지 | 소문자 단어 연속, 언더스코어 없음 | `com.fitcoach.erp` |
| 클래스 / 인터페이스 | UpperCamelCase | `Member`, `Membership`, `FinancialRecord` |
| 메서드 | lowerCamelCase, 동사로 시작 | `findAll()`, `processPayment()` |
| 변수 (로컬 / 필드) | lowerCamelCase | `memberId`, `remainingCount` |
| 상수 (`static final`) | UPPER_SNAKE_CASE | `MAX_RETRY_COUNT` |
| 매개변수 | lowerCamelCase | `trainerId`, `startDate` |

### 프로젝트 클래스명 영문 변환 (필수 준수)

| 한글 | 영문 클래스명 |
|------|--------------|
| 회원 | Member |
| 회원권 | Membership |
| 회원권상품 | MembershipProduct |
| PT | PT |
| PT일정 | PTSchedule |
| PT상품 | PTProduct |
| 운동프로그램 | ExerciseProgram |
| 운동용품 | SportEquipment |
| 운동기록 | ExerciseRecord |
| 운동방법 | ExerciseMethod |
| 트레이너 | Trainer |
| 직원 | Employee |
| 기구 | Apparatus |
| 기구점검이력 | ApparatusInspectionHistory |
| 비품 | Supply |
| 업체 | Vendor |
| 계약 | Contract |
| 공지사항 | Notice |
| 출석 | Attendance |
| 결제 | Payment |
| 포인트 | Point |
| 포인트정책 | PointPolicy |
| 포인트내역 | PointHistory |
| 주문 | Order |
| 부가상품 | AdditionalProduct |
| 외부강사 | ExternalInstructor |
| 이벤트 | Event |
| 알림메시지 | Notification |
| 재정내역 | FinancialRecord |
| 급여 | Salary |
| 프로그램예약 | ProgramReservation |
| 기구(고객용) | Equipment |
| 상품(슈퍼) | Product |

---

## 4. 클래스 구조 규칙

- **속성(Field)**: `private` 가시성 필수
- **메서드**: `public` 가시성 필수
- **생성자**: 클래스 상단, 필드 다음에 위치
- **메서드 순서**: 생성자 → public 메서드 → private 헬퍼 메서드

```java
public class Member {

  // [1] private 속성
  private String memberId;
  private String name;

  // [2] 생성자
  public Member(String memberId, String name) {
    this.memberId = memberId;
    this.name = name;
  }

  // [3] public 메서드
  public Member register(String name, String nickname, String loginId,
      String password, String phone) {
    // ...
    return this;
  }
}
```

---

## 5. TextBaseMain 구조 규칙

### 파일 위치
```
src/TextBaseMain.java
```

### UC 진입 출력 형식 (필수)
```java
System.out.println("[UC01] 재정을 관리한다");
```

### 주석 형식 (필수)
```java
// [UC01] Basic Path - 1단계: 기간 설정
// [UC01] Alternative Path A1 - 수입/지출 조회
// [UC01] Exception E1 - 데이터 없음
```

### Scanner 사용
```java
Scanner scanner = new Scanner(System.in);
// ...
scanner.close(); // 마지막에 닫기
```

### UC 흐름 구현 원칙
- **Basic Path**: 정상 흐름 전체 구현
- **Alternative Path (A1, A2 ...)**: `if/else` 분기로 구현
- **Exception (E1, E2 ...)**: `try-catch` 블록으로 구현
- 사용자 선택은 `scanner.nextLine()` 또는 `scanner.nextInt()` 사용
- 각 UC는 독립적인 `private static void runUCxx(Scanner scanner)` 메서드로 분리

### TextBaseMain 골격
```java
public class TextBaseMain {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    runUC01(scanner); // UC01 재정 관리
    runUC02(scanner); // UC02 직원 관리
    // ...

    scanner.close();
  }

  // [UC01] 재정을 관리한다
  private static void runUC01(Scanner scanner) {
    System.out.println("\n[UC01] 재정을 관리한다");

    // [UC01] Basic Path - 1단계: 기간 설정
    System.out.print("조회 시작일 (yyyy-MM-dd): ");
    String startDate = scanner.nextLine();

    // [UC01] Alternative Path A1 - 수입/지출 선택 조회
    System.out.print("조회 유형 (1:수입 2:지출 3:전체): ");
    String type = scanner.nextLine();
    if ("1".equals(type)) {
      System.out.println("[A1] 수입 내역 조회");
    } else if ("2".equals(type)) {
      System.out.println("[A1] 지출 내역 조회");
    }

    // [UC01] Exception E1 - 데이터 없음
    try {
      System.out.println("[UC01] 재정 내역 출력 완료");
    } catch (Exception e) {
      System.out.println("[E1] 오류: " + e.getMessage());
    }
  }
}
```

---

## 6. 파일 & 패키지 구조

```
src/
├── TextBaseMain.java              ← 진입점, UC 순서대로 실행
├── Employee.java
├── Trainer.java
├── Member.java
├── Membership.java
├── FinancialRecord.java
├── ...                            ← 클래스별 단일 파일
```

- 클래스 하나당 파일 하나
- 모든 소스 파일은 `src/` 하위에 위치
- 별도 패키지 구조 없이 flat하게 관리

---

## 7. 기타 Google Style 핵심 규칙

- `@Override` 어노테이션: 오버라이딩 시 항상 명시
- 빈 catch 블록 금지: 최소 주석 또는 출력 필수
  ```java
  catch (Exception e) {
    System.out.println("[예외] " + e.getMessage());
  }
  ```
- `static` 멤버 접근: 클래스명으로 직접 접근 (인스턴스로 접근 금지)
- 불필요한 괄호 사용 금지
- 문자열 비교: `==` 금지, `.equals()` 사용

---

## 8. 절대 금지 사항

1. Spring / Maven / Gradle / 외부 프레임워크 사용 금지
2. HTTP / 서블릿 / REST API 사용 금지
3. GUI / 웹 UI 사용 금지
4. `TextBaseMain.java` 외 별도 main 메서드 생성 금지
5. `class-spec.md`와 다른 클래스명 / 속성명 / 메서드명 사용 금지
