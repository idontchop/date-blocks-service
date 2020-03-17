package com.idontchop.blocks.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.idontchop.blocks.entities.Blocks;
import com.idontchop.blocks.repositories.BlocksRepository;

@Service
public class BlocksService {
	
	// Mongo Collection fields
	private final String 	FROM	= 	"from";		// The username who set blocks
	private final String	TOARRAY	=	"blocks";   // Array field of user's blocks
			
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
		
		query.addCriteria(new Criteria().orOperator(
				Criteria.where(FROM).is(from).and(TOARRAY).all(to),
				Criteria.where(FROM).is(to).and(TOARRAY).all(from)));
		
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
		
		query.addCriteria(Criteria.where(FROM).is(from).and(TOARRAY).all(to));
		
		return mongoTemplate.count(query, Blocks.class) == 1 ? true : false;

	}
	
	public Blocks findBlocks ( String from ) {
		
		Query query = new Query();
		
		query.addCriteria ( Criteria.where(FROM).is(from) );
		
		Optional<Blocks> blockOpt = blocksRepository.findByFrom(from);
		
		// If no blocks, we can just return a new blocks with the username
		// no need to save an empty one to database
		Blocks block = blockOpt.orElse( new Blocks(from) );
		
		return block;
		
	}
	/**
	 * Returns the user's array of blocks only.
	 * 
	 * @param from
	 * @return
	 */
	public Set<String> allBlocks (String from) {
		
		return findBlocks(from).getBlocks();

	}
}
