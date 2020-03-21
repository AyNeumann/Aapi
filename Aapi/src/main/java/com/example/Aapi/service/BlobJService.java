package com.example.Aapi.service;

import java.util.Optional;

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

/**
 * Service for Blob
 * @author Aymeric Neumann
 */
@Service
public class BlobJService {
	
	/** Reference to the log4j logger. */
	private static final Logger LOG = LogManager.getLogger();
	
	/** Number of blobj return per page */
	private static final int NUM_OF_BLOBJ_PER_PAGE = 50;
	
	/** Reference to the BlobJRepository */
	@Autowired
	BlobJRepository blobJRepository;

	/**
	 * Save the blobJ in the database
	 * @param blobj blobJ to save
	 * @return savedBlobJ
	 */
	public BlobJ saveBlobj(final BlobJ blobj) {
		
		BlobJ savedBlob = blobJRepository.save(blobj);
		
		return savedBlob;
	}

	/**
	 * Retrieve all BlobJ per page - 50 blobJ/page
	 * @param pageNumber number of the page requested - 0 base count
	 * @return required page with 50 blobJ sorted by name
	 */
	public Page<BlobJ> retrieveAllBlobJs(Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber, NUM_OF_BLOBJ_PER_PAGE, Sort.by("name"));
		
		Page<BlobJ> blobjs = blobJRepository.findAll(pageable);
		
		return blobjs;
	}

	public Optional<BlobJ> retrieveById(Long id) {
		
		Optional<BlobJ> blobJToRetrieve = blobJRepository.findById(id);
		
		if(!blobJToRetrieve.isPresent()) {
			String message = "No BlobJ found with this id";
			LOG.warn(message);
		}
		
		return blobJToRetrieve;
	}

}
