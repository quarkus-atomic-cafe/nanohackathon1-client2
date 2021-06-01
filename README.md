# Sample Quarkus Application for the first NanoHackathon

This application connects to a Kafka topic called hackathon-topic and consumes messages via the reactive Kafka connector. The messages contain one field called token with a UID and are deserialized to SourceObject.java .  A name is read from an environment variable called ENV.  The token and the name are written into a Postgres database via the postgres reactive connector.  The goal was to achieve maximum throughput performance.

## How to run 
Adjust the settings in 'src/main/resources/application.properties' and replace all fields with brackets.  If you need to access a Kafka boostrap server with self-signed TLS-certificate you can drop a JKS keystore containing the certificate into 'src/main/resources/client.truststore.jks' and enable usage in the properties.  This will only be available for Dev mode.

When starting the app locally in Dev Mode, Quarkus will look for a Docker Daemon and if available launch a Postgres DB container (Quarkus DevService) and connect directly to it. So you will only need to wire up your Kafka connection.

Run the app locally in dev mode:
```shell script
./mvnw quarkus:dev
```

To build a native image and push it to quay.io execute this command
```shell script
./mvnw package -Pnative -Dquarkus.container-image.push=true -Dquarkus.container-image.username=<quay.io username> -Dquarkus.container-image.password=<quay.io password>
```

## General

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/nanohackathonemitter-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.html.

## Related guides

- RESTEasy JAX-RS ([guide](https://quarkus.io/guides/rest-json)): REST endpoint framework implementing JAX-RS and more

## Provided examples

### RESTEasy JAX-RS example

REST is easy peasy with this Hello World RESTEasy resource.

[Related guide section...](https://quarkus.io/guides/getting-started#the-jax-rs-resources)
