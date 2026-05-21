# FitCoach 리팩토링 체크리스트

## 역할 분리 원칙
- **Controller**: 순수 비즈니스 로직만 (print 없음, 데이터 반환)
- **View**: 모든 출력/입력 처리 + Controller 호출

## Phase 0: 기반 유틸 생성
- [x] `util/InputUtil.java` — Scanner 래퍼
- [x] `util/ConsoleUtil.java` — 출력 헬퍼

## Phase 1: 컨트롤러 분리 (순수 로직)
- [x] `controller/AuthController.java` — UC01, 로그인, 로그아웃
- [x] `controller/InfoController.java` — UC11, UC12, UC13
- [x] `controller/PTController.java` — UC20
- [x] `controller/ActivityController.java` — UC08, UC09, UC10
- [x] `controller/PurchaseController.java` — UC02~UC07
- [x] `controller/MemberController.java` — UC14~UC19

## Phase 2: 뷰 생성 및 연결
- [x] `view/MainView.java` — 메인 루프 + 라우팅 + 더미 데이터 로드
- [x] `view/AuthView.java`
- [x] `view/PurchaseView.java`
- [x] `view/ActivityView.java`
- [x] `view/InfoView.java`
- [x] `view/MemberView.java`
- [x] `view/PTView.java`

## Phase 3: 최종 정리
- [x] `Main.java` — MainView 사용으로 교체
- [x] `MainController.java` 삭제

## Phase 4: 검증
- [ ] 컴파일 오류 없음 확인
- [ ] 비로그인 기구 검색 동작 확인
- [ ] 로그인 후 전체 20개 UC 진입 확인
- [ ] UC14 → UC16/UC19 서브메뉴 라우팅 확인
- [ ] UC09 → UC10 포인트 연쇄 호출 확인
