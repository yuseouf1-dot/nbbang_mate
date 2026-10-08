# 1. Java 17 환경을 베이스 이미지로 사용 (가볍고 빠른 alpine 버전)
FROM eclipse-temurin:17-jdk-alpine

# 2. 컨테이너 내부의 작업 디렉토리 설정
WORKDIR /app

# 3. 호스트(내 컴퓨터)에서 빌드된 jar 파일을 컨테이너 내부로 복사
# 프로젝트 빌드 시 build/libs/ 폴더 안에 생성되는 jar 파일을 app.jar로 이름 변경하여 가져옴
COPY nbbang_matching/build/libs/*-SNAPSHOT.jar app.jar
# 4. 서버 포트 8082 개방
EXPOSE 8082

# 5. 컨테이너가 켜질 때 Spring Boot 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]