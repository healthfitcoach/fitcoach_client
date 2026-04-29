# 헬스장 관리 시스템 — 고객용 SW

## 세션 시작 시 반드시 실행할 것

1. **클래스 명세 읽기 (필수)**
   - `class-spec.md` — 클래스명·속성(타입)·메서드(파라미터/반환타입)·관계의 **정확한 텍스트 명세**. 이것을 코딩 기준으로 삼는다.
   - `Docs/class-diagram-customer-1.png` / `Docs/class-diagram-customer-2.png`  — 다이어그램 이미지을 읽고 클래스 다이어그램에 명세된 그대로 관계를 설정하고 클래스를 작성한다.

2. **시나리오 읽기**
   - `docs/고객용 시나리오.pdf` (또는 `Docs/고객용 시나리오.pdf`)
   - UC별 Basic Path, Alternative Path, Exception 흐름을 전부 파악한 후 코딩한다.

3. **코딩 컨벤션 읽기**
   - `gym-java-conventions.md` 파일을 읽고 모든 규칙을 준수한다.
   - Google Java Style Guide 기준 (들여쓰기 2칸, UpperCamelCase 클래스, lowerCamelCase 메서드/변수 등).

4. **기타사항**
   - aggregation hierarchy 구조로 작성하며 메인클래스에 모든 시나리오를 작성하는 것이 아닌 컨트롤러와 같은 것을 통해 메인은 진입점 역할만 하고 실제 역할 분배와 작업은 하위 클래스로 한다.
   - 도메인에 따라 클래스를 구분해서 패키지를 나눈다.
---

## 절대 규칙

- 순수 Java만 — Spring / Maven / Gradle / 프레임워크 금지
- 웹 프로그램 금지 — HTTP, 서블릿, REST API 없음
- 텍스트 기반 — Scanner 입력, System.out.println() 출력만 사용
- Main은 `src/Main.java` 하나만 — UC 순서대로 순차 실행
- UC 하나 구현 → 수동 테스트 → 다음 UC 순서로 진행

---

## UC 구현 순서 (20개)

1. UC01 회원가입을 한다
2. UC02 구매한다
3. UC03 회원권을 구매한다
4. UC04 헬스 프로그램을 구매한다
5. UC05 운동용품을 구매한다
6. UC06 PT를 구매한다
7. UC07 결제한다
8. UC08 헬스장에 출석한다
9. UC09 운동기록을 기록한다
10. UC10 포인트를 지급받는다
11. UC11 기구를 검색한다
12. UC12 운동방법을 조회한다
13. UC13 공지사항을 확인한다
14. UC14 내정보를 관리한다
15. UC15 내정보를 수정한다
16. UC16 회원권을 관리한다
17. UC17 잔여기간을 조회한다
18. UC18 부가적인 상품을 관리한다
19. UC19 포인트를 조회한다
20. UC20 PT 일정을 잡는다
