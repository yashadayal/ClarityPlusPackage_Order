# Use the OpenJDK 17 as the base image
FROM openjdk:17

# Copy the Order Management Service JAR file to the container
COPY ./target/OrderMService-0.0.1-SNAPSHOT.jar ./

# Set the working directory to the root of the container
WORKDIR ./

# Expose port 9001 for the Order Management Service
EXPOSE 9001

# Set the entrypoint for the container to run the JAR file
ENTRYPOINT ["java", "-jar"]

# Set the default command to run the Order Management Service JAR file
CMD ["OrderMService-0.0.1-SNAPSHOT.jar"]
