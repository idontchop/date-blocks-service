package com.idontchop.blocks.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.idontchop.blocks.entities.Blocks;

public interface BlocksRepository extends MongoRepository<Blocks, String> {

	public Optional<Blocks> findByFrom(String from);
	
}
