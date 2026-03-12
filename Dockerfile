FROM eclipse-temurin:17-jdk-alpine AS build
COPY . .
RUN ./gradlew build -x test

FROM eclipse-temurin:17-jre-alpine
COPY --from=build /build/libs/diplomado-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]