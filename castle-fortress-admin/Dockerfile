# 多阶段构建 - 第一阶段：构建
FROM maven:3.8.7-openjdk-8 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# 第二阶段：运行
FROM eclipse-temurin:8-jre-alpine  # 【主要修改在这里】
WORKDIR /app
EXPOSE 9081
COPY --from=builder /app/target/castle-fortress-admin.jar /app/admin.jar

# 注意：主服务的启动命令包含了 profile 参数
ENTRYPOINT ["java", "-Xmx256m", "-Xms256m", "-XX:+UseG1GC", "-jar", "/app/admin.jar", "--spring.profiles.active=prod"]
