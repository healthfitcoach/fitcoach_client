# DAO 리팩토링 체크리스트

> 시퀀스 순서대로 하나씩 완료 후 체크. 이전 시퀀스 미완료 시 다음 진행 금지.

---

## Sequence 1 — 기반 작업
- [x] `docs/maintenance-log.md` 생성 (포맷 헤더 포함)
- [x] `src/db/BaseDao.java` 생성

## Sequence 2 — AuthDao
- [x] `src/db/dao/AuthDao.java` 생성
- [x] 구현 메서드: `findByLoginId`, `save`, `existsByLoginId`

## Sequence 3 — PurchaseDao
- [x] `src/db/dao/PurchaseDao.java` 생성
- [x] 구현 메서드: 카탈로그 조회 5종, `saveOrder`, `savePayment`, `saveMemberMembership`, `saveMemberPT`, Point/PointHistory 관련, Trainer 조회, PTSchedule 저장/중복체크

## Sequence 4 — ActivityDao
- [x] `src/db/dao/ActivityDao.java` 생성
- [x] 구현 메서드: `saveAttendance`, `saveExerciseRecord`, Point CRUD, `savePointHistory`, `findCurrentPolicy`, `findActiveMembershipsByMemberId`

## Sequence 5 — InfoDao
- [x] `src/db/dao/InfoDao.java` 생성
- [x] 구현 메서드: `findAllEquipments`, `searchEquipments`, `findExerciseMethodsByEquipmentId`, `findAllNotices`, `updateNoticeReadStatus`

## Sequence 6 — MemberDao
- [x] `src/db/dao/MemberDao.java` 생성
- [x] 구현 메서드: `findById`, `updateMember`, `findMembershipsByMemberId`, `findOrdersByMemberId`, `findPurchasedProductsByMemberId`, `findPointByMemberId`, `findPointHistoryByMemberId`

## Sequence 7 — PTDao
- [x] `src/db/dao/PTDao.java` 생성
- [x] 구현 메서드: Trainer 조회/검색, `findActivePTsByMemberId`, `updatePT`, `savePTSchedule`, `findSchedulesByTrainerAndDate`, `isSlotBooked`

## Sequence 8 — 기존 DAO 삭제
- [x] 엔티티 DAO 18개 전부 삭제

## Sequence 9 — 검증
- [x] `javac` 전체 컴파일 오류 없음 확인
- [x] `docs/maintenance-log.md` 이슈 기록 여부 확인
