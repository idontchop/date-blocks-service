package com.idontchop.blocks.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.idontchop.blocks.entities.Blocks;

public interface BlocksRepository extends MongoRepository<Blocks, String> {

	public Blocks findByFrom(String from);
	
}
