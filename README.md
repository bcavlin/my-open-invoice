my-open-invoice
=====
[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

Time reporting and invoice generation tool specialized for the IT consultant. This project utilizes 
[H2](http://www.h2database.com/html/main.html) database, [JoinFaces](http://joinfaces.org) 
and [Spring Boot](http://projects.spring.io/spring-boot).

## TODO
* Create Jasper Report engine integration<br/>
* Create Invoice report<br/>
* Create Main page<br/>

## Initialization
Before using the application, user needs to pass in command line parameter for the database init: 

```Shell
--spring.datasource.initialize=true
```

This will create and populate basic data profile. Since the project uses [Querydsl](http://www.querydsl.com/)
it is necessary to compile the application in the maven to get Q* objects that are used for filtering 
data. To generate the report, for now, a template with name 'Invoice_V1' needs to be inserted into the REPORT_TEMPLATES
 table.

## License
Copyright 2017 Branislav Cavlin. Licensed under the Apache License, Version 2.0 (the "License").
