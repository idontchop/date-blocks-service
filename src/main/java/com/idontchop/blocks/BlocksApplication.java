package com.idontchop.blocks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * TODO:
 * 
 *  After brainstorming, determined that blocks should be written twice, once in who someone blocked
 *  and who blocked the user. Would mean each document has a blocked and isBlocked field.
 *  
 *  Duplication of data and slower writes but would make reads super quick. 
 *  
 * @author micro
 *
 */
@SpringBootApplication
public class BlocksApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlocksApplication.class, args);
	}
	

}
