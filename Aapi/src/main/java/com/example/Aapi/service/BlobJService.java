package com.example.Aapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Aapi.dao.BlobJRepository;
import com.example.Aapi.dto.BlobJ;

/**
 * Service for Blob
 * @author Aymeric Neumann
 */
@Service
public class BlobJService {
	
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

}
