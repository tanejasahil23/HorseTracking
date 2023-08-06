package com.demo.horsetracking;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.demo.horsetracking.controller.AccessorModeController;

@SpringBootApplication
public class HorsetrackApplication implements CommandLineRunner {

	@Autowired
	private ConfigurableApplicationContext context;

	@Autowired
	private AccessorModeController accessorMode;

	public static void main(String[] args) {
		SpringApplication.run(HorsetrackApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {

		accessorMode.initialize();

		accessorMode.printStartupMessages();

		Scanner commandString = new Scanner(System.in);
		while (!(accessorMode.quit())) {

			accessorMode.execute(commandString.nextLine());

		}

		System.exit(SpringApplication.exit(context));

	}
}
