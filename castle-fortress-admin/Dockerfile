# ... 其他部分保持不变
FROM maven:3.8.6-openjdk-8 AS builder
WORKDIR /build
# 关键修正：路径前缀改为 castle-fortress-admin
COPY castle-fortress-admin/pom.xml .
COPY castle-fortress-admin/src/main ./src/main
# COPY castle-fortress-admin/src/test ./src/test
# ... 第一阶段相同 ...
RUN mvn clean package -pl castle-fortress-admin -am -DskipTests
# ... 后续相同，注意JAR包路径和端口 ...
# ... 后续保持不变

# 第二阶段：运行
FROM eclipse-temurin:8-jre-alpine  # 【主要修改在这里】
WORKDIR /app
EXPOSE 9081
COPY --from=builder /app/target/castle-fortress-admin.jar /app/admin.jar

# 注意：主服务的启动命令包含了 profile 参数
ENTRYPOINT ["java", "-Xmx256m", "-Xms256m", "-XX:+UseG1GC", "-jar", "/app/admin.jar", "--spring.profiles.active=prod"]
