# 多阶段构建 - 第一阶段：构建 (使用稳定的Maven 3.8 + JDK 8镜像)
FROM maven:3.8.6-openjdk-8 AS builder
WORKDIR /build
# 复制项目文件
COPY pom.xml .
COPY src ./src
# 打包，跳过测试
RUN mvn clean package -DskipTests

# 第二阶段：运行 (使用稳定的Eclipse Temurin 8 JRE镜像)
FROM eclipse-temurin:8-jre-alpine
WORKDIR /app
EXPOSE 8302
# 从构建阶段复制生成的jar包
COPY --from=builder /build/target/*.jar app.jar
# 使用你优化好的JVM启动参数
ENTRYPOINT ["java", "-Xmx128m", "-Xms128m", "-XX:+UseG1GC", "-jar", "app.jar"]

