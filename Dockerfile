FROM openjdk:17-jdk-slim
#FROM maven:3.8.4-openjdk-17-slim
FROM maven:3.9.9-eclipse-temurin-17

WORKDIR /simple-blog-system/app

COPY . .

RUN mvn clean package -DskipTests
# RUN mvn install package -DskipTests

#ใส่ Path database ที่อยู่บน Host
# ENV SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/simple_blog_system_db
# ENV SPRING_DATASOURCE_USERNAME=postgres
# ENV SPRING_DATASOURCE_PASSWORD=257890
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://dpg-cu2jtgd6l47c73c46p70-a.singapore-postgres.render.com:5432/simple_blog_system_db
ENV SPRING_DATASOURCE_USERNAME=jey
ENV SPRING_DATASOURCE_PASSWORD=keBLbOl6IryS4ET3YVST3fkQScxV3v5m

EXPOSE 8080

ENTRYPOINT ["java","-jar","/simple-blog-system/app/target/simple-blog-system-0.0.1-SNAPSHOT.jar"]