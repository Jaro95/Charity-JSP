FROM openjdk:17-jdk

COPY target/charity.war .

EXPOSE 8080

ENTRYPOINT ["java","-war", "charity.war"]