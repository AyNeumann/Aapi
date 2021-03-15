package com.example.Aapi.service;

import java.util.ArrayList;
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
import com.example.Aapi.helper.StringFormatHelper;

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
	
	/** Reference to the Tag Repository */
	@Autowired
	TagRepository tagRepository;
		
	/**
	 * Save the Tag in the database.
	 * @param tag tag to save
	 * @return savedTag - Tag
	 */
	public Tag saveTag(final Tag tag) {
		
		Tag tagToSave = formatTagData(tag);
		
		checkIfTagAlreadyExist(tag.getName());
				
		Tag savedTag = tagRepository.save(tagToSave);
		
		return savedTag;
	}
	
	/**
	 * Save all Tags contained in the list.
	 * @param tag Tags to save
	 * @return the saved Tags
	 */
	public Iterable<Tag> saveAllTag(final List<Tag> tags) {
		
		List<Tag> tagsToSave = new ArrayList<Tag>();
		
		for(Tag t : tags) {
			Tag tagToSave = formatTagData(t);
			checkIfTagAlreadyExist(t.getName());
			tagsToSave.add(tagToSave);
		}
		
		Iterable<Tag> savedTag = tagRepository.saveAll(tagsToSave);
		
		return savedTag;
	}
	
	/**
	 * Retrieve all tag from the database and return paginated data - 50 tag/page.
	 * @param pageNumber number of the page requested - 0 base count
	 * @return required page of Tag - Page<Tag>
	 */
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
	public Optional<Tag> retrieveTagById(final Long id) {
		
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
	public Set<Tag> findTagByName(final String name) {
		
		Set<Tag> tagsToRetrieve = tagRepository.findByNameContainingOrderByNameAsc(name);
		
		return tagsToRetrieve;
		
	}
	
	public Tag retrieveTagByName(final String name) {
		
		String nametoRetrieve = StringFormatHelper.capitalize(name);
				
		Optional<Tag> tagToRetrieve = tagRepository.findByName(nametoRetrieve);
		
		if(!tagToRetrieve.isPresent()) {
			StringBuilder message = new StringBuilder();
			message.append("No Tag found with this name: ");
			message.append(nametoRetrieve);
			LOG.warn(message);
			throw new AapiEntityException(message.toString());
		}
		
		return tagToRetrieve.get();
	}
	
	/**
	 * Update the Tag with the matching id.
	 * @param tag new Tag data
	 * @return updated tag or null if tag hasn't been updated - Tag
	 */
	public Tag updateTag (final Tag tag) {
		
		Tag updatedTag = null;
		
		Integer tagUpdatedStatus = tagRepository.updateTag(tag.getId(), tag.getName());
		
		if(tagUpdatedStatus == 1) {
			updatedTag = tag;
		} else {
			StringBuilder message = new StringBuilder();
			message.append("Tag has not been updated.");
			message.append(" Tag id: ");
			message.append(tag.getId());
			LOG.warn(message);
		}
				
		return updatedTag;
	}
	
	/**
	 * Delete the Tag with the matching type.
	 * @param id id of the Tag to delete
	 * @return true if the Tag has been deleted
	 */
	public boolean deleteTag (final Long id) {
		
		boolean isDeleted = false;
		
		if(!tagRepository.existsById(id)) {
			StringBuilder message = new StringBuilder();
			message.append("No Tag found with this id: ");
			message.append(id);
			LOG.warn(message);
			throw new AapiEntityException(message.toString());
		}
		
		tagRepository.deleteById(id);
		
		boolean hasBeenDeleted = tagRepository.existsById(id);
		
		isDeleted = !hasBeenDeleted;
				
		return isDeleted;
	}
	
	private Tag formatTagData(Tag tagToFormat) {
		
		if(!Character.isUpperCase(tagToFormat.getName().charAt(0))) {
			String formattedName = StringFormatHelper.capitalize(tagToFormat.getName());
			tagToFormat.setName(formattedName);
		}
		
		return tagToFormat;
	}

	/**
	 * Check if a tag with the same name already exist in the database.
	 * Throws AapiEntityException if a Tag with the same name is found
	 * @param name name of the Tag to check
	 */
	private void checkIfTagAlreadyExist(String name) {
		
		if(tagRepository.findByName(name).isPresent()) {
			throw new AapiEntityException("A Tag already exist with the name: " + name);
		}

	}
}
