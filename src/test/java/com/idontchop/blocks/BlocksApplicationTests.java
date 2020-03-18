package com.idontchop.blocks;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;
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
	void deleteBlock () {
		
		String from = "1";
		
		List<String> to = new ArrayList<>();
		to.add("last");
		to.add("lastagain");
		
		assertTrue(blocksService.deleteBlocks(from, to));
		
	}
	
	@Test
	void testIsBlockedList () {
		String from = "1";
		List<String> to = new ArrayList<>();
		to.addAll( List.of("5", "username2","1000", "44") );
		
		assertTrue ( blocksService.isBlockedList(from, to).size() == 2);
	}
	
	@Test
	void addBlock () {
		
		Query query = new Query();
		
		String from = "1";
		List<String> to = new ArrayList<>();
		
		to.add("1111");
		to.add("34");
		to.add("44");
		to.add("44");
		
		assertTrue(blocksService.addBlocks(from, to));
		
	}
	
	@Test
	void testMongoAllBlocks() {
		
		String from = "username";
		
		List<String> toBlocks = new ArrayList<>();
		toBlocks.add("2");
		toBlocks.add("900");
		toBlocks.add("1");
		
		assertTrue ( blocksService.allBlocks(from, toBlocks).size() == 3);
		
		
	}
	
	@Test
	void testMongoHasBlock() {
		
		Query query = new Query();
		
		String from = "1";
		String to = "1111";
		

		assertTrue (blocksService.hasBlock(from, to));
	}
	
	@Test
	void testMongoIsBlocked() {
		
		Query query = new Query();
		
		String from = "1";
		String to = "1111";
		
		assertTrue (blocksService.isBlocked(from, to));
	}

}
