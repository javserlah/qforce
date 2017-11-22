# Application design

The application has been developed in SpringBoot, using gradle as the building tool.

The application exposes two REST end points in the port that is defined inside the application.properties file.

Both endpoints will share the same root which is "/api"

The main entry point of the application is set through the "QlnhApplication" class.

The project is divided in different packages. Following is a description of the purpose of each one of them:

   - Configuration: Its main goal is to load the application.properties file into a configuration object that can be accessed easily from the rest of the application.

   - Controller: Its main goal is to  have all the controller classes that will be exposing all the endpoints of the application

   - Service: Its main goal is to have classes that will have application business logic. Here I included the call for logging the analytics in H2. Probably this
     could be better done with AOP as this seems like a crosscutting concern.   
     
   - Domain: Its main goal is to have all the classes that will have the business information.

   - Facade: Its main goal is to have classes that will be responsible for connecting with third party api's.

   - Repository: Its main goal is to have classes for dealing with DataBase operations.


# Testing design

The tests have been divided in the following categories:

   - Unit tests: Their main goal is to test each class as a completely independent unit by mocking any dependency that it may have.

   - Integration tests: Their main goal is to test the relations between the internal components of the application. In order to mock
     the external SwApi dependency, I used "wiremock" to return mocked json responses so that the tests can still work without depending on the
     third party SwApi.

   - Acceptance tests: Although not included in the project, I think that some "cucumber" tests could also add some more reliability to the application and at the same time complement the documentation.

In order to make the 3 testing phases (Arrange, Act, Assert) more readable, the following libraries have been used:

   - Jfixture: In order to create objects quickly in the Arrange phase

   - FluentAssertions: In order to make assertions phase more natural and readable.




# How to build the application

In order to build the application we should make use of gradlew so that everybody can share the same gradle version
and it can be downloaded automatically if it does not exist in the local machine.

In order to build the application, run the following command inside the project directory: gradlew build

In order to run the tests, run the following command inside the project directory: gradlew test


#How to generate the documentation

In order to see the javadocs documentation, run the following command inside the project directory: gradlew javadoc

The documentation generated in the step before can be found at: build/docs


# How to run the application

After building the application, you can start it by running the following command inside the project directory: java -jar build/libs/qforce-1.0.jar

Once the application is started, you can search the endpoints at:

    1)http://localhost:8080/api/persons?q={name}

    2)http://localhost:8080/api/persons/{id}


