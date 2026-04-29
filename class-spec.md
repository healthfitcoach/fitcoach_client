# 고객용 SW — 클래스 명세서

> **이미지(class-diagram-customer-1.png, class-diagram-customer-2.png)와 함께 반드시 참조할 것.**
> 속성은 모두 `private`, 메서드는 모두 `public`.

---

## 클래스 관계

### 상속 (Generalization)
```
Product (상품)
  ├── Membership (회원권)
  ├── AdditionalProduct (부가상품)
  ├── ExerciseProgram (운동프로그램)
  ├── SportEquipment (운동용품)
  └── PT
```

### 집합 (Aggregation)
| 전체 | 부분 |
|------|------|
| Member (회원) | Attendance (출석) |
| Order (주문) | Payment (결제) |
| PT | PTSchedule (PT일정) |
| Point (포인트) | PointHistory (포인트내역) |

### 연관 (Association)
| 클래스A | 클래스B |
|---------|---------|
| Member | Order |
| Point | Payment |
| ExerciseRecord | Point |
| Attendance | ExerciseRecord |
| Point | PointPolicy |
| PT | Trainer |

### 독립 클래스
- Equipment (기구)
- Notice (공지사항)

---

## 클래스 상세 명세

---

### 1. Member (회원)

**영문명**: `Member`

#### 속성 (private)
| 속성명 | 한글명 | 타입 |
|--------|--------|------|
| memberId | 회원ID | String |
| name | 이름 | String |
| nickname | 닉네임 | String |
| loginId | 아이디 | String |
| password | 비밀번호 | String |
| phone | 전화번호 | String |
| birthDate | 생년월일 | Date |
| bodyInfo | 신체정보 | String |
| address | 주소 | String |
| profileImage | 프로필사진 | String |
| joinDate | 가입일 | Date |

#### 메서드 (public)
| 메서드명 | 한글명 | 파라미터 | 반환타입 |
|----------|--------|----------|----------|
| register | 회원가입 | name:String, nickname:String, loginId:String, password:String, phone:String, birthDate:Date, bodyInfo:String, address:String, profileImage:String | Member |
| login | 로그인 | loginId:String, password:String | Member |
| checkLoginIdDuplicate | 아이디중복확인 | loginId:String | boolean |
| getInfo | 정보조회 | - | Member |
| updateInfo | 정보수정 | nickname:String, phone:String, password:String, profileImage:String, bodyInfo:String, address:String | void |
| withdraw | 회원탈퇴 | - | void |

---

### 2. Product (상품) — 슈퍼클래스

**영문명**: `Product`

#### 속성 (private)
| 속성명 | 한글명 | 타입 |
|--------|--------|------|
| productId | 상품ID | String |
| productName | 상품명 | String |
| price | 가격 | int |
| description | 설명 | String |
| type | 타입 | String |

#### 메서드 (public)
| 메서드명 | 한글명 | 파라미터 | 반환타입 |
|----------|--------|----------|----------|
| purchase | 구매 | - | void |
| getDetail | 상세조회 | - | Product |
| get | 조회 | - | Product |

---

### 3. Membership (회원권) — extends Product

**영문명**: `Membership`

#### 속성 (private)
| 속성명 | 한글명 | 타입 |
|--------|--------|------|
| membershipId | 회원권ID | String |
| memberId | 회원ID | String |
| type | 타입 | String |
| startDate | 시작일 | Date |
| endDate | 종료일 | Date |
| price | 가격 | int |
| status | 상태 | String |
| pauseDate | 일시정지일 | Date |
| resumeDate | 재개예정일 | Date |

#### 메서드 (public)
| 메서드명 | 한글명 | 파라미터 | 반환타입 |
|----------|--------|----------|----------|
| get | 조회 | - | Membership |
| getRemainingDays | 잔여기간조회 | - | int |
| applyRenewal | 갱신신청 | period:int | void |
| getUsageHistory | 이용내역조회 | - | Attendance |
| refund | 환불 | - | void |

---

### 4. AdditionalProduct (부가상품) — extends Product

**영문명**: `AdditionalProduct`

#### 속성 (private)
| 속성명 | 한글명 | 타입 |
|--------|--------|------|
| additionalProductId | 부가상품ID | String |
| memberId | 회원ID | String |
| name | 이름 | String |
| price | 가격 | int |
| usagePeriod | 이용기간 | int |
| status | 상태 | String |

#### 메서드 (public)
| 메서드명 | 한글명 | 파라미터 | 반환타입 |
|----------|--------|----------|----------|
| purchase | 구매 | - | void |
| getDetail | 상세조회 | - | AdditionalProduct |
| get | 조회 | - | AdditionalProduct |

---

### 5. ExerciseProgram (운동프로그램) — extends Product

**영문명**: `ExerciseProgram`

#### 속성 (private)
| 속성명 | 한글명 | 타입 |
|--------|--------|------|
| programId | 프로그램ID | String |
| name | 이름 | String |
| description | 설명 | String |
| instructorId | 강사ID | String |
| price | 가격 | int |
| capacity | 정원 | int |
| remainingCapacity | 잔여정원 | int |
| startDate | 시작일 | Date |
| endDate | 종료일 | Date |
| status | 상태 | String |

#### 메서드 (public)
| 메서드명 | 한글명 | 파라미터 | 반환타입 |
|----------|--------|----------|----------|
| getList | 목록조회 | - | ExerciseProgram |
| get | 조회 | - | ExerciseProgram |
| purchase | 구매 | programId:String | void |
| cancel | 취소 | - | void |

---

### 6. SportEquipment (운동용품) — extends Product

**영문명**: `SportEquipment`

#### 속성 (private)
| 속성명 | 한글명 | 타입 |
|--------|--------|------|
| productId | 상품ID | String |
| name | 이름 | String |
| category | 카테고리 | String |
| price | 가격 | int |
| stock | 재고 | int |
| description | 설명 | String |

#### 메서드 (public)
| 메서드명 | 한글명 | 파라미터 | 반환타입 |
|----------|--------|----------|----------|
| getList | 목록조회 | category:String | SportEquipment |
| search | 검색 | keyword:String | SportEquipment |
| get | 조회 | - | SportEquipment |

---

### 7. PT — extends Product

**영문명**: `PT`

#### 속성 (private)
| 속성명 | 한글명 | 타입 |
|--------|--------|------|
| ptId | PTID | String |
| memberId | 회원ID | String |
| trainerId | 트레이너ID | String |
| count | 횟수 | int |
| remainingCount | 잔여횟수 | int |
| price | 가격 | int |
| status | 상태 | String |

#### 메서드 (public)
| 메서드명 | 한글명 | 파라미터 | 반환타입 |
|----------|--------|----------|----------|
| get | 조회 | - | PT |
| purchase | 구매 | trainerId:String, count:int | PT |
| cancel | 취소 | - | void |
| getStatus | 상태조회 | - | PT |

---

### 8. Trainer (트레이너)

**영문명**: `Trainer`

#### 속성 (private)
| 속성명 | 한글명 | 타입 |
|--------|--------|------|
| trainerId | 트레이너ID | String |
| name | 이름 | String |
| specialty | 전문분야 | String |
| career | 경력 | String |
| certificate | 자격증 | String |
| rating | 평점 | float |
| profileImage | 프로필사진 | String |

#### 메서드 (public)
| 메서드명 | 한글명 | 파라미터 | 반환타입 |
|----------|--------|----------|----------|
| getList | 목록조회 | - | Trainer |
| get | 조회 | trainerId:String | Trainer |
| search | 검색 | specialty:String, rating:float | Trainer |
| getSchedule | 스케줄조회 | trainerId:String | PTSchedule |

---

### 9. PTSchedule (PT일정)

**영문명**: `PTSchedule`

#### 속성 (private)
| 속성명 | 한글명 | 타입 |
|--------|--------|------|
| scheduleId | 일정ID | String |
| ptId | PTID | String |
| trainerId | 트레이너ID | String |
| memberId | 회원ID | String |
| date | 날짜 | Date |
| time | 시간 | String |
| status | 상태 | String |

