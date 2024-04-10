FROM eclipse-temurin
EXPOSE 8765
ADD /target/gateway.jar gateway.jar
ENTRYPOINT ["java", "-jar", "gateway.jar"]