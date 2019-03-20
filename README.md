my-open-invoice
=====
[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

Time reporting and invoice generation tool specialized for the IT consultant. This project utilizes 
[H2](http://www.h2database.com/html/main.html) database for DEV and Postgres database for PROD, [Spring Boot](http://projects.spring.io/spring-boot). 
Angular 6 was used as front end framework, but due to licensing issues, it is kept in private repository. 

## Initialization
Application DB is initialized with [Liquibase](https://www.liquibase.org/) on startup, based on the profile (dev or prod). Since the project uses [Querydsl](http://www.querydsl.com/)
it is necessary to compile the application in the maven to get Q* objects that are used for filtering 
data. Template if hardcoded for the current time. Two templates are available in /data directory.

Before running the app, create DB manually and create schema named INOVICE.

Environment variables: INVOICE_DB_LOCATION=jdbc:postgresql://localhost:5432/<db>;JASYPT_PWD=<pass>

## License
Copyright 2019 Branislav Cavlin. Licensed under the Apache [License](LICENSE), Version 2.0 (the "License").

#### Some examples taken from
https://github.com/mrin9/Angular-SpringBoot-REST-JWT