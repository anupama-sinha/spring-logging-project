## Myth for Logging
* Writing log.info is just a tip of an iceberg. Rather, what we must see is architecture of the project, reasons for logging(Support, Data Analytics, Security Checks, Application Issues, Functional Bugs, Ticket Creation, etc), logging architecture(Centralized/Decentralized,Partionioned/SOA/Microservices, Implementaion Library, Different Application Tech Stacks and sync ups)

## Need for Logging
* Configurable
* Log levels
* Log destination
* Able to analyze bugs and perform Data Analytics

## Centralized vs Decentralized Logging
* Depends on architecture which is built
* Write to DB & perform SQL to check type of log. Or use ELK framework & Splunk Architecture to perform analytics
* Performance hit on synchronizing file access between multiple modules & file lock
* Logs are queued or cached in RAM first and then dumped to disk in sequence as write speed allows
* Fishtag or Sleuth used for assigning unique IDs to logs to keep consitency across microservices
* Microservices architecture reduces overhead of log file size. Even SQL Partitioning can be done for logging

## History
* JDK 1.4 : java.util.logging has Loggers, Handlers
* Log4J : Library introduced for Loggers, Formatting Options & Appenders(File,Console,Rolling File, JDBC, SMTP, JMS)
* If logging style change required, all methods had to be changed, which created problem
* Introduction of Facade Library : Apache Commons, SLF4J(Most common). Implementation needed with other libraries
* Implementation Library with SLF4J support also : Logback, Log4J2

## Advantages of using Facade Library/Standard Specificiations
* Any need for change won't require effort to change methods accordingly for implementation library. Instead an interface facade library takes care of the same. And a single dependency change does the job

## Log4J2 advantages over Logback
* Lazy Loading messages
* Async Logging

## Spring Boot Logging
* spring-boot-starter-web -> Pulls spring-boot-starter-logging -> Pulls spring-jcl(Spring Commons Logging Bridge)
* Logback : Successor of Log4j
* Can be checked using mvn dependency:tree
* 
## Supported Log Levels
* SLF4J : FATAL > ERROR > WARN > INFO > DEBUG > TRACE
* [Check others here](https://www.javacodegeeks.com/java-logging-tutorials)

## Setting Logging Level
* Default level : INFO

```properties
logging.level.root=TRACE
logging.level.com.anupama.sinha=ERROR
```

* https://docs.spring.io/spring-boot/docs/2.1.1.RELEASE/reference/html/boot-features-logging.html

## Java Logging Framework
* Logger
* Formatter/Renderer
* Handler(Appender)

## Basic Step
* Message + Metadata -> Logger captures & passes to Logging framework -> Passed to Formatter -> Passed to Appender for disposition-> Output(Console Display/Disk writing/Append to DB/Email,etc)
* Logger & Appender : Present in basic Logging but less configurable across environments

## Logger
* Passes Object/Exception to Logger Object with given name/identifier

## Formatter
* Does formatting
* Example : Takes binary object -> Convert to String representation

## Handler/Appender
* Listens for message & posts appropriately in output
* ConsoleAppender : Rolls details in console
* FileAppender : Rolls details to file mentioned
* Defines log level threshold above which to log for each resource

## Enable Logging
* Adding needed Libraries/JARs
* Configuration
* Log Statements

## Log4J2
* Adding below dependency in pom.xml

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-web</artifactId>
	<exclusions>
	    <exclusion>
	        <groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-logging</artifactId>
		</exclusion>
	</exclusions>
</dependency>
<dependency>
	<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-log4j2</artifactId>
		<version>2.3.4.RELEASE</version>
</dependency>
```

* Simple Logging in any class

```java 
public class RestController {

    Logger log = LoggerFactory.getLogger(SpringLoggingProjectApplication.class);

    @GetMapping("/log")
    String logMessage(){
        log.info("Hi Anupama, Logging a message here...");
        return "Message logged in Console";
    }
}
```

* Logging format as below

```text
2021-06-13 18:11:38.860  INFO 36336 --- [nio-8080-exec-1] c.a.s.SpringLoggingProjectApplication    : Hi Anupama, Logging a message here...
```

* Now, add [log4j2.xml](https://github.com/anupama-sinha/spring-logging-project/blob/master/src/main/resources/log4j2.xml) in src/main/resources and check logging format
* Significance of below format : %d : Date, %p : Priority(Logging Severity), %t : Thread, %c : Class, %n : New Line, And rest uri & endpoint configured using Logging Interceptor and registering the same for WebMvcConfigurer registry

>  %d{HH:mm:ss.SSS} | %5p | (%15.15t) | [%c{1}] : %m; | uri=%X{URI}; endpoint=%X{ENDPOINT}; queryString=%X{QUERYSTRING};%n


```text
21:26:22.979 |  INFO | (nio-8080-exec-2) | [LoggingInterceptor] : Incoming Request; | uri=/log; endpoint=logMessage; queryString=;
21:26:23.003 |  INFO | (nio-8080-exec-2) | [SpringLoggingProjectApplication] : INFO : Hi Anupama, Logging a message here...; | uri=/log; endpoint=logMessage; queryString=;
21:26:23.003 | ERROR | (nio-8080-exec-2) | [SpringLoggingProjectApplication] : ERROR : Hi Anupama, Logging a message here...; | uri=/log; endpoint=logMessage; queryString=;
21:26:23.004 |  WARN | (nio-8080-exec-2) | [SpringLoggingProjectApplication] : WARN : Hi Anupama, Logging a message here...; | uri=/log; endpoint=logMessage; queryString=;
21:26:23.064 |  INFO | (nio-8080-exec-2) | [LoggingInterceptor] : Request Completed; execTime=87ms; | uri=/log; endpoint=logMessage; queryString=;
```
 
* This creates a directory log in project path and creates application.log as mentioned in xml file

## Asynchronous Logging
* LMAX disruptor dependency is used
* https://www.baeldung.com/java-logging-intro
* https://logging.apache.org/log4j/2.x/manual/appenders.html

## References
* https://www.javacodegeeks.com/java-logging-tutorials
