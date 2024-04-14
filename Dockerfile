FROM maven:3.8.4-openjdk-11-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn package -DskipTests

FROM openjdk:11-jre-slim AS deploy
COPY --from=build /app/target/*.war /app/app.war
EXPOSE 8443
CMD ["java", "-jar", "/app/app.war"]