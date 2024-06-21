FROM openjdk:21-slim
WORKDIR /app
LABEL maintainer="hendrysurijanto@gmail.com"
LABEL company="hahahah"
COPY ./target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]