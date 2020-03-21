package com.example.Aapi.service;

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
	public Page<BlobJ> retireveAllBlobJs(Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber, NUM_OF_BLOBJ_PER_PAGE, Sort.by("name"));
		
		Page<BlobJ> blobjs = blobJRepository.findAll(pageable);
		
		return blobjs;
	}

}
