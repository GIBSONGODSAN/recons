FROM openjdk:21
ADD target/gibson.jar /gibson.jar
CMD ["java", "-jar", "/gibson.jar"]