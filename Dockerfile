FROM openjdk:11.0.2-oracle

WORKDIR /api/build/libs/

ADD /api/build/libs/api.jar .

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=prod","-jar","api.jar"]