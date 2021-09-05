package com.example.Aapi.controller;

import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Aapi.dto.BlobJDTO;
import com.example.Aapi.dto.TagDTO;
import com.example.Aapi.entity.BlobJ;
import com.example.Aapi.entity.BlobJType;
import com.example.Aapi.exception.AapiEntityException;
import com.example.Aapi.service.BlobJService;
import com.example.Aapi.service.TagService;

//TODO: Find a solution to replace the search by name, count etc... to have more RESTFUL compliant URLs (query strings ?)
//TODO: Rank could be null. If so auto-generate rank
//TODO: Implement unit test
//TODO: Implement SWAGGER

/**
 * Manage request about blob.
 * @author Aymeric NEUMANN
 *
 */
@Validated
@RestController
@RequestMapping("/blobjs-management")
public class BlobJController {
	
	/** Reference to the log4j logger. */
	private static final Logger LOG = LogManager.getLogger();
	
	/** Reference to BlobJService. */
	@Autowired
	private BlobJService blobJService;
	
	/** Reference to the BlobJRepository */
	@Autowired
	private TagService tagService;
		
	
	/**
	 * Create a list of BlobJDTO and return saved BloBJsDTO.
	 * The list is validated with the Valid annotation in the method parameters
	 * and with the Validated annotation above the class.
	 * If one of the item of the list is invalid throws ConstraintViolationException.
 	 * @param blobj BloBJs to save
	 * @param bindingResult bindingResult spring framework validation interface
	 * @return the saved BlobJs - Set<BlobJ>
	 */
	@PostMapping("/blobjs")
	public List<BlobJDTO> saveAllBlobJs(@RequestBody @Valid final List<BlobJDTO> blobjs, final BindingResult bindingResult) {
				
		if (bindingResult.hasErrors()) {
			String message = "Attempt to create a BlobJs with an invalid list.";
			LOG.warn(message);
			throw new IllegalArgumentException(message);
		}
							
		List<BlobJDTO> savedBlobJ = blobJService.saveAllBlobj(blobjs);
				
		return savedBlobJ;
	}
	
	/**
	 * Retrieve all blob from the database and return paginated data - 50 blobJ/page.
	 * @param pageNumber number of the page requested - 0 base count
	 * @return required page of BlobJ - Page<BlobJ>
	 */
	@GetMapping("/blobjs")
	public Page<BlobJDTO> retrieveAllBlobJs(@RequestParam(name="page-number", required = true ) final Integer pageNumber) {
		
		return blobJService.retrieveAllBlobJs(pageNumber);
	}
	
	/**
	 * Retrieve the BlobJ with the matching id.
	 * @param id id of the BlobJ to retrieve
	 * @return found BlobJ - Optional<BlobJ>
	 */
	@GetMapping("blobjs/{id}")
	public BlobJDTO retrieveById(@PathVariable(name="id", required = true ) final Long id) {
		
		BlobJDTO blobJToRetrieve = blobJService.retrieveById(id);
		
		return blobJToRetrieve;
	}
	
	/**
	 * Retrieve the BlobJs with a count strictly equal to requested count. 
	 * @param minCount minimal Count requested
	 * @return a list of BlobJ with matching conditions - Set<BlobJ>
	 */
	@GetMapping("blobjs/count")
	public Set<BlobJ> retrieveByCount(@RequestParam(name="count", required = true ) final Integer count) {
		
		Set<BlobJ> blobJsToRetrieve  = blobJService.retrieveByCount(count, "exact");
		
		return blobJsToRetrieve;
	}
	
	
	/**
	 * Retrieve the BlobJs with a count equal to or than greater the requested count. 
	 * @param minCount minimal Count requested
	 * @return a list of BlobJ with matching conditions - Set<BlobJ>
	 */
	@GetMapping("/byCountMin")
	public Set<BlobJ> retrieveByCountMin(@RequestParam(name="minCount", required = true ) final Integer minCount) {
		
		Set<BlobJ> blobJsToRetrieve  = blobJService.retrieveByCount(minCount, "min");
		
		return blobJsToRetrieve;
	}
	
	/**
	 * Retrieve the BlobJs with a count equal to or less than the requested count. 
	 * @param minCount minimal Count requested
	 * @return a list of BlobJ with matching conditions - Set<BlobJ>
	 */
	@GetMapping("/byCountMax")
	public Set<BlobJ> retrieveByCountMax(@RequestParam(name="maxCount", required = true ) final Integer maxCount) {
		
		Set<BlobJ> blobJsToRetrieve  = blobJService.retrieveByCount(maxCount, "max");
		
		return blobJsToRetrieve;
	}
	
	/**
	 * Retrieve the BlobJs with a count between the requested minimum and maximum count. 
	 * @param minCount minimal Count requested
	 * @return a list of BlobJ with matching conditions - Set<BlobJ>
	 */
	@GetMapping("/byCountTranche")
	public Set<BlobJ> retrieveByCountTranche(
			@RequestParam(name="minCount", required = true ) final Integer minCount,
			@RequestParam(name="maxCount", required = true ) final Integer maxCount) {
		
		if(minCount > maxCount) {
			String message = "Minimal count cannot be superior to maximal count";
			LOG.warn(message);
			throw new IllegalArgumentException(message);
		}
		
		Set<BlobJ> blobJsToRetrieve  = blobJService.retrieveByCountTranche(minCount, maxCount);
		
		return blobJsToRetrieve;
	}
	
