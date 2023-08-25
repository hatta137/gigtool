# Build Image
FROM maven:3.8.3-openjdk-17-slim as build
WORKDIR /workspace/app

# Copy POM
COPY pom.xml pom.xml
COPY ./storage/pom.xml ./storage/pom.xml
COPY ./api/pom.xml ./api/pom.xml

# MVN get Dependencies
#RUN mvn dependency:go-offline

# Copy Source
COPY storage/src storage/src
COPY api/src api/src

# Build Jar
RUN mvn clean install -DskipTests
#RUN mvn dependency:go-offline

# Final Image
FROM amazoncorretto:17
ARG DEPENDENCY=/workspace/app

# Copy build results
WORKDIR /app
#COPY --from=build ${DEPENDENCY}/lib ./lib
COPY --from=build ${DEPENDENCY}/api/target/GigTool-Api.jar .

# Expose Server Port
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "GigTool-Api.jar"]

