#Foxminded mentoring course

##Task - University
Create university web project. Decomposit from student to department. 

##Usage

###Compile project
For compile project put files in folder "src/main/java" Use next command in command line:
```
mvn compile
```

###Create execute war 
For create execute war file and type in the following command and hit "Enter"
```
mvn clean install
```


#### Configure apache tomcat 9 server:
Add to $Catalina/lib folder library for postgresql (postgresql-42.2.8.jar)

###Starting project:
For starting project deploy war file on web-server. And follow 
to web link: http://{ip address web-server:port}/{name of war file}
```
For example: http://localhost:8080/task-university-java8-api-0.0.1-SNAPSHOT
```

###Tests
For starting checkstyle audit 
Use command and get "Ok" (Audit done)!
```
mvn validate
```

For starting unit tests and checkstyle audit
Use command and get "Ok" (20 tests)!
```
mvn test
```
For starting integration tests, unit tests and checkstyle audit
Use command and get "Ok" (4 tests)!
```
mvn verify
```

##Libraries
Use libraries from pom.xml file

##History
####Task 8 - Decompose university
Decomposit university: student, teacher, group, auditory, schedule for student and teacher. UML diagram.
Branch dev, src/main/java/resources/uml_structure_university.png 
####Task 9 - Domain layer
Created JAVA project based on University UML class diagram from the previous task.
Implemented domain logic for University.
Writed JUnit tests for the created application.
Branch dev
####Task 10 - DAO layer
Created plain JDBC based DAO for decomposed domain objects using DriverManager.
Created sql_scheme.png. Created DAO layer. 
Branch: task10
####Task 11 - Exceptions and Logging
Added custom exceptions and logging.
Branch: task10
####Task 12 - Maven
Converted current project to Maven format. Added checkstyle plugin
Branch: taskMaven
####Task 13 - User Interface-1
Created status pages (read data from dao - show it in JSP). Added servlets for show objects. Added flyway plugin.
Branch: task13
####Task 14 - User Interface-2
Created CRUD pages for decomposed domain objects (servlets + JSP). Added validator for objects.
Branch: task14
####Task 15 - Data Source
Created DataSource in web-project configuration files. Switch DAO layer to lookup DataSource by JNDI name and use it instead of simple JDBC connection.
Branch: task15
####Task 16 - Spring IoC
Rewrite DAO code to inject DataSource to all DAO objects using Spring IoC framework.
Branch: task16
####Task 17 - Hibernate
Rewrite the DAO layer. Use Hibernate instead of Spring JDBC.
Add for JPA Criteria query metamodel generator. 
Also for work with IDE need to add as source Build Path folder target/generated-sources/annotation
Branch: task17
####Task 17 additions
Add Sonar Lint plugin to eclipse
Add Spring MVC
Add Thymeleaf
Branch: task17.2
####Task 18 - Spring Boot
Convert application to Spring Boot
Branch: task18


##Author
With best regards, Mykola Afanasiev!
