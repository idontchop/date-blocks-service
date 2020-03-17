package com.idontchop.blocks.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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
	 * Adds all blocks from to List to the user's blocks. Will create new
	 * block record if does not exist.
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	public boolean addBlocks ( String from, List<String> to ) {
		
		Query query = new Query();
		
		query.addCriteria(Criteria.where(FROM).is(from));
		Update update = new Update();
				
		update.addToSet(TOARRAY).each(to);
		
		return mongoTemplate.upsert(query, update, Blocks.class).wasAcknowledged();

	}
	
	/**
	 * Deletes blocks. If the user is left with no blocks, removes the
	 * record from DB.
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	public boolean deleteBlocks ( String from, List<String> to ) {
		
		Optional<Blocks> blockOpt = blocksRepository.findByFrom(from);
		
		// if no from exists, don't continue
		if ( blockOpt.isEmpty() ) return false;
		
		// remove all blocks to block set
		to.forEach( newBlock -> blockOpt.get().getBlocks().remove(newBlock));
		
		// check if we still have blocks, if not remove the record
		
		if ( blockOpt.get().getBlocks().size() == 0) {
			
			// remove because has no blocks
			blocksRepository.deleteById(blockOpt.get().getId());
			
		} else {
			
			// still has blocks, just save it back
			blocksRepository.save(blockOpt.get());
			
		}
		
		return true;
		
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
	
	/**
	 * Returns a user's blocks that appear in passed to List.
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	public Set<String> allBlocks ( String from, List<String> to ) {
		
		Set<String> blocks = findBlocks(from).getBlocks()
				.stream()
				.filter(
						b -> to.contains(b) )
				.collect(Collectors.toSet());
		
		return blocks;
		
	}
}
