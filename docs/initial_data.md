# initial_data.md - 프로그램 시작 시 초기 데이터

이 파일은 GymSystem 초기화 시 In-Memory에 세팅할 데이터 목록이다.
GymDataInitializer 클래스(또는 GymSystem.init() 내부)에서 아래 데이터를 생성하여 등록한다.

---

## 1. 회원권 상품 (Membership)

| 상품명    | 기간   | 가격       |
|-----------|--------|------------|
| 1개월권   | 30일   | 99,000원   |
| 3개월권   | 90일   | 270,000원  |
| 6개월권   | 180일  | 500,000원  |
| 1년권     | 365일  | 900,000원  |

status: ACTIVE
이용 가능 시설: 헬스장 전체 시설

---

## 2. 운동 프로그램 (ExerciseProgram)

| 프로그램명     | 정원 | 잔여정원 | 가격      | 강사ID       |
|----------------|------|----------|-----------|--------------|
| 요가           | 15   | 15       | 80,000원  | trainer-001  |
| 줌바           | 20   | 20       | 70,000원  | trainer-002  |
| 다이어트 댄스  | 20   | 20       | 70,000원  | trainer-002  |
| 스피닝         | 15   | 15       | 90,000원  | trainer-003  |

status: ACTIVE
시작일: 프로그램 구매 시 고객이 선택

---

## 3. 트레이너 (Trainer)

트레이너 1:
  trainerId: trainer-001
  name: 김민준
  specialty: 요가, 필라테스
  career: 경력 7년
  certification: 생활스포츠지도사 2급, 요가지도자 1급
  rating: 4.8
  profilePicture: trainer_01.jpg

트레이너 2:
  trainerId: trainer-002
  name: 이서연
  specialty: 댄스, 유산소
  career: 경력 5년
  certification: 생활스포츠지도사 2급, 줌바 인증강사
  rating: 4.6
  profilePicture: trainer_02.jpg

트레이너 3:
  trainerId: trainer-003
  name: 박도현
  specialty: 웨이트, 체형교정
  career: 경력 10년
  certification: 건강운동관리사, NSCA-CPT
  rating: 4.9
  profilePicture: trainer_03.jpg

---

## 4. PT 횟수권 (PT 상품)

| 상품명    | 총 횟수 | 가격       | 담당 트레이너         |
|-----------|---------|------------|-----------------------|
| PT 10회권 | 10      | 350,000원  | 선택 (구매 시 결정)   |
| PT 20회권 | 20      | 650,000원  | 선택 (구매 시 결정)   |
| PT 30회권 | 30      | 900,000원  | 선택 (구매 시 결정)   |

remainingCount: totalCount와 동일하게 초기화
status: ACTIVE

---

## 5. 운동용품 (SportEquipment)

| 카테고리 | 상품명           | 가격     | 재고 |
|----------|------------------|----------|------|
| 헬스용품 | 요가 매트        | 35,000원 | 50   |
| 헬스용품 | 폼롤러           | 25,000원 | 30   |
| 의류     | 헬스 반팔 티셔츠 | 28,000원 | 100  |
| 의류     | 운동 레깅스      | 45,000원 | 80   |
| 보충제   | 단백질 보충제    | 55,000원 | 40   |
| 보충제   | BCAA             | 38,000원 | 40   |

---

## 6. 부가상품 (AdditionalProduct)

| 상품명        | 이용기간 | 가격      |
|---------------|----------|-----------|
| 락커 이용권   | 30일     | 10,000원  |
| 운동복 대여권 | 30일     | 15,000원  |

status: AVAILABLE (구매 후 ACTIVE로 변경)

---

## 7. 기구 (Equipment)

유산소 카테고리:
  equipmentId: eq-001
  name: 트레드밀
  description: 러닝 및 걷기 운동 기구. 속도와 경사 조절 가능.
  category: 유산소
  status: AVAILABLE

  equipmentId: eq-002
  name: 사이클
  description: 실내 자전거 운동 기구. 저충격 유산소 운동에 적합.
  category: 유산소
  status: AVAILABLE

근력 카테고리:
  equipmentId: eq-003
  name: 벤치프레스
  description: 가슴, 어깨, 삼두근 강화 기구. 바벨 또는 덤벨 사용 가능.
  category: 근력
  status: AVAILABLE

  equipmentId: eq-004
  name: 레그프레스
  description: 하체 전반(대퇴사두근, 햄스트링) 강화 기구.
  category: 근력
  status: AVAILABLE

