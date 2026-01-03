# 多阶段构建 - 第一阶段：构建
FROM maven:3.8.7-openjdk-8 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# 第二阶段：运行
FROM eclipse-temurin:8-jre-alpine
WORKDIR /app
EXPOSE 8302
COPY --from=builder /app/target/castle-docpreview.jar /app/docpreview.jar

# 使用你已优化好的JVM启动参数
ENTRYPOINT ["java", "-Xmx128m", "-Xms128m", "-XX:+UseG1GC", "-jar", "/app/docpreview.jar"]

