package com.idontchop.blocks.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.idontchop.blocks.entities.Blocks;
import com.idontchop.blocks.repositories.BlocksRepository;

@Service
public class BlocksService {
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	BlocksRepository blocksRepository;

	/**
	 * Returns true if a block exists in either direction.
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	public boolean isBlocked(String from, String to) {
		
		Query query = new Query();
		
		query.addCriteria(new Criteria()
				.orOperator(Criteria.where("from").is(from).and("blocks").all(to),
						Criteria.where("from").is(to).and("blocks").all(from)));
		
		return mongoTemplate.count(query, Blocks.class) > 0 ? true : false;
		
	}
	
	/**
	 * Returns true if has unidirectional block
	 * @param from
	 * @param to
	 * @return
	 */
	public boolean hasBlock(String from, String to) {
		
		Query query = new Query();
		
		query.addCriteria(Criteria.where("from").is(from).and("blocks").all(to));
		
		return mongoTemplate.count(query, Blocks.class) == 1 ? true : false;

	}
}
