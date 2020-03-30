package com.example.Aapi.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.Aapi.dao.BlobJRepository;
import com.example.Aapi.dto.BlobJ;
import com.example.Aapi.dto.BlobJType;
import com.example.Aapi.exception.AapiEntityException;

/**
 * Service for Blob
 * @author Aymeric NEUMANN
 */
@Service
public class BlobJService {
	
	/** Reference to the log4j logger. */
	private static final Logger LOG = LogManager.getLogger();
	
	/** Number of blobJ return per page */
	private static final int NUM_OF_BLOBJ_PER_PAGE = 50;
	
	/** Reference to the BlobJRepository */
	@Autowired
	BlobJRepository blobJRepository;

	/**
	 * Save the blobJ in the database.
	 * @param blobj blobJ to save
	 * @return savedBlobJ - BlobJ
	 */
	public BlobJ saveBlobj(final BlobJ blobj) {
		
		BlobJ savedBlob = blobJRepository.save(blobj);
		
		return savedBlob;
	}
	
	/**
	 * Save all BlobJ contained in the list
	 * @param blobj BloBJs to save
	 * @return the saved BlobJs
	 */
	public Iterable<BlobJ> saveAllBlobj(final List<BlobJ> blobj) {
		
		Iterable<BlobJ> savedBlob = blobJRepository.saveAll(blobj);
		
		return savedBlob;
	}

	/**
	 * Retrieve all BlobJ per page - 50 blobJ/page.
	 * @param pageNumber number of the page requested - 0 base count
	 * @return required page with 50 blobJ sorted by name - Page<BlobJ>
	 */
	public Page<BlobJ> retrieveAllBlobJs(Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber, NUM_OF_BLOBJ_PER_PAGE, Sort.by("name"));
		
		Page<BlobJ> blobjs = blobJRepository.findAll(pageable);
		
		return blobjs;
	}

	/**
	 * Retrieve the BlobJ with the matching id.
	 * @param id id of the BlobJ to retrieve
	 * @return found BlobJ - Optional<BlobJ>
	 */
	public Optional<BlobJ> retrieveById(Long id) {
		
		Optional<BlobJ> blobJToRetrieve = blobJRepository.findById(id);
		
		if(!blobJToRetrieve.isPresent()) {
			StringBuilder message = new StringBuilder();
			message.append("No BlobJ found with this id: ");
			message.append(id);
			LOG.warn(message);
			throw new AapiEntityException(message.toString());
		}
		
		return blobJToRetrieve;
	}
	
	/**
	 * Retrieve the BlobJswith a count strictly equal or greater than or less than requested count.
	 * @param count requested count
	 * @return list of BlobJ with matching conditions -Set<BlobJ>
	 */
	public Set<BlobJ> retrieveByCount(Integer count, String type) {
		
		Set<BlobJ> blobJsToRetrieve = null;
		
		switch(type) {
			case "exact":
				blobJsToRetrieve = blobJRepository.findByCount(count);
				break;
			case "min":
				blobJsToRetrieve = blobJRepository.findByCountGreaterThanEqual(count);
				break;
			case "max":
				blobJsToRetrieve = blobJRepository.findByCountLessThanEqual(count);
				break;
		}
		
		
		return blobJsToRetrieve;
	}

	/**
	 * Retrieve the BlobJswith a count between the minimum and maximum requested count.
	 * @param count requested count
	 * @return list of BlobJ with matching conditions - Set<BlobJ>
	 */
	public Set<BlobJ> retrieveByCountTranche(Integer minCount, Integer maxCount) {
		
		Set<BlobJ> blobJsToRetrieve = null;
		
		blobJsToRetrieve = blobJRepository.findByCountGreaterThanEqualAndCountLessThanEqualOrderByCountAsc(minCount, maxCount);
		
		return blobJsToRetrieve;
	}
	
	/**
	 * Retrieve the BlobJs with a name which contains the received name.
	 * @param name required name to find
	 * @return a list of BlobJ with a name which contains the received name - Set<BlobJ>
	 */
	public Set<BlobJ> retrieveByName(String name) {
		
		Set<BlobJ> blobJsToRetrieve = blobJRepository.findByNameContainingOrderByNameAsc(name);
		
		return blobJsToRetrieve;
	}
	
	/**
	 * Retrieve all BlobJs with a matching type.
	 * @param type type of BlobJ to retrieve
	 * @return all BlobJs with a matching type - Set<BlobJ>
	 */
	public Set<BlobJ> retrieveByType(BlobJType type) {
		
		Set<BlobJ> blobJsToRetrieve = blobJRepository.findByTypeOrderByNameAsc(type);
		
		return blobJsToRetrieve;
	}

	/**
	 * Update the BlobJ with the matching id.
	 * @param blobj new BlobJ data
	 */
	public BlobJ updateBlobJ(BlobJ blobj) {
		
		BlobJ udpatedBlob = null;
				
		Integer updatedBlobJ = blobJRepository.updateBlobJ(blobj.getId(), blobj.getName(), blobj.getCount(), blobj.getType());
		
		if(updatedBlobJ == 1) {
			udpatedBlob = blobj;
		} else {
			StringBuilder message = new StringBuilder();
			message.append("BlobJ has not been updated.");
			message.append(" BlobJ id: ");
			message.append(blobj.getId());
			LOG.warn(message);
		}
		
		return udpatedBlob;
	}
	
	/**
	 * Delete the blobJ with the matching type
	 * @param id id of the BLobJ to delete
	 * @return true if the BlobJ has been deleted
	 */
	public boolean deleteBlobJ(Long id) {
		
		boolean isDeleted = false;
		
		if(!blobJRepository.existsById(id)) {
			StringBuilder message = new StringBuilder();
			message.append("No BlobJ found with this id: ");
			message.append(id);
			LOG.warn(message);
			throw new AapiEntityException(message.toString());
		}
		
		blobJRepository.deleteById(id);
		
		boolean hasBeenDeleted = blobJRepository.existsById(id);
		
		isDeleted = !hasBeenDeleted;
				
		return isDeleted;
	}
}
