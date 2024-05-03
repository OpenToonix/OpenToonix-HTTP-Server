FROM maven:3-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn package -DskipTests

FROM amazoncorretto:17-alpine AS production
ENV PORT 8080
VOLUME /tmp
WORKDIR /app
COPY --from=build /app/target/*.jar ./app.jar
EXPOSE $PORT
CMD ["java", "-jar", "app.jar"]