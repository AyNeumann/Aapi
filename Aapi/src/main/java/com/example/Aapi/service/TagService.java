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

import com.example.Aapi.dao.TagRepository;
import com.example.Aapi.dto.Tag;
import com.example.Aapi.exception.AapiEntityException;

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
	
	/**
	 * Save all Tags contained in the list
	 * @param tag Tags to save
	 * @return the saved Tags
	 */
	public Iterable<Tag> saveAllTag(final List<Tag> tag) {
		
		Iterable<Tag> savedTag = tagRepository.saveAll(tag);
		
		return savedTag;
	}
	
	public Page<Tag> retrieveAllTags(Integer pageNumber) {
		
		Pageable pageable = PageRequest.of(pageNumber, NUM_OF_TAG_PER_PAGE, Sort.by("name"));
		
		Page<Tag> tags = tagRepository.findAll(pageable);
		
		return tags;
	}
	
	/**
	 * Retrieve the Tag with the matching id.
	 * @param id id of the Tag to retrieve
	 * @return found Tag - Optional<Tag>
	 */
	public Optional<Tag> retrieveById(final Long id) {
		
		Optional<Tag> tagToRetrieve = tagRepository.findById(id);
		
		if(!tagToRetrieve.isPresent()) {
			StringBuilder message = new StringBuilder();
			message.append("No Tag found with this id: ");
			message.append(id);
			LOG.warn(message);
			throw new AapiEntityException(message.toString());
		}
		
		return tagToRetrieve;
	}
	
	/**
	 * Retrieve the Tags with a name which contains the received name. 
	 * @param name required name to find
	 * @return a list of Tags with a name which contains the received name - Set<Tag>
	 */
	public Set<Tag> retrieveByName(final String name) {
		
		Set<Tag> tagToRetrieve = tagRepository.findByNameContainingOrderByNameAsc(name);
		
		return tagToRetrieve;
		
	}

}
