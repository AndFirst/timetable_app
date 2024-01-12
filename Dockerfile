FROM eclipse-temurin:17-jdk AS build
WORKDIR /workspace/app
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
COPY frontend frontend

RUN ./mvnw clean package -Pproduction -DskipTests

FROM eclipse-temurin:17-jre
COPY --from=build /workspace/app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
