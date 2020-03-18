package com.idontchop.blocks.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.idontchop.blocks.entities.Blocks;
import com.idontchop.blocks.repositories.BlocksRepository;
import com.idontchop.blocks.service.BlocksService;

/**
 * Since the Blocks Microservice is very specific, all endpoints will be created here manually
 * instead of using data-rest.
 * 
 * Generally the endpoint used when a user is querying / modifying his own.
 * 
 * /block/{from}/[{to}]  (to is array)
 * 
 * PUT - must have a [to]
 * POST - identical to put
 * GET - leaving off to will get all, only returns those found in [to]
 * DELETE - leaving off [to] will remove account, otherwise deletes all in [to]
 * 
 * Other endpoints will be used to check status from any calls. Can be flipped.
 * 
 * /isBlocked/{from}/{to}
 * 
 * GET - Checks for existence of block, returns 200 if blocked, 404 if block not found
 * 
 * /isBlocked/{from}/[{to}]
 * 
 * @author nathan
 *
 */
@RestController
public class MainController {
	
	@Autowired
	BlocksRepository blocksRepository;
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	BlocksService blocksService;
	
	@RequestMapping ("/helloWorld")
	public String helloWorld () {
		return " {\n \"message\": \"Hello World!\",\n\"Service\": \"Dating App Blocks\"\n}";
	}
	
	/**
	 * DEVELOPMENT. For testing.
	 * 
	 * @return
	 */
	@RequestMapping ("/getAllUsers")
	public List<Blocks> getAllUsers () {
		return blocksRepository.findAll();
	}
	
	@GetMapping ( value = {"/block/{from}/{to}", "/block/{from}"} )
	public Set<String> getBlock ( @PathVariable ( name = "from", required = true) String from,
			@PathVariable ( name = "to", required = false) List<String> to ) {
		
		// if we didn't get a to list, just return all the user's blocks
		if ( to == null ) {
			return blocksService.allBlocks(from);
		} else {
			return blocksService.allBlocks(from, to);
		}
		
	}
	
	@RequestMapping ( value = "/block/{from}/{to}",
			method = { RequestMethod.POST, RequestMethod.PUT } )
	public String putBlock ( @PathVariable ( name = "from", required=true) String from, 
			@PathVariable ( name = "to", required=true) List<String> to )  {
		
		// good return 200
		if ( blocksService.addBlocks(from, to) ) {
			return "";
		} else {
			throw new ResponseStatusException ( HttpStatus.NOT_FOUND, "Not Found: " + from );
		}
				
	}
	
	@DeleteMapping ( "/block/{from}/{to}" )
	public String deleteBlock ( @PathVariable ( name = "from", required=true) String from,
			@PathVariable ( name = "to", required=true) List<String> to ) {
		
		// good return 200
		if ( blocksService.deleteBlocks(from, to) ) {
			return "";
		} else {
			throw new ResponseStatusException ( HttpStatus.NOT_FOUND, "Not Found: " + from );
		}
			
	}
	
	@GetMapping ( "/isBlockedList/{from}/{to}")
	public Set<String> isBlockedList ( @PathVariable ( name = "from", required=true) String from, 
			@PathVariable ( name = "to", required = true) List<String> to ) {
		
		return blocksService.isBlockedList(from, to);
		
	}
		
	@GetMapping ( "/isBlocked/{from}/{to}")
	public ArrayList<String> isBlocked ( @PathVariable ( name = "from", required = true ) String from, 
			@PathVariable ( name = "to", required = true) String to) {
		
		if (blocksService.isBlocked(from, to)) {
			return new ArrayList<String>(Arrays.asList(to));
		} else {
			throw new ResponseStatusException( HttpStatus.NOT_FOUND, "Not Found: " + to);
		}
		
	}
	
}
