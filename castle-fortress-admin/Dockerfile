# 多阶段构建 - 第一阶段：构建
FROM maven:3.8-openjdk-11 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# 第二阶段：运行
FROM eclipse-temurin:11-jre-alpine
WORKDIR /app
EXPOSE 8200
COPY --from=builder /app/target/castle-fortress-admin.jar /app/admin.jar
ENTRYPOINT ["java", "-Xmx256m", "-Xms256m", "-XX:+UseG1GC", "-jar", "/app/admin.jar", "--spring.profiles.active=prod"]