#### 메서드 (public)
| 메서드명 | 한글명 | 파라미터 | 반환타입 |
|----------|--------|----------|----------|
| get | 조회 | - | PTSchedule |
| reserve | 예약 | date:Date, time:String | PTSchedule |
| cancel | 취소 | - | void |

---

### 10. Order (주문)

**영문명**: `Order`

#### 속성 (private)
| 속성명 | 한글명 | 타입 |
|--------|--------|------|
| orderId | 주문ID | String |
| memberId | 회원ID | String |
| productId | 상품ID | String |
| quantity | 수량 | int |
| deliveryAddress | 배송지 | String |
| totalAmount | 총금액 | int |
| orderDateTime | 주문일시 | String |
| status | 상태 | String |

#### 메서드 (public)
| 메서드명 | 한글명 | 파라미터 | 반환타입 |
|----------|--------|----------|----------|
| create | 주문생성 | productId:String, quantity:int, deliveryAddress:String | Order |
| get | 조회 | - | Order |

---

### 11. Payment (결제)

**영문명**: `Payment`

#### 속성 (private)
| 속성명 | 한글명 | 타입 |
|--------|--------|------|
| paymentId | 결제ID | String |
| memberId | 회원ID | String |
| productType | 상품종류 | String |
| productId | 상품ID | String |
| amount | 금액 | int |
| usedPoint | 사용포인트 | int |
| paymentMethod | 결제수단 | String |
| paymentDateTime | 결제일시 | String |
| status | 상태 | String |
| pgApprovalNumber | PG승인번호 | String |

#### 메서드 (public)
| 메서드명 | 한글명 | 파라미터 | 반환타입 |
|----------|--------|----------|----------|
| pay | 결제하기 | amount:int, paymentMethod:String, usedPoint:int | Payment |
| applyPoint | 포인트적용 | point:int | int |
| cancelPayment | 결제취소 | - | void |
| get | 조회 | - | Payment |

---

### 12. Point (포인트)

**영문명**: `Point`

#### 속성 (private)
| 속성명 | 한글명 | 타입 |
|--------|--------|------|
| pointId | 포인트ID | String |
| memberId | 회원ID | String |
| balance | 잔액 | int |
| expiryDate | 유효기간 | Date |

#### 메서드 (public)
| 메서드명 | 한글명 | 파라미터 | 반환타입 |
|----------|--------|----------|----------|
| get | 조회 | - | Point |
| accumulate | 적립 | amount:int | void |
| use | 사용 | amount:int | void |

---

### 13. PointPolicy (포인트정책)

**영문명**: `PointPolicy`

#### 속성 (private)
| 속성명 | 한글명 | 타입 |
|--------|--------|------|
| policyId | 정책ID | String |
| baseAccumulationPoint | 기본적립포인트 | int |
| exerciseTimeCriteria | 운동시간기준 | int |
| timeBonusPoint | 시간보너스포인트 | int |
| consecutiveAttendanceBonusPoint | 연속출석보너스포인트 | int |
| consecutiveAttendanceCriteriaDays | 연속출석기준일수 | int |

#### 메서드 (public)
| 메서드명 | 한글명 | 파라미터 | 반환타입 |
|----------|--------|----------|----------|
| get | 조회 | - | PointPolicy |
| calculatePoint | 포인트산정 | exerciseTime:int | int |
| checkConsecutiveBonus | 연속출석보너스확인 | consecutiveDays:int | int |

---

### 14. PointHistory (포인트내역)

**영문명**: `PointHistory`

#### 속성 (private)
| 속성명 | 한글명 | 타입 |
|--------|--------|------|
| historyId | 내역ID | String |
| memberId | 회원ID | String |
| date | 날짜 | String |
| type | 구분 | String |
| reason | 사유 | String |
| changeAmount | 변동량 | int |
| remainingPoint | 잔여포인트 | int |

