# system_design.md - 클래스 설계 명세

이 파일은 클래스 뼈대(Skeleton)를 작성할 때만 참조한다.
구현 로직이 아닌 구조와 관계 정의에만 집중한다.

---

## 1. 클래스 상세 명세 (19개)

[1] Member
속성: memberId, loginId, password, name, nickname, phoneNumber, birthDate, physicalInfo, address, profilePicture, joinDate
메서드: signUp(), login(), checkDuplicateId(), updateInfo(), getInfo(), withdraw()

[2] Product (슈퍼 클래스)
속성: productId, productName, price, description, type
메서드: purchase(), getDetail(), search()

[3] Membership (extends Product)
속성: membershipId, memberId, type, status, startDate, endDate, pauseDate, resumeDate
메서드: purchase(), getDetail(), getUsageHistory(), checkRemainingPeriod(), requestRenewal(), refund()

[4] AdditionalProduct (extends Product)
속성: additionalProductId, memberId, name, status, usagePeriod
메서드: purchase(), getDetail(), search()

[5] ExerciseProgram (extends Product)
속성: programId, name, price, instructorId, status, startDate, endDate, capacity, remainingCapacity
메서드: purchase(), getDetail(), search(), cancel()

[6] SportEquipment (extends Product)
속성: price, stock, category
메서드: search(), purchase(), getDetail()

[7] PT (extends Product)
속성: ptId, memberId, trainerId, totalCount, remainingCount, status
메서드: purchase(), checkStatus(), search(), cancel()

[8] Trainer
속성: trainerId, name, career, certification, specialty, rating, profilePicture
메서드: search(), listAll(), checkSchedule(), searchDetail()

[9] PTSchedule
속성: scheduleId, ptId, memberId, trainerId, date, time, status
메서드: reserve(), search(), cancel()

[10] Order
속성: orderId, memberId, productId, quantity, totalAmount, shippingAddress, status, orderDateTime
메서드: createOrder(), search()

[11] Payment
속성: paymentId, memberId, productId, productType, paymentMethod, amount, usedPoints, status, paymentDateTime, pgAuthNumber
메서드: processPayment(), cancelPayment(), applyPoints(), search()

[12] Point
속성: pointId, memberId, balance, expiryDate
메서드: earnPoints(), usePoints(), search()

[13] PointPolicy
속성: policyId, basePoints, timeBonusPoints, exerciseTimeStandard, consecutiveAttendanceDays, consecutiveAttendanceBonus
메서드: calculatePoints(), checkConsecutiveAttendanceBonus(), search()

[14] PointHistory
속성: historyId, memberId, type, amount, reason, date, balanceAfter
메서드: listByPeriod(), getDetail(), search()

[15] Attendance
속성: attendanceId, memberId, attendanceDateTime, authMethod
메서드: checkAttendance(), search()

[16] ExerciseRecord
속성: recordId, memberId, exerciseDate, exerciseType, exerciseTime, sets, reps, memo, photo
메서드: recordExercise(), search(), receivePoints()

[17] Notice
속성: noticeId, title, content, category, writeDate, attachment, readByMembers
메서드: listAll(), getDetail(), markAsRead()
구현 주의: readByMembers는 List<String> 타입. markAsRead() 호출 시 현재 로그인 memberId를 추가한다.

[18] Equipment
속성: equipmentId, name, description, category, status
메서드: search(), listAll(), getDetail()
구현 주의: Member와 독립적인 클래스. 비로그인 상태에서도 조회 가능하다.

[19] ExerciseMethod
속성: methodId, equipmentId, exerciseName, targetBodyPart, difficulty, preparationPose, stepByStepMethod, image, videoUrl
메서드: search(), getDetail(), searchList()

---

## 2. 구조적 관계

Generalization (상속):
  Product 를 부모로 하며 아래 클래스들이 상속받는다.
  PT                extends Product
  Membership        extends Product
  AdditionalProduct extends Product
  ExerciseProgram   extends Product
  SportEquipment    extends Product

Aggregation (집합 - 생명주기 공유):
  Member    <- Attendance
  Order     <- Payment
  PT        <- PTSchedule
  Point     <- PointHistory
  Attendance <- ExerciseRecord
  Equipment  <- ExerciseMethod

Association (연관):
  Member        <- Order
  Member        <- Product
  Order         <- Product
  Trainer       <- PT
  Payment       <- Point
  ExerciseRecord <- Point
  Point         <- PointPolicy
  Member        <- Notice

Independence (독립):
  Equipment 는 Member 와 독립적인 클래스이다.
  비로그인 상태에서도 Equipment 및 ExerciseMethod 조회가 가능하다.
