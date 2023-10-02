FROM maven:3.9.4-eclipse-temurin-8-alpine AS builder
WORKDIR /build
COPY . .
RUN mvn clean package -DskipTests

FROM tomcat:10.1.13-jre11-temurin-jammy
COPY --from=builder /build/target/url-shortener-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war
CMD ["catalina.sh", "run"]