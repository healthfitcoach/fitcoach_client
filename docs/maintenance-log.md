# 유지보수 이슈 로그

> Claude가 코드 작업 중 발견한 문제점/주의사항을 기록합니다.
> 나중에 유지보수 시 참고 자료로 활용하세요.

---

## 심각도 기준

| 레벨 | 기준 |
|------|------|
| 🔴 심각 | 런타임 오류 or 데이터 불일치 유발 가능 |
| 🟡 주의 | 성능 저하 or 유지보수 시 혼란 유발 가능 |
| 🟢 참고 | 개선 아이디어, 리팩토링 여지 |

---

<!-- 아래부터 작업 단위로 기록 추가 -->

## [2026-05-25] DAO Controller 1:1 대응 리팩토링

### 🟡 주의 | PurchaseDao / ActivityDao / MemberDao
**문제**: `findPointByMemberId()`, `updatePoint()`, `savePointHistory()` 메서드가 3개 DAO에 중복 선언됨  
**영향**: Point 관련 SQL 변경 시 3곳을 모두 수정해야 함 — 누락 시 불일치 발생  
**권장 조치**: 추후 `PointDao` 분리 또는 PointRepository 패턴 도입 고려

### 🟡 주의 | PurchaseDao.findAllMemberships / findAllPTs / findAllAdditionalProducts
**문제**: 카탈로그 항목과 회원 구매 인스턴스가 같은 테이블 공유 (`member_id IS NULL` 로 구분)  
**영향**: 카탈로그용 SELECT 쿼리에 `WHERE member_id IS NULL` 조건 필수 — 누락 시 회원 데이터 노출  
**권장 조치**: DB 설계 시 카탈로그 테이블과 인스턴스 테이블 분리 고려

### 🟡 주의 | PurchaseController.createProgramOrder / createEquipmentOrder
**문제**: 프로그램 수강 정원(`remaining_capacity`) 및 운동용품 재고(`stock`) 감소가 DB에 반영되지 않음  
**영향**: 앱 재시작 시 재고/정원이 원래 값으로 복구됨  
**권장 조치**: `PurchaseDao`에 `updateExerciseProgram()`, `updateSportEquipmentStock()` 메서드 추가 필요

### 🟡 주의 | ActivityController.countConsecutiveAttendance
**문제**: 연속 출석 계산 시 매번 해당 회원의 전체 출석 목록을 DB에서 조회  
**영향**: 출석 누적 시 쿼리 비용 증가  
**권장 조치**: 최근 N일치만 조회하도록 SQL에 `LIMIT` 또는 날짜 범위 조건 추가 권장

### 🟡 주의 | ActivityController.earnPoints (포인트 정책 반복 조회)
**문제**: `earnPoints()`, `isTimeBonusApplicable()`, `getTimeBonusStandard()` 등 호출마다 `findCurrentPolicy()` DB 조회 발생  
**영향**: 단일 UC에서 여러 번 중복 조회 가능  
**권장 조치**: Controller 레벨에서 정책을 세션 동안 캐싱하는 방안 고려

### 🟡 주의 | InfoDao.updateNoticeReadStatus
**문제**: `read_by_members` 컬럼이 쉼표 구분 문자열로 회원 ID를 저장하는 구조  
**영향**: 동일 회원이 중복 추가될 수 있음 (중복 체크 없음). 회원 수 증가 시 컬럼 값이 길어짐  
**권장 조치**: 추후 `notice_read` 별도 테이블로 분리 (notice_id, member_id 복합키) 고려

### 🟢 참고 | PurchaseDao.mapMembership
**메모**: `start_date`, `end_date` 컬럼이 카탈로그 항목(member_id IS NULL)일 때 NULL일 수 있음  
null 체크 후 `toLocalDate()` 호출하도록 처리함 — 다른 DAO에서 같은 패턴 사용 시 동일하게 적용 필요
