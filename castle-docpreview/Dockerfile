# 多阶段构建 - 第一阶段：从项目根目录构建
FROM maven:3.8.6-openjdk-11 AS builder  # 修改点 1: 将 `openjdk-8` 改为 `openjdk-11`

# 1. 设置工作目录并复制整个项目（关键步骤）
WORKDIR /workspace
COPY . .

# 2. 在项目根目录下，仅打包 castle-docpreview 模块及其所有依赖
# -pl 指定要构建的模块，-am 表示同时构建其依赖模块
RUN mvn clean package -pl castle-docpreview -am -DskipTests

# 第二阶段：运行
FROM eclipse-temurin:11-jre-alpine       # 修改点 2: 将 `8-jre-alpine` 改为 `11-jre-alpine`
WORKDIR /app
EXPOSE 8302

# 3. 从构建阶段复制目标模块的jar包
# 注意：jar包路径位于 castle-docpreview/target/ 下
COPY --from=builder /workspace/castle-docpreview/target/*.jar app.jar

# 使用你优化好的JVM启动参数
ENTRYPOINT ["java", "-Xmx128m", "-Xms128m", "-XX:+UseG1GC", "-jar", "app.jar"]
