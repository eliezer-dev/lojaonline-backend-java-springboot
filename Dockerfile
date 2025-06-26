FROM openjdk:21
WORKDIR /app

#ENV PGDATABASE=lojaonline PGHOST=localhost PGUSER=postgres PGPASSWORD=postgres PGPORT=5432 SPRING_PROFILES_ACTIVE=dev

COPY ./target/lojaonline-0.0.1-dev.jar /app
EXPOSE 8080
CMD ["java", "-jar", "lojaonline-0.0.1-dev.jar"]