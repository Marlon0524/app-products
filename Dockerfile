FROM openjdk:17
VOLUME /tmp
EXPOSE 8020
ADD ./target/products-0.0.1-SNAPSHOT.jar app-products.jar
ENTRYPOINT ["java", "-jar", "app-products.jar"]
