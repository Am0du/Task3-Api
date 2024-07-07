package com.example.task3;

import com.example.task3.DAO.UserDAO;
import com.example.task3.entity.Organisations;
import com.example.task3.entity.Users;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Task3Application {

	public static void main(String[] args) {
		SpringApplication.run(Task3Application.class, args);
	}
//
//	@Bean
//	public CommandLineRunner commandLineRunnerRunner(UserDAO userDAO){
//		return runner ->{
//			Users user = userDAO.findUser("");
//			System.out.println(user);
//		};
//	}
}
