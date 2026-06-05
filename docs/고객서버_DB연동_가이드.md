# 고객 서버 DB 연동 가이드

> 작성자: ERP 서버 담당 (정우주)  
> 대상: 고객 서버 담당 팀원  
> 목적: 단일 DB 공유 구성 시 Entity 및 설정 통일 가이드

---

## 1. 전체 구성 개요

우리 프로젝트는 **ERP 서버**와 **고객 서버** 두 개의 스프링부트 애플리케이션이 **하나의 MySQL DB**를 공유하는 구조야.

```
[고객 서버 :8080]         [ERP 서버 :8081]
       │                        │
       └──────────┬─────────────┘
                  ▼
         [MySQL DB: fitcoach_db]
```

- 스키마 분리 없이 **단일 스키마** 사용
- **ERP 서버**가 테이블 생성 담당 (`ddl-auto: update`)
- **고객 서버**는 기존 테이블 그대로 사용 (`ddl-auto: validate`)

---

## 2. application.yml DB 설정

```yaml
spring:
  datasource:
    url: jdbc:mysql://[DB_HOST]:3306/fitcoach_db?useSSL=false&serverTimezone=Asia/Seoul&allowPublicKeyRetrieval=true
    username: [DB_USER]
    password: [DB_PASSWORD]
    driver-class-name: com.mysql.cj.jdbc.Driver

  jpa:
    hibernate:
      ddl-auto: validate   # ← 고객 서버는 반드시 validate
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQLDialect
        format_sql: true
    show-sql: true
```

> **⚠️ 주의**: 고객 서버는 반드시 `validate`로 설정할 것.  
> ERP 서버가 먼저 실행되어 테이블이 생성된 이후에 고객 서버를 띄워야 함.

---

## 3. 서버 기동 순서

```
1. MySQL DB 컨테이너 실행
2. ERP 서버 실행 (테이블 자동 생성)
3. 고객 서버 실행 (테이블 존재 검증 후 기동)
```

---

## 4. 공유 Entity 목록 (반드시 동일하게 맞춰야 함)

아래 15개 테이블은 ERP/고객 서버가 **동시에 읽고 씀**.  
**테이블명, 컬럼명, 타입이 하나라도 다르면 오류 발생**.  
ERP 쪽 Entity 코드를 기준으로 맞춰줘.

### 4-1. Member (회원)

테이블명: `member`

| 컬럼 | 타입 | 비고 |
|------|------|------|
| id | BIGINT (PK, AUTO) | |
| name | VARCHAR | |
| login_id | VARCHAR (UNIQUE) | |
| password | VARCHAR | |
| phone | VARCHAR | |
| address | VARCHAR | |
| birth_date | DATE | |
| nickname | VARCHAR | |
| body_info | VARCHAR | default '' |
| profile_image | VARCHAR | default '' |
| join_date | DATE | |

### 4-2. Membership (회원권)

테이블명: `membership`

| 컬럼 | 타입 | 비고 |
|------|------|------|
| id | BIGINT (PK, AUTO) | |
| member_id | BIGINT (FK → member) | |
| product_id | BIGINT (FK → product, nullable) | |
| type | VARCHAR | |
| price | INT | |
| status | ENUM(ACTIVE, PAUSED, CANCELLED) | |
| start_date | DATE | |
| end_date | DATE | |
| pause_date | DATE | nullable |
| resume_date | DATE | nullable |

### 4-3. Product (상품 부모 테이블)

테이블명: `product`  
> `MembershipProduct`, `PTProduct`, `SportEquipment`, `ExerciseProgram`, `AdditionalProduct`의 부모. JPA JOINED 전략.

| 컬럼 | 타입 | 비고 |
|------|------|------|
| id | BIGINT (PK, AUTO) | |
| name | VARCHAR | |
| price | INT | |
| description | TEXT | |
| status | ENUM(ON_SALE, OFF_SALE) | |
| type | ENUM(MEMBERSHIP, PT_PRODUCT, SPORT_EQUIPMENT, ADDITIONAL, EXERCISE_PROGRAM) | |

### 4-4. MembershipProduct (회원권 상품)

테이블명: `membership_product`  
PK: `product_id` (FK → product)

| 컬럼 | 타입 | 비고 |
|------|------|------|
| product_id | BIGINT (PK, FK → product) | |
| month_count | INT | |

### 4-5. PTProduct (PT 상품)

테이블명: `pt_product`  
PK: `product_id` (FK → product)

| 컬럼 | 타입 | 비고 |
|------|------|------|
| product_id | BIGINT (PK, FK → product) | |
| session_count | INT | |
| pr_message | VARCHAR | |
| trainer_id | BIGINT (FK → trainer) | |

### 4-6. SportEquipment (운동용품)

테이블명: `sport_equipment`  
PK: `product_id` (FK → product)

| 컬럼 | 타입 | 비고 |
|------|------|------|
| product_id | BIGINT (PK, FK → product) | |
| stock | INT | |
| category | VARCHAR | |

### 4-7. ExerciseProgram (운동프로그램)

테이블명: `exercise_program`  
PK: `product_id` (FK → product)

| 컬럼 | 타입 | 비고 |
|------|------|------|
| product_id | BIGINT (PK, FK → product) | |
| start_date | DATE | |
| end_date | DATE | |
| capacity | INT | |
| remaining_capacity | INT | |
| instructor_id | BIGINT (FK → external_instructor) | |

### 4-8. AdditionalProduct (부가상품)

테이블명: `additional_product`  
PK: `product_id` (FK → product)

