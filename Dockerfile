FROM java:8
EXPOSE 9001
ADD /target/Timesheet-spring-boot-core-data-jpa-mvc-REST-1-0.0.1-SNAPSHOT.war timesheet-devops.war
ENTRYPOINT ["java","-jar","auth-service.jar"]