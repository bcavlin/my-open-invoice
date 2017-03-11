package com.bgh.myopeninvoice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource({"classpath:db.properties"})
public class InvoiceServerApplication {

	public static void main(String[] args) {
        SpringApplication.run(InvoiceServerApplication.class, args);
	}
}
