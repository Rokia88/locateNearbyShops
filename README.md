# locateNearbyShops
this application displays shops near the current location of the user. It is developped using Java, SpringBoot, MongoDb, VueJs  technologies

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

you need to install MongoDb, Eclipse, Maven plugin (m2e), Standalone tomcat if you do not want to use the embedded tomcat. you have to restore the database "shops" that contains a set of 300 shops from the dump file given in the assignment.

### Installing

Read the documentation.pdf file for installation instructions.


## Running the tests

### Unit tests

Run the Junit class  src/test/java/SuitesTests.java or run maven test command in eclipse

### Integration tests

Run the integration tests manually by following the test cases described in IntegrationTests.pdf

## Deployment

Using embedded tomcat, you only need to start the database server on 27017 port and run the comand java -jar locateNearbyShops.war.
Using standalone tomcat, you need to modify the configuration in server.xml and web.xml files (see documentation.pdf) and start both the database server and the tomcat server.

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Contributing

## Versioning

we use GitHub eclipse plugin

## Authors

* **Rokia Lamrani Alaoui** - (https://github.com/Rokia88)

## License

This project is licensed under the MIT License

