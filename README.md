Test Task 3
=========

Prerequisites
-------------

* [Java Development Kit (JDK) 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Maven 3](https://maven.apache.org/download.cgi)
* [Vaadin 8](https://vaadin.com/vaadin-8)
* [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
* [HSQLDB](http://hsqldb.org/doc/2.0/guide/)

Description
-------------

Sql scripts: 
* src/main/resources/schema.sql
* src/main/resources/data.sql

Database:
* /database

Build and Run
-------------

1. Run in the command line:
	```
	mvn package
	mvn jetty:run
	```

2. Open `http://localhost:8080` in a web browser.
