# 构建阶段
FROM gradle:8.13-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle bootJar --no-daemon

# 生产阶段
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

# 复制所有环境配置文件到容器
COPY src/main/resources/application*.yml /app/config/

# 通过环境变量动态指定 Profile
ENV SPRING_PROFILES_ACTIVE=prod

# 在 Dockerfile 中指定调试参数（推荐）
ENV JAVA_TOOL_OPTIONS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"

# 使用 Shell 形式解析变量，调整配置优先级
CMD ["sh", "-c", "java -jar app.jar --spring.config.location=file:/app/config/,classpath:/ --spring.profiles.active=${SPRING_PROFILES_ACTIVE}"]

RUN apt-get update && \
    apt-get install -y iputils-ping dnsutils mysql-client && \
    rm -rf /var/lib/apt/lists/*