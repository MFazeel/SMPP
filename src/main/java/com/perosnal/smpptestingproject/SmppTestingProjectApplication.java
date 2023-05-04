package com.perosnal.smpptestingproject;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.perosnal.smpptestingproject.SMPP.MultipleSubmitExample;

@SpringBootApplication
public class SmppTestingProjectApplication {

	public static void main(String[] args) {
//		SpringApplication.run(SmppTestingProjectApplication.class, args);

		MultipleSubmitExample multiSubmit = new MultipleSubmitExample();
		multiSubmit.broadcastMessage("Test message from Fazeel", Arrays.asList("923037512284", "923037512284"));

	}

}
