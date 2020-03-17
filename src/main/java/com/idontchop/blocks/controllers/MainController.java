package com.idontchop.blocks.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idontchop.blocks.entities.Blocks;
import com.idontchop.blocks.repositories.BlocksRepository;

/**
 * Since the Blocks Microservice is very specific, all endpoints will be created here manually
 * instead of using data-rest.
 * 
 * Generally the endpoint used when a user is querying / modifying his own.
 * 
 * /block/{from}/[{to}]  (to is array)
 * 
 * PUT - must have a [to]
 * POST - identical to put
 * GET - leaving off to will get all, only returns those found in [to]
 * DELETE - leaving off [to] will remove account, otherwise deletes all in [to]
 * 
 * Other endpoints will be used to check status from any calls. Can be flipped.
 * 
 * /isblocked/{from}/{to}
 * 
 * GET - Checks for existence of block, returns 200 if blocked, 404 if block not found
 * 
 * @author nathan
 *
 */
@RestController
public class MainController {
	
	@Autowired
	BlocksRepository blocksRepository;
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@RequestMapping ("/helloWorld")
	public String helloWorld () {
		return " {\n \"message\": \"Hello World!\",\n\"Service\": \"Dating App Blocks\"\n}";
	}
	
	/**
	 * DEVELOPMENT. For testing.
	 * 
	 * @return
	 */
	@RequestMapping ("/getAllUsers")
	public List<Blocks> getAllUsers () {
		return blocksRepository.findAll();
	}
	
}
