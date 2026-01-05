# 多阶段构建
FROM maven:3.8.4-openjdk-11 AS build
WORKDIR /workspace

# 复制pom文件
COPY pom.xml .
# 下载依赖
RUN mvn dependency:go-offline -B

# 复制源码 - 复制src/main目录
COPY src/main ./src/main

# 构建应用
RUN mvn clean package -DskipTests

# 运行阶段
FROM eclipse-temurin:11-jre-alpine
WORKDIR /app

# 复制构建的jar文件
COPY --from=build /workspace/target/*.jar app.jar

# 暴露端口
EXPOSE 8200

# 启动应用
ENTRYPOINT ["java", "-Xmx256m", "-Xms256m", "-XX:+UseG1GC", "-jar", "app.jar", "--spring.profiles.active=prod"]
