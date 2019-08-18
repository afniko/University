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


##Author
With best regards, Mykola Afanasiev!
