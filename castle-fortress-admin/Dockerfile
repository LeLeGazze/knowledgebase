# ... 其他部分保持不变
FROM maven:3.8.6-openjdk-8 AS builder
WORKDIR /build
# 关键修正：复制正确的源码路径
COPY pom.xml .
COPY src/main ./src/main
# COPY src/test ./src/test
RUN mvn clean package -DskipTests
# ... 后续保持不变

# 第二阶段：运行
FROM eclipse-temurin:8-jre-alpine  # 【主要修改在这里】
WORKDIR /app
EXPOSE 9081
COPY --from=builder /app/target/castle-fortress-admin.jar /app/admin.jar

# 注意：主服务的启动命令包含了 profile 参数
ENTRYPOINT ["java", "-Xmx256m", "-Xms256m", "-XX:+UseG1GC", "-jar", "/app/admin.jar", "--spring.profiles.active=prod"]
