# 多阶段构建 - 第一阶段：构建
FROM maven:3.8.6-openjdk-8 AS builder
WORKDIR /build
# 1. 复制 pom.xml
COPY pom.xml .
# 2. 复制源码目录（根据你的实际结构修正：复制 src/main，而非 src）
COPY src/main ./src/main
# 如果有测试代码目录也可一并复制，但非必需
# COPY src/test ./src/test
# 打包，跳过测试
RUN mvn clean package -DskipTests

# 第二阶段：运行
FROM eclipse-temurin:8-jre-alpine
WORKDIR /app
EXPOSE 8302
# 从构建阶段复制生成的jar包
COPY --from=builder /build/target/*.jar app.jar
# 使用你优化好的JVM启动参数
ENTRYPOINT ["java", "-Xmx128m", "-Xms128m", "-XX:+UseG1GC", "-jar", "app.jar"]
