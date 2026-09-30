FROM eclipse-temurin:21-jdk-jammy

WORKDIR /workspace

COPY gradlew .
COPY gradle gradle
COPY settings.gradle.kts .
COPY build.gradle.kts .

RUN chmod +x gradlew
RUN ./gradlew dependencies

CMD ["./gradlew", "bootRun"]