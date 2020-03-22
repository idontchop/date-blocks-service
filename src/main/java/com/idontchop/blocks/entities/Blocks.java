package com.idontchop.blocks.entities;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;

public class Blocks {

	public Blocks () {}
	
	public Blocks ( String from ) {
		this.from = from;
	}
	@Id
	public String id;
	
	// provided by JWT token
	// This is the FROM field for a block
	public String from;
	
	public Set<String> blocks = new HashSet<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public Set<String> getBlocks() {
		return blocks;
	}

	public void setBlocks(Set<String> blocks) {
		this.blocks = blocks;
	}
	
	
}
