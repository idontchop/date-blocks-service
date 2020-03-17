package com.idontchop.blocks.entities;

import java.util.Set;

import org.springframework.data.annotation.Id;

public class Blocks {

	public Blocks () {}
	
	public Blocks ( String username ) {
		this.username = username;
	}
	@Id
	public String id;
	
	// provided by JWT token
	// This is the FROM field for a block
	public String username;
	
	public Set<String> blocks;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<String> getBlocks() {
		return blocks;
	}

	public void setBlocks(Set<String> blocks) {
		this.blocks = blocks;
	}
	
	
}
