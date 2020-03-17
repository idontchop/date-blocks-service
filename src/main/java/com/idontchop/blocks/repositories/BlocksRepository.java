package com.idontchop.blocks.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.idontchop.blocks.entities.Blocks;

@RepositoryRestResource(collectionResourceRel = "blocks", path = "blocks")
public interface BlocksRepository extends MongoRepository<Blocks, String> {

	public Blocks findByUsername(String username);
}
