# CLAUDE.md - Master Rules

이 파일은 Claude Code가 코드를 작성할 때 항상 참조하는 마스터 규칙이다.
모든 규칙은 예외 없이 전체 코드베이스에 적용된다.

---

## 1. 기술 원칙

언어: Pure Java SE 17+
외부 라이브러리, Spring, GUI, DB, Web 사용 금지
인터페이스: Text-Based Console (Scanner 입력, System.out.println 출력)
데이터 관리: DB 없이 List, Map 등 Java Collection을 활용한 In-Memory 방식

---

## 2. 적시 초기화 원칙 (Just-In-Time Init)

규칙 1: 생성자에서는 필드 할당만 수행한다. 로직을 절대 포함하지 않는다.
규칙 2: 실제 로직(데이터 로드, 객체 연결, 외부 연동 시뮬레이션)은 반드시 public boolean init() 메서드에서 처리한다.
규칙 3: init()은 해당 객체의 기능이 실행되기 직전에 호출한다.
규칙 4: init() 호출 후 반드시 성공 여부를 검증한다. 실패 시 오류 메시지를 출력하고 즉시 흐름을 종료한다.

적용 패턴:
    SomeService service = new SomeService();
    if (!service.init()) {
        System.out.println("서비스 초기화에 실패했습니다. 잠시 후 다시 시도해주세요.");
        return;
    }
    service.doSomething();

---

## 3. 콘솔 출력 형식

모든 유스케이스의 각 단계는 아래 형식으로 출력한다.

    Step 1: 회원가입 입력 폼을 출력합니다.
    Step 2: 아이디 중복 여부를 확인합니다.

사용자 입력 요청 시 프롬프트:
    >

오류 및 안내 메시지는 유스케이스 시나리오(docs/usecases/) 원문 텍스트를 그대로 출력한다.

---

## 4. 파일 참조 가이드

클래스 뼈대 및 구조 설계 시: docs/system_design.md 참조
초기 데이터 세팅 시: docs/initial_data.md 참조
유스케이스 시나리오 구현 시: docs/usecases/UC01_SignUp.md ~ UC20_SchedulePT.md 참조

---

## 5. 메인 루프 구조

비로그인 상태 노출 메뉴: 회원가입, 로그인, 기구검색 (Equipment는 Member와 독립)
로그인 상태 노출 메뉴: 전체 20개 유스케이스
종료 조건: 0 또는 exit 입력 시 프로그램 종료

---

## 6. 예외 처리 원칙

Alternative Path와 Exception은 docs/usecases/ 원문 메시지와 동일하게 출력한다.
숫자 입력 오류(NumberFormatException)는 try-catch로 처리하고 재입력을 유도한다.
시스템 오류 시뮬레이션은 Random 또는 플래그 변수를 활용한다.
