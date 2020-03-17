package com.idontchop.blocks;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

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
	void addBlock () {
		
		Query query = new Query();
		
		String from = "newUsername2";
		String to = "1111";
		
		query.addCriteria(Criteria.where("from").is(from));
		Update update = new Update();
		
		update.addToSet("blocks", to);
		
		assertTrue(mongoTemplate.upsert(query, update, Blocks.class).wasAcknowledged());
		
		assertTrue(blocksService.hasBlock(from, to));
		
	}
	
	
	void modifyMongo() {
		
		Blocks b = blocksRepository.findById("5e705c40b2e8f9498f822b27").get();
		b.setFrom("username");
		blocksRepository.save(b);
	}
	
	@Test
	void testMongoHasBlock() {
		
		Query query = new Query();
		
		String from = "newUsername";
		String to = "1111";
		

		assertTrue (blocksService.hasBlock(from, to));
	}
	
	@Test
	void testMongoIsBlocked() {
		
		Query query = new Query();
		
		String from = "newUsername";
		String to = "1111";
		
		assertTrue (blocksService.isBlocked(from, to));
	}

}