	/**
	 * Retrieve the BlobJs with a name which contains the received name. 
	 * @param name required name to find
	 * @return a list of BlobJ with a name which contains the received name - Set<BlobJ>
	 */
	@GetMapping("/byName")
	public Set<BlobJ> retrieveByName(@RequestParam(name="name", required = true ) final String name) {
		
		Set<BlobJ> blobJsToRetrieve = blobJService.retrieveByName(name);
		
		return blobJsToRetrieve;
	}
	
	/**
	 * Retrieve all BlobJs with a matching type.
	 * @param type type of BlobJ to retrieve
	 * @return all BlobJs with a matching type - Set<BlobJ>
	 */
	@GetMapping("/byType")
	public Set<BlobJ> retrieveByType(@RequestParam(name="type", required = true ) final BlobJType type) {
				
		Set<BlobJ> blobJsToRetrieve = blobJService.retrieveByType(type);
		
		return blobJsToRetrieve;
	}
	
	/**
	 * Update the BlobJ with the matching id.
	 * @param blobj new BlobJ data
	 * @param bindingResult spring framework validation interface
	 * @return updated BlobJ
	 */
	@PutMapping
	public BlobJDTO updateBlobJ (@RequestBody @Valid final BlobJDTO blobj, final BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			String message = "Attempt to udpate a Blobj with invalid data.";
			LOG.warn(message);
			throw new IllegalArgumentException(message);
		}
		
		BlobJDTO udpatedBlobJ = blobJService.updateBlobJ(blobj);
		
		return udpatedBlobJ;
	}
	
	/**
	 * Delete the blobJ with the matching type
	 * @param id id of the BlobJ to delete
	 * @return true if the BlobJ has been deleted
	 */
	@DeleteMapping("/blobjs/{id}")
	public boolean deleteBlogJ (@PathVariable(name="id", required = true ) final Long id) {
		
		boolean isDeleted = blobJService.deleteBlobJ(id);
		
		return isDeleted;
	}
	
	/**
	 * Add a Tag to a BlobJ
 	 * @param blobId id of the BlobJ to update
 	 * @param tagId id of the tag to add
	 * @return updated BlobJ
	 */
	@PatchMapping("/blobjs/{blobId}/tag/{tagId}")
	public BlobJDTO addTagToBlobJ(@PathVariable(name="blobId", required = true ) final Long blobId,
			@PathVariable(name="tagId", required = true ) final Long tagId) {
		
		TagDTO tagToAdd = tagService.retrieveTagById(tagId);
		
		BlobJDTO updatedBlobJ = blobJService.addTagToBlobJ(blobId, tagToAdd);
		
		return updatedBlobJ;
	}
	
	/**
	 * Delete a Tag from a BlobJ
	 * @param IdInfo IdInfo containing the id of the BlobJ to update and the Id of the Tag to delete
	 * @param bindingResult bindingResult spring framework validation interface
	 * @return updated BlobJ
	 */
	@DeleteMapping("/blobjs/{blobId}/tag/{tagId}")
	public BlobJDTO deleteTagFromBlobJ(@PathVariable(name="blobId", required = true ) final Long blobId,
			@PathVariable(name="tagId", required = true ) final Long tagId) {
		
		TagDTO tagToDelete = tagService.retrieveTagById(tagId);
		
		BlobJDTO updatedBlobJ = blobJService.deleteTagFromBlobJ(blobId, tagToDelete);
		
		return updatedBlobJ;
	}
	
	/**
	 * Add a linked BlobJ to a BlobJ
 	 * @param IdInfo IdInfo containing the id of the BlobJ to update and the Id of the BlobJ to add
	 * @param bindingResult bindingResult spring framework validation interface
	 * @return updated BlobJ
	 */
	@PatchMapping("/blobjs/{blobId}/linked-blobj/{linkedId}")
	public BlobJDTO addLinkedBlobJToBlobJ(@PathVariable(name="blobId", required = true ) final Long blobId,
			@PathVariable(name="linkedId", required = true ) final Long linkedId) {
		
		if(blobId == linkedId) {
			String message = "A BlobJ cannot be linked to itself";
			LOG.info(message);
			throw new AapiEntityException(message.toString());
		}
		
		BlobJDTO updatedBlobJ = blobJService.addLinkedBlobJToBlobJ(blobId, linkedId);
		
		return updatedBlobJ;
	}
	
	/**
	 * Delete a linked BlobJ to a BlobJ.
 	 * @param IdInfo IdInfo containing the id of the BlobJ to update and the Id of the BlobJ to delete
	 * @param bindingResult bindingResult spring framework validation interface
	 * @return updated BlobJ
	 */
	@DeleteMapping("/blobjs/{blobId}/linked-blobj/{linkedId}")
	public BlobJDTO deleteLinkedBlobJFromBlobJ(@PathVariable(name="blobId", required = true ) final Long blobId,
			@PathVariable(name="linkedId", required = true ) final Long linkedId) {
		
		return blobJService.deleteLinkedBlobJFromBlobJ(blobId, linkedId);
		
	}
	
	/**
	 * TEST ENDPOINT
	 * TODO: delete this end point
	 * @param allParams
	 * @return
	 */
	@GetMapping("/blobjs/test")
	public String test(@RequestParam Map<String, String> allParams) {
		return "Parameters are " + allParams.entrySet();
	}
}
