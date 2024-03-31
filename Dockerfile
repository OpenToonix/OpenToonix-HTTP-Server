FROM maven:3.8.4-openjdk-11-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn package -DskipTests

FROM tomcat:9.0.54-jdk11-openjdk-slim AS deploy
# Changing the name also changges the context path, so the application will be available at /api
COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/api.war
EXPOSE 8080
CMD ["catalina.sh", "run"]