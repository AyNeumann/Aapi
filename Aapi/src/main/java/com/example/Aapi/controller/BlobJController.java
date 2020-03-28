package com.example.Aapi.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Aapi.dto.BlobJ;
import com.example.Aapi.dto.BlobJType;
import com.example.Aapi.service.BlobJService;

/**
 * Manage request about blob.
 * @author Aymeric NEUMANN
 *
 */
@Validated
@RestController
@RequestMapping("/blobj/")
public class BlobJController {
	
	/** Reference to the log4j logger. */
	private static final Logger LOG = LogManager.getLogger();
	
	/** Reference to BlobJService. */
	@Autowired
	private BlobJService blobJService;
		
	/**
	 * Create a BlobJ and return saved BlobJ.
	 * @param blobj BlobJ to create
	 * @param bindingResult spring framework validation interface
	 * @return the saved BlobJ
	 */
	@PostMapping("save")
	public BlobJ saveBlobJ(@RequestBody @Valid final BlobJ blobj, final BindingResult bindingResult) {
				
		if (bindingResult.hasErrors()) {
			String message = "Attempt to create a Blobj with invalid data.";
			LOG.warn(message);
			throw new IllegalArgumentException(message);
		}
		
		BlobJ savedBlobJ = blobJService.saveBlobj(blobj);
		
		return savedBlobJ;
	}
	
	/**
	 * Create a list of BlobJ and return saved BloBJs.
	 * The list is validated with the valid annotation in the method parameters
	 * and with the validated annotation above the class.
	 * If one of the item of the list is invalid throws ConstraintViolationException.
 	 * @param blobj BloBJs to save
	 * @param bindingResult bindingResult spring framework validation interface
	 * @return the saved BlobJs
	 */
	@PostMapping("saveAll")
	public Iterable<BlobJ> saveAllBlobJs(@RequestBody @Valid final List<BlobJ> blobjs, final BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			String message = "Attempt to create a BlobJs with an invalid list.";
			LOG.warn(message);
			throw new IllegalArgumentException(message);
		}
							
		Iterable<BlobJ> savedBlobJ = blobJService.saveAllBlobj(blobjs);
				
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
	 * Retrieve the BlobJs with a count strictly equal to requested count. 
	 * @param minCount minimal Count requested
	 * @return a list of BlobJ with matching conditions - Set<BlobJ>
	 */
	@GetMapping("byCount")
	public Set<BlobJ> retrieveByCount(@RequestParam(name="count", required = true ) final Integer count) {
		
		Set<BlobJ> blobJsToRetrieve  = blobJService.retrieveByCount(count, "exact");
		
		return blobJsToRetrieve;
	}
	
	
	/**
	 * Retrieve the BlobJs with a count equal to or than greater the requested count. 
	 * @param minCount minimal Count requested
	 * @return a list of BlobJ with matching conditions - Set<BlobJ>
	 */
	@GetMapping("byCountMin")
	public Set<BlobJ> retrieveByCountMin(@RequestParam(name="minCount", required = true ) final Integer minCount) {
		
		Set<BlobJ> blobJsToRetrieve  = blobJService.retrieveByCount(minCount, "min");
		
		return blobJsToRetrieve;
	}
	
	/**
	 * Retrieve the BlobJs with a count equal to or less than the requested count. 
	 * @param minCount minimal Count requested
	 * @return a list of BlobJ with matching conditions - Set<BlobJ>
	 */
	@GetMapping("byCountMax")
	public Set<BlobJ> retrieveByCountMax(@RequestParam(name="maxCount", required = true ) final Integer maxCount) {
		
		Set<BlobJ> blobJsToRetrieve  = blobJService.retrieveByCount(maxCount, "max");
		
		return blobJsToRetrieve;
	}
	
	/**
	 * Retrieve the BlobJs with a count between the requested minimum and maximum count. 
	 * @param minCount minimal Count requested
	 * @return a list of BlobJ with matching conditions - Set<BlobJ>
	 */
	@GetMapping("byCountTranche")
	public Set<BlobJ> retrieveByCountTranche(
			@RequestParam(name="minCount", required = true ) final Integer minCount,
			@RequestParam(name="maxCount", required = true ) final Integer maxCount) {
		
		Set<BlobJ> blobJsToRetrieve  = blobJService.retrieveByCountTranche(minCount, maxCount);
		
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
	public BlobJ updateBlobJ (@RequestBody @Valid final BlobJ blobj, final BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			String message = "Attempt to udpate a Blobj with invalid data.";
			LOG.warn(message);
			throw new IllegalArgumentException(message);
		}
		
		BlobJ udpatedBlob = blobJService.updateBlobJ(blobj);
		
		return udpatedBlob;
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