| 컬럼 | 타입 | 비고 |
|------|------|------|
| product_id | BIGINT (PK, FK → product) | |
| usage_period_days | INT | |

### 4-9. PT

테이블명: `pt`

| 컬럼 | 타입 | 비고 |
|------|------|------|
| id | BIGINT (PK, AUTO) | |
| status | ENUM(ACTIVE, COMPLETED) | |
| total_count | INT | |
| remaining_count | INT | |
| price | INT | |
| trainer_id | BIGINT (FK → trainer) | |
| member_id | BIGINT (FK → member) | |
| product_id | BIGINT (FK → product, nullable) | |

### 4-10. PTSchedule (PT 일정)

테이블명: `pt_schedule`

| 컬럼 | 타입 | 비고 |
|------|------|------|
| id | BIGINT (PK, AUTO) | |
| pt_id | BIGINT (FK → pt) | |
| trainer_id | BIGINT (FK → trainer) | |
| member_id | BIGINT (FK → member) | |
| schedule_date | DATE | |
| schedule_time | VARCHAR | "14:00" 형식 |
| status | ENUM(SCHEDULED, COMPLETED, CANCELLED) | |

### 4-11. Trainer (트레이너)

테이블명: `trainer`  
PK: `trainer_id` (FK → employee) — JOINED 상속

| 컬럼 | 타입 | 비고 |
|------|------|------|
| trainer_id | BIGINT (PK, FK → employee) | |
| specialty | VARCHAR | |
| certification | VARCHAR | |
| rating | DOUBLE | |
| experience_years | INT | |
| profile_image | VARCHAR | |

> **참고**: `trainer`는 `employee` 테이블과 JOINED 상속 관계.  
> 고객 서버에서 트레이너 이름/연락처 등이 필요하면 `employee` 테이블도 읽어야 함.  
> 단순 조회만 한다면 Entity를 만들지 않고 JPQL로 직접 JOIN 해도 무방.

### 4-12. Attendance (출석)

테이블명: `attendance`

| 컬럼 | 타입 | 비고 |
|------|------|------|
| id | BIGINT (PK, AUTO) | |
| member_id | BIGINT (FK → member) | |
| attendance_date_time | DATETIME | |
| auth_method | VARCHAR | |

### 4-13. ProgramReservation (프로그램 예약)

테이블명: `program_reservation`

| 컬럼 | 타입 | 비고 |
|------|------|------|
| id | BIGINT (PK, AUTO) | |
| program_id | BIGINT (FK → exercise_program.product_id) | |
| member_id | BIGINT (FK → member) | |
| reservation_date | DATE | |
| status | ENUM(CONFIRMED, CANCELLED) | |

### 4-14. Apparatus (기구)

테이블명: `apparatus`

| 컬럼 | 타입 | 비고 |
|------|------|------|
| id | BIGINT (PK, AUTO) | |
| name | VARCHAR | |
| category | VARCHAR | |
| status | ENUM(ACTIVE, INACTIVE) | |
| quantity | INT | |
| manufacturer | VARCHAR | |
| purchase_date | DATE | |
| last_inspection_date | DATE | |

### 4-15. Notice (공지사항)

테이블명: `notice`

| 컬럼 | 타입 | 비고 |
|------|------|------|
| id | BIGINT (PK, AUTO) | |
| title | VARCHAR | |
| content | TEXT | |
| category | VARCHAR | |
| created_date | DATE | |
| attachment | VARCHAR | default '' |
| author_employee_id | BIGINT | 단순 참조 (FK 없음) |

---

## 5. 고객 서버 고유 Entity (자유롭게 설계)

아래 테이블은 ERP에 없으므로 고객 서버 팀이 직접 설계하면 됨.

| 테이블명 (권장) | 역할 |
|----------------|------|
| `orders` | 운동용품 구매 주문 |
| `payment` | 결제 내역 |
| `point` | 포인트 잔액 |
| `point_policy` | 포인트 적립 정책 |
| `point_history` | 포인트 적립/사용 내역 |
| `exercise_record` | 운동 기록 |
| `exercise_method` | 기구별 운동 방법 |

> 이 테이블들은 ERP가 건드리지 않으므로 `ddl-auto: validate`여도  
> 고객 서버가 자체적으로 `CREATE TABLE` 하면 됨.  
> `ddl-auto: create-drop` 또는 Flyway/Liquibase 사용 권장.

---

## 6. 데이터 흐름 예시

```
[고객] 회원가입
  → 고객 서버: INSERT INTO member (...)
  → ERP 서버: SELECT * FROM member  ← 동일 데이터 조회 가능 ✅

[고객] PT 일정 예약
  → 고객 서버: INSERT INTO pt_schedule (...)
  → ERP(트레이너): SELECT * FROM pt_schedule WHERE trainer_id = ?  ← 바로 확인 ✅

[ERP] 회원권 상품 등록
  → ERP 서버: INSERT INTO membership_product (...)
  → 고객 서버: SELECT * FROM membership_product  ← 즉시 구매 목록에 노출 ✅
```

---

## 7. 체크리스트

- [ ] DB 접속 정보 공유받기 (host, port, username, password)
- [ ] `application.yml` ddl-auto → `validate` 로 설정
- [ ] 공유 Entity 15개 컬럼명 ERP 기준으로 맞추기
- [ ] ERP 서버 먼저 기동 후 고객 서버 기동 확인
- [ ] `member` 테이블에 고객 서버가 insert한 데이터가 ERP에서 조회되는지 테스트
