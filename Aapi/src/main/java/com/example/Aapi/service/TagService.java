package com.example.Aapi.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Aapi.dao.TagRepository;
import com.example.Aapi.dto.Tag;

/**
 * Service for Tag
 * @author Aymeric NEUMANN
 */
@Service
public class TagService {
	
	/** Reference to the log4j logger. */
	private static final Logger LOG = LogManager.getLogger();
	
	/** Number of Tag return per page */
	private static final int NUM_OF_TAG_PER_PAGE = 50;
	
	/** Reference to the BlobJRepository */
	@Autowired
	TagRepository tagRepository;
	
	/**
	 * Save the Tag in the database.
	 * @param tag tag to save
	 * @return savedTag - Tag
	 */
	public Tag saveTag(final Tag tag) {
		
		Tag savedTag = tagRepository.save(tag);
		
		return savedTag;
	}

}
