FROM ghcr.io/graalvm/native-image-community:17 AS build
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY src ./src
RUN ./mvnw native:compile -Pnative -DskipTests -Dnative-image.xmx=6g

FROM ubuntu:jammy
WORKDIR /app
RUN apt-get update && apt-get install -y zlib1g && rm -rf /var/lib/apt/lists/*
COPY --from=build /app/target/my-orderfactory .
EXPOSE 9092
RUN chmod +x ./my-orderfactory
CMD ["./my-orderfactory"]