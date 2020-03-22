package com.example.Aapi.controller;

import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.Aapi.dto.BlobJ;
import com.example.Aapi.dto.BlobJType;
import com.example.Aapi.service.BlobJService;

/**
 * Manage request about blob.
 * @author Aymeric NEUMANN
 *
 */
@RestController
@RequestMapping("/blobj/")
public class BlobJController {
	
	/** Reference to the log4j logger. */
	private static final Logger LOG = LogManager.getLogger();
	
	/** Reference to BlobJService. */
	@Autowired
	private BlobJService blobJService;
	
	@ExceptionHandler({IllegalArgumentException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<String> handleException(final Exception ex) {
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Create a blobj and return created blobj.
	 * @param blobj blobj to create
	 * @param bindingResult spring framework validation interface
	 * @return the created blobj
	 */
	@PostMapping("save")
	public BlobJ saveBlob(@RequestBody @Valid final BlobJ blobj, final BindingResult bindingResult) {
		
		LOG.warn(blobj.toString());
		
		if (bindingResult.hasErrors()) {
			String message = "Attempt to create a Blobj with invalid data";
			LOG.warn(message);
			throw new IllegalArgumentException(message);
		}
		
		BlobJ savedBlobJ = blobJService.saveBlobj(blobj);
		
		return savedBlobJ;
	}
	
	/**
	 * Retrieve all blob from the database and return paginated data - 50 blobJ/page.
	 * @param pageNumber number of the page requested - 0 base count
	 * @return required page of BlobJ - Page<BlobJ>
	 */
	@GetMapping("all")
	public Page<BlobJ> retrieveAllBlobJs(@RequestParam(name="pageNumber", required = true ) final Integer pageNumber) {
		
		return blobJService.retrieveAllBlobJs(pageNumber);
	}
	
	/**
	 * Retrieve the BlobJ with the matching id.
	 * @param id id id of the BlobJ to retrieve
	 * @return found BlobJ - Optional<BlobJ>
	 */
	@GetMapping("byId")
	public Optional<BlobJ> retrieveById(@RequestParam(name="id", required = true ) final Long id) {
		
		Optional<BlobJ> blobJToRetrieve = blobJService.retrieveById(id);
		
		return blobJToRetrieve;
	}
	
	/**
	 * Retrieve the BlobJs with a count equal or greater than minCount. 
	 * @param minCount minimal Count requested
	 * @return a list of BlobJ with matching conditions - Set<BlobJ>
	 */
	@GetMapping("byCount")
	public Set<BlobJ> retrieveByMinCount(@RequestParam(name="minCount", required = true ) final Integer minCount) {
		
		Set<BlobJ> blobJsToRetrieve  = blobJService.retrieveByMinCount(minCount);
		
		return blobJsToRetrieve;
	}
	
	/**
	 * Retrieve the BlobJs with a name which contains the received name. 
	 * @param name required name to find
	 * @return a list of BlobJ with a name which contains the received name - Set<BlobJ>
	 */
	@GetMapping("byName")
	public Set<BlobJ> retrieveByName(@RequestParam(name="name", required = true ) final String name) {
		
		Set<BlobJ> blobJsToRetrieve = blobJService.retrieveByName(name);
		
		return blobJsToRetrieve;
	}
	
	/**
	 * Retrieve all BlobJs with a matching type.
	 * @param type type of BlobJ to retrieve
	 * @return all BlobJs with a matching type - Set<BlobJ>
	 */
	@GetMapping("byType")
	public Set<BlobJ> retrieveByType(@RequestParam(name="type", required = true ) final BlobJType type) {
		
		Set<BlobJ> blobJsToRetrieve = blobJService.retrieveByType(type);
		
		return blobJsToRetrieve;
	}
	
	/**
	 * Update the BlobJ with the matching id.
	 * @param blobj new BlobJ data
	 * @param bindingResult spring framework validation interface
	 */
	@PutMapping("update")
	public void updateBlobJ (@RequestBody @Valid final BlobJ blobj, final BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			String message = "Attempt to udpate a Blobj with invalid data";
			LOG.warn(message);
			throw new IllegalArgumentException(message);
		}
		
		blobJService.updateBlobJ(blobj);
	}
	
	/**
	 * Delete the blobJ with the matching type
	 * @param id id of the BLobJ to delete
	 * @return true if the BlobJ has been deleted
	 */
	@DeleteMapping("delete")
	public boolean deleteBlogJ (@RequestParam(name="id", required = true ) final Long id) {
		
		boolean isDeleted = blobJService.deleteBlobJ(id);
		
		return isDeleted;
	}
}
