FROM openjdk:17
EXPOSE 8080
ADD build/libs/CrudServiceUCX-0.0.1-SNAPSHOT.jar CrudServiceUCX-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/CrudServiceUCX-0.0.1-SNAPSHOT.jar"]