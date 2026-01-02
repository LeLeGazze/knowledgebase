# 多阶段构建 - 第一阶段：构建
FROM maven:3.8-openjdk-8 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# 第二阶段：运行
FROM openjdk:8-jre-slim
WORKDIR /app
EXPOSE 8302
COPY --from=builder /app/target/castle-docpreview.jar /app/docpreview.jar
ENTRYPOINT ["java", "-jar", "/app/docpreview.jar"]
