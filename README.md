# my-books

하루 개발 하고 작업 일지 노션에 기록
왜 이런 선택을 했는지 정리해야함
화면은 간단하게

## 기능 정리
 - 인증
   - 로그인/로그아웃
     - 세션 인증 방식 구현 -- ok
     
   - 로그인 사용자 확인을 위해 Controller에 적용할 AOP 추가 
 
 - 도서 관리
   1. 내가 가지고 있는 책 등록
      1. 책 등록을 위해 kakao 도서 api 연동 -- ok
   2. 리뷰 작성
   3. 리뷰 조회
   4. 내 책 검색
   5. 내 책 리스트 조회 (최신순)

 - 중고 책 거래
   1. 1:1 대화 기능

 - 화면
   - 카카오 오븐 사용해서 UI만 그리기

## 배포 + 모니터링
 - 배포
   - EC2 + RDS + 
   - Docker? 쿠버네티스?
 
 - 모니터링
   - 프로메테우스 + 그라파나
   - 핀포인트
   - Logging: ELK stack


