# 多阶段构建 - 第一阶段：构建
FROM maven:3.8.6-openjdk-8 AS builder
WORKDIR /build

# 关键修正：从构建上下文（仓库根目录）的正确路径复制文件
# 1. 复制当前模块的 pom.xml
COPY castle-docpreview/pom.xml .
# 2. 复制当前模块的源代码
COPY castle-docpreview/src/main ./src/main
# 如果有测试代码
# COPY castle-docpreview/src/test ./src/test

# 打包，跳过测试
RUN mvn clean package -DskipTests

# 第二阶段：运行
FROM eclipse-temurin:8-jre-alpine
WORKDIR /app
EXPOSE 8302
# 从构建阶段复制生成的jar包
COPY --from=builder /build/target/*.jar app.jar
# 使用优化好的JVM启动参数
ENTRYPOINT ["java", "-Xmx128m", "-Xms128m", "-XX:+UseG1GC", "-jar", "app.jar"]
