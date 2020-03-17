package com.idontchop.blocks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.idontchop.blocks.repositories.BlocksRepository;

@Service
public class BlocksService {
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	BlocksRepository blocksRepository;

}