#### 메서드 (public)
| 메서드명 | 한글명 | 파라미터 | 반환타입 |
|----------|--------|----------|----------|
| get | 조회 | - | PointHistory |
| getByPeriod | 기간별조회 | startDate:Date, endDate:Date | PointHistory |
| getDetail | 상세조회 | historyId:String | PointHistory |

---

### 15. Attendance (출석)

**영문명**: `Attendance`

#### 속성 (private)
| 속성명 | 한글명 | 타입 |
|--------|--------|------|
| attendanceId | 출석ID | String |
| memberId | 회원ID | String |
| attendanceDateTime | 출석일시 | String |
| authMethod | 인증방법 | String |

#### 메서드 (public)
| 메서드명 | 한글명 | 파라미터 | 반환타입 |
|----------|--------|----------|----------|
| checkIn | 출석체크 | memberId:String | Attendance |
| get | 조회 | - | Attendance |

---

### 16. ExerciseRecord (운동기록)

**영문명**: `ExerciseRecord`

#### 속성 (private)
| 속성명 | 한글명 | 타입 |
|--------|--------|------|
| recordId | 기록ID | String |
| memberId | 회원ID | String |
| exerciseDate | 운동일 | Date |
| exerciseType | 운동종류 | String |
| exerciseTime | 운동시간 | int |
| sets | 세트수 | int |
| reps | 반복횟수 | int |
| memo | 메모 | String |
| photo | 사진 | String |

#### 메서드 (public)
| 메서드명 | 한글명 | 파라미터 | 반환타입 |
|----------|--------|----------|----------|
| record | 기록하기 | exerciseDate:Date, exerciseType:String, exerciseTime:int, sets:int, reps:int, memo:String, photo:String | ExerciseRecord |
| get | 조회 | - | ExerciseRecord |
| receivePoint | 포인트지급받기 | - | void |

---

### 17. Notice (공지사항)

**영문명**: `Notice`

#### 속성 (private)
| 속성명 | 한글명 | 타입 |
|--------|--------|------|
| noticeId | 공지ID | String |
| title | 제목 | String |
| content | 내용 | String |
| category | 카테고리 | String |
| createdDate | 작성일 | Date |
| attachment | 첨부파일 | String |
| isRead | 읽음여부 | boolean |

#### 메서드 (public)
| 메서드명 | 한글명 | 파라미터 | 반환타입 |
|----------|--------|----------|----------|
| getList | 목록조회 | - | Notice |
| getDetail | 상세조회 | noticeId:String | Notice |
| markAsRead | 읽음처리 | noticeId:String | void |

---

### 18. Equipment (기구) — 독립 클래스

**영문명**: `Equipment`

#### 속성 (private)
| 속성명 | 한글명 | 타입 |
|--------|--------|------|
| equipmentId | 기구ID | String |
| name | 이름 | String |
| category | 카테고리 | String |
| image | 이미지 | String |
| description | 설명 | String |
| status | 상태 | String |

#### 메서드 (public)
| 메서드명 | 한글명 | 파라미터 | 반환타입 |
|----------|--------|----------|----------|
| getList | 목록조회 | category:String | Equipment |
| search | 검색 | keyword:String | Equipment |
| get | 조회 | - | Equipment |

---

### 19. ExerciseMethod (운동방법)

**영문명**: `ExerciseMethod`

#### 속성 (private)
| 속성명 | 한글명 | 타입 |
|--------|--------|------|
| methodId | 방법ID | String |
| equipmentId | 기구ID | String |
| exerciseName | 운동이름 | String |
| targetMuscle | 대상부위 | String |
| difficulty | 난이도 | String |
| startingPosition | 준비자세 | String |
| stepByStep | 단계별방법 | String |
| image | 이미지 | String |
| videoUrl | 영상URL | String |

#### 메서드 (public)
| 메서드명 | 한글명 | 파라미터 | 반환타입 |
|----------|--------|----------|----------|
| get | 조회 | equipmentId:String | ExerciseMethod |
| search | 검색 | keyword:String, muscle:String | ExerciseMethod |
| getDetail | 상세조회 | methodId:String | ExerciseMethod |
