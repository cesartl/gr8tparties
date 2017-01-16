package com.ctl.gr8tparties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;

@SpringBootApplication
public class FriendServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FriendServiceApplication.class, args);
	}

	/*
	 This beans allows to convert Protocol buffer classes into different type like native protocol buffer type, JSON, XML etc...
	 Just change the accept header in the HTTP request to get the desired type
	 */
	@Bean
	ProtobufHttpMessageConverter protobufHttpMessageConverter() {
		return new ProtobufHttpMessageConverter();
	}
}
