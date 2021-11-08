FROM tomcat:8.5-alpine
VOLUME /tmp
RUN rm -rf /usr/local/tomcat/webapps/*
COPY /target/Timesheet-spring-boot-core-data-jpa-mvc-REST-1-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war
CMD ["catalina.sh","run"]
