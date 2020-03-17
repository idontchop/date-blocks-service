package com.idontchop.blocks;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.idontchop.blocks.entities.Blocks;
import com.idontchop.blocks.repositories.BlocksRepository;
import com.idontchop.blocks.service.BlocksService;

@SpringBootTest
class BlocksApplicationTests {
	
	@Autowired
	BlocksRepository blocksRepository;
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	BlocksService blocksService;

	@Test
	void contextLoads() {
	}
	
	@Test
	void modifyMongo() {
		
		Blocks b = blocksRepository.findById("5e705c40b2e8f9498f822b27").get();
		b.setFrom("username");
		blocksRepository.save(b);
	}
	
	@Test
	void testMongoHasBlock() {
		
		Query query = new Query();
		
		String from = "username";
		String to = "1000";
		

		assertTrue (blocksService.hasBlock(from, to));
	}
	
	@Test
	void testMongoIsBlocked() {
		
		Query query = new Query();
		
		String from = "1";
		String to = "username";
		
		assertTrue (blocksService.isBlocked(from, to));
	}

}
