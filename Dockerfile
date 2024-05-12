FROM ubuntu:latest
RUN apt-get update 
RUN apt-get install -y openjdk-17-jdk maven
ENV APP_HOME=/usr/app
WORKDIR $APP_HOME/
COPY ./ $APP_HOME/
RUN mvn -B package -DfinalName=chatmanager -DskipTests --file pom.xml
EXPOSE 8082
CMD ["java", "-jar", "target/chatmanager.jar"]
