# 일정 관리 앱 만들기

## 프로젝트 소개
3 Layer Architecture를 적용해 확장 가능하고 유지보수가 용이한 구조 설계

---

## 사용 기술
- 언어: JAVA
- 개발환경: IntelliJ
- 프레임워크: Spring Boot
    - Spring Web
    - Spring Data JPA
    - Validation
- 데이터베이스: MySQL
- 추가 라이브러리: Lombok

---

## API
### **users Table**
| Column | Type | Constraints | Description |
| :--- | :--- | :--- | :--- |
| **id** (PK) | BigInt | Auto Increment | 유저 식별자 |
| **username** | Varchar(4) | Not Null | 유저 이름 (최대 4자) |
| **email** | Varchar(255) | Not Null, Unique | 로그인용 이메일 |
| **password** | Varchar(255) | Not Null | 암호화된 비밀번호 (BCrypt) |
| **created_at**| DateTime | Not Null | 생성일 |
| **modified_at**| DateTime | Not Null | 수정일 |

### **schedules Table**
| Column | Type | Constraints | Description |
| :--- | :--- | :--- | :--- |
| **id** (PK) | BigInt | Auto Increment | 일정 식별자 |
| **user_id** (FK)| BigInt | Not Null | 작성자 연관관계 (User 테이블 참조) |
| **title** | Varchar(10) | Not Null | 일정 제목 (최대 10자) |
| **content** | Text | Not Null | 일정 내용 |
| **created_at**| DateTime | Not Null | 생성일 |
| **modified_at**| DateTime | Not Null | 수정일 |

---

##  2. API 명세서

### **[User 도메인]**
| 기능 | Method | URL | Request Body | Response | 비고 |
| :--- | :--- | :--- | :--- | :--- | :--- |
| 회원가입 | `POST` | `/users` | `username`, `email`, `password` | 201 Created | 비밀번호 8자 이상 필수 |
| 로그인 | `POST` | `/login` | `email`, `password` | 200 OK | 세션 생성 (`USER_ID`) |
| 로그아웃 | `POST` | `/logout` | - | 200 OK | 세션 무효화 |
| 유저 조회 | `GET` | `/users/{id}` | - | 200 OK | 단건 조회 |
| 유저 수정 | `PUT` | `/users/{id}` | `username`, `email` | 200 OK | - |
| 유저 삭제 | `DELETE` | `/users/{id}` | - | 204 No Content | - |

### **[Schedule 도메인]**
| 기능 | Method | URL | Request Body | Response | 비고 |
| :--- | :--- | :--- | :--- | :--- | :--- |
| 일정 생성 | `POST` | `/schedules` | `userId`, `title`, `content` | 201 Created | 제목 10자 이내 필수 |
| 전체 조회 | `GET` | `/schedules` | - | 200 OK | 수정일 기준 내림차순 정렬 |
| 단건 조회 | `GET` | `/schedules/{id}` | - | 200 OK | - |
| 일정 수정 | `PATCH` | `/schedules/{id}` | `title`, `content` | 200 OK | 부분 수정 가능 |
| 일정 삭제 | `DELETE` | `/schedules/{id}` | - | 204 No Content | - |