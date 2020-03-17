package com.idontchop.blocks;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.idontchop.blocks.entities.Blocks;
import com.idontchop.blocks.repositories.BlocksRepository;

@SpringBootTest
class BlocksApplicationTests {
	
	@Autowired
	BlocksRepository blocksRepository;
	
	@Autowired
	MongoTemplate mongoTemplate;

	@Test
	void contextLoads() {
	}
	
	@Test
	void testMongo() {
		
		Query query = new Query();
		
		query.addCriteria(Criteria.where("blocks").all("1000"));
		List<Blocks> b = mongoTemplate.find(query, Blocks.class);
		
		b.forEach( e -> System.out.println(e.getUsername()));
	}

}