스트레칭 카테고리:
  equipmentId: eq-005
  name: 폼롤러
  description: 근막 이완 및 유연성 향상을 위한 자가 마사지 도구.
  category: 스트레칭
  status: AVAILABLE

  equipmentId: eq-006
  name: 스트레칭존
  description: 매트와 스트레칭 보조 기구가 구비된 전용 공간.
  category: 스트레칭
  status: AVAILABLE

---

## 8. 운동방법 (ExerciseMethod)

트레드밀 (eq-001):
  methodId: em-001
  exerciseName: 인터벌 러닝
  targetBodyPart: 전신, 심폐기능
  difficulty: 중급
  preparationPose: 트레드밀 위에 올라 발을 어깨 너비로 벌린다.
  stepByStepMethod: 1. 속도 6km/h로 5분 워밍업 2. 속도 12km/h로 1분 달리기 3. 속도 7km/h로 2분 걷기 4. 2~3번 8회 반복 5. 속도 5km/h로 5분 쿨다운
  videoUrl: https://example.com/treadmill_interval.mp4
  image: treadmill_interval.jpg

벤치프레스 (eq-003):
  methodId: em-002
  exerciseName: 바벨 벤치프레스
  targetBodyPart: 대흉근, 삼각근 전면, 삼두근
  difficulty: 중급
  preparationPose: 벤치에 누워 등을 밀착시키고 발을 바닥에 고정한다.
  stepByStepMethod: 1. 바벨을 어깨 너비보다 약간 넓게 잡는다 2. 바벨을 가슴 중앙으로 천천히 내린다 3. 가슴에 살짝 닿으면 힘차게 밀어올린다 4. 팔꿈치를 완전히 펴지 않고 반복한다 5. 세트당 8~12회 수행
  videoUrl: https://example.com/bench_press.mp4
  image: bench_press.jpg

레그프레스 (eq-004):
  methodId: em-003
  exerciseName: 레그프레스
  targetBodyPart: 대퇴사두근, 햄스트링, 둔근
  difficulty: 초급
  preparationPose: 시트에 등을 기대고 발을 플레이트 중앙에 어깨 너비로 놓는다.
  stepByStepMethod: 1. 안전 잠금장치를 해제한다 2. 무릎을 90도로 구부려 플레이트를 내린다 3. 발뒤꿈치로 밀어 플레이트를 밀어올린다 4. 무릎을 완전히 펴지 않고 반복한다 5. 세트당 10~15회 수행
  videoUrl: https://example.com/leg_press.mp4
  image: leg_press.jpg

---

## 9. 공지사항 (Notice)

공지 1:
  noticeId: notice-001
  title: 헬스장 이용 안내 및 주의사항
  category: 공지
  writeDate: 2025-01-01
  content: 안녕하세요. FitCoach 헬스장을 이용해 주셔서 감사합니다. 기구 사용 후 반드시 제자리에 정리해 주시고, 타인을 배려하는 이용 문화를 만들어 주세요. 운동 중 부상 방지를 위해 워밍업을 충분히 진행해 주십시오.
  attachment: 없음
  readByMembers: []

공지 2:
  noticeId: notice-002
  title: 5월 봄맞이 회원권 할인 이벤트
  category: 이벤트
  writeDate: 2025-04-15
  content: 5월 한 달간 3개월권 이상 구매 시 10% 할인 혜택을 드립니다. 이벤트 기간: 2025년 5월 1일 ~ 5월 31일. 카운터 또는 앱에서 구매 가능합니다.
  attachment: event_may.jpg
  readByMembers: []

공지 3:
  noticeId: notice-003
  title: 정기 기구 점검 안내 (5월 20일)
  category: 점검
  writeDate: 2025-05-10
  content: 5월 20일(화) 오전 6시 ~ 오전 10시 정기 기구 점검이 진행됩니다. 해당 시간에는 일부 기구 이용이 제한될 수 있습니다. 양해 부탁드립니다.
  attachment: 없음
  readByMembers: []

---

## 10. 포인트 정책 (PointPolicy)

policyId: policy-001
basePoints: 10
exerciseTimeStandard: 30 (분 단위, 30분 이상 운동 시 보너스 적용)
timeBonusPoints: 5
consecutiveAttendanceDays: 7 (7일 연속 출석 달성 시 보너스 지급)
consecutiveAttendanceBonus: 50
