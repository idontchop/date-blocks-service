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
	
	@RequestMapping ("/getAllUsers")
	public List<Blocks> getAllUsers () {
		return blocksRepository.findAll();
	}
}
