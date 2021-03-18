package com.example.Aapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.Aapi.dao.BlobJRepository;
import com.example.Aapi.dto.BlobJDTO;
import com.example.Aapi.dto.TagDTO;
import com.example.Aapi.entity.BlobJ;
import com.example.Aapi.entity.BlobJType;
import com.example.Aapi.exception.AapiEntityException;
import com.example.Aapi.helper.StringFormatHelper;
import com.example.Aapi.mapper.BlobJMapper;

/**
 * Service for Blob
 * 
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
	private BlobJRepository blobJRepository;

	/** Reference to the BlobJMapper */
	@Autowired
	private BlobJMapper blobJMapper;

	/**
	 * Save the blobJ in the database.
	 * 
	 * @param blobj blobJ to save
	 * @return savedBlobJDTO a DTO of the saved BlobJ - BlobJDTO
	 */
	public BlobJDTO saveBlobj(final BlobJDTO blobj) {

		checkIfBlobJAlreadyExist(blobj.getName());

		BlobJDTO blobJToSave = checkAndFormatBlobJData(blobj);

		BlobJ entity = blobJMapper.blobJDTOtoBlobJEntity(blobJToSave);

		BlobJ savedBlob = blobJRepository.save(entity);
		LOG.info("Saved Blob Entity: " + savedBlob);

		BlobJDTO savedBlobDTO = blobJMapper.blobJEntityToBlobJDTO(savedBlob);
		LOG.info("Saved Blob DTO: " + savedBlobDTO);

		return savedBlobDTO;
	}

	/**
	 * Save all BlobJ contained in the list
	 * 
	 * @param blobjs BloBJs to save
	 * @return the a set a saved BlobJDTO
	 */
	public List<BlobJDTO> saveAllBlobj(final List<BlobJDTO> blobjs) {

		List<BlobJDTO> blobjsDTO = new ArrayList<BlobJDTO>();

		for (BlobJDTO b : blobjs) {
			checkIfBlobJAlreadyExist(b.getName());
			checkAndFormatBlobJData(b);
			blobjsDTO.add(b);
		}

		List<BlobJ> blobjsToSave = blobJMapper.convertBlobJDTOListToBlobJEntityList(blobjsDTO);

		blobJRepository.saveAll(blobjsToSave);

		List<BlobJDTO> savedBlobDTO = blobJMapper.convertBlobJEntityListToBlobJDTOList(blobjsToSave);

		return savedBlobDTO;
	}

	/**
	 * Retrieve all BlobJ per page - 50 blobJ/page.
	 * 
	 * @param pageNumber number of the page requested - 0 base count
	 * @return required page with 50 blobJDTO sorted by rank - Page<BlobJ>
	 */
	public Page<BlobJDTO> retrieveAllBlobJs(Integer pageNumber) {

		Pageable pageable = PageRequest.of(pageNumber, NUM_OF_BLOBJ_PER_PAGE, Sort.by("rank"));

		Page<BlobJ> blobjsEntities = blobJRepository.findAll(pageable);

		List<BlobJDTO> blobjs = blobJMapper.convertBlobJEntityListToBlobJDTOList(blobjsEntities.getContent());

		return new PageImpl<BlobJDTO>(blobjs, pageable, blobjsEntities.getTotalElements());
	}

	/**
	 * Retrieve the BlobJ with the matching id.
	 * 
	 * @param id id of the BlobJ to retrieve
	 * @return found BlobJ - BlobJDTO
	 */
	public BlobJDTO retrieveById(Long id) {

		BlobJ blobJToRetrieve = blobJRepository.findById(id)
				.orElseThrow(() -> new AapiEntityException("No BlobJ found with this id: " + id));

		BlobJDTO retrievedBlobJDTO = blobJMapper.blobJEntityToBlobJDTO(blobJToRetrieve);

		return retrievedBlobJDTO;
	}

	/**
	 * Retrieve the BlobJswith a count strictly equal or greater than or less than
	 * requested count.
	 * 
	 * @param count requested count
	 * @return list of BlobJ with matching conditions -Set<BlobJ>
	 */
	public Set<BlobJ> retrieveByCount(Integer count, String type) {

		Set<BlobJ> blobJsToRetrieve = null;

		switch (type) {
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
	 * Retrieve the BlobJswith a count between the minimum and maximum requested
	 * count.
	 * 
	 * @param count requested count
	 * @return list of BlobJ with matching conditions - Set<BlobJ>
	 */
	public Set<BlobJ> retrieveByCountTranche(Integer minCount, Integer maxCount) {

		Set<BlobJ> blobJsToRetrieve = null;

		blobJsToRetrieve = blobJRepository.findByCountGreaterThanEqualAndCountLessThanEqualOrderByCountAsc(minCount,
				maxCount);

		return blobJsToRetrieve;
	}

	/**
	 * Retrieve the BlobJs with a name which contains the received name.
	 * 
	 * @param name required name to find
	 * @return a list of BlobJ with a name which contains the received name -
	 *         Set<BlobJ>
	 */
	public Set<BlobJ> retrieveByName(String name) {

		Set<BlobJ> blobJsToRetrieve = blobJRepository.findByNameContainingOrderByNameAsc(name);

		return blobJsToRetrieve;
	}

	/**
	 * Retrieve all BlobJs with a matching type.
	 * 
	 * @param type type of BlobJ to retrieve
	 * @return all BlobJs with a matching type - Set<BlobJ>
	 */
	public Set<BlobJ> retrieveByType(BlobJType type) {

		Set<BlobJ> blobJsToRetrieve = blobJRepository.findByTypeOrderByNameAsc(type);

		return blobJsToRetrieve;
	}

	/**
	 * Update the BlobJ with the matching id.
	 * 
	 * @param blobj new BlobJ data
	 */
	public BlobJDTO updateBlobJ(BlobJDTO blobj) {

		BlobJDTO updatedBlob = null;

		Integer blobJUpdateStatus = blobJRepository.updateBlobJ(blobj.getId(), blobj.getName(), blobj.getCount(),
				blobj.getType());

		if (blobJUpdateStatus == 1) {
			updatedBlob = blobj;
		} else {
			StringBuilder message = new StringBuilder();
			message.append("BlobJ has not been updated.");
			message.append(" BlobJ id: ");
			message.append(blobj.getId());
			LOG.warn(message);
		}

		return updatedBlob;
	}

	/**
	 * Delete the blobJ with the matching type.
	 * 
	 * @param id id of the BLobJ to delete
	 * @return true if the BlobJ has been deleted
	 */
	public boolean deleteBlobJ(Long id) {

		boolean isDeleted = false;

		if (!blobJRepository.existsById(id)) {
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

	/**
	 * Add a Tag to a BlobJ and save it in the database
	 * 
	 * @param blobjId  blobjId id of the BlobJ to add a tag to
	 * @param tagToAdd tag to add
	 * @return updated BlobJ
	 */
	public BlobJDTO addTagToBlobJ(Long blobjId, TagDTO tagToAdd) {

		BlobJDTO retrievedBlobJ = retrieveById(blobjId);

		Set<TagDTO> blobJTags = retrievedBlobJ.getTags();

		if (blobJTags.contains(tagToAdd)) {
			StringBuilder message = new StringBuilder();
			message.append("The BlobJ: ");
			message.append(retrievedBlobJ.getId());
			message.append(" ");
			message.append(retrievedBlobJ.getName());
			message.append(" already has the tag: ");
			message.append(tagToAdd.getName());
			LOG.info(message);
			throw new AapiEntityException(message.toString());
		}

		blobJTags.add(tagToAdd);

		retrievedBlobJ.setTags(blobJTags);

		BlobJ entity = blobJMapper.blobJDTOtoBlobJEntity(retrievedBlobJ);

		blobJRepository.save(entity);

		return retrievedBlobJ;
	}

	/**
	 * Delete a Tag to a BlobJ
	 * 
	 * @param blobjId     blobjId id of the BlobJ to delete a tag to
	 * @param tagToDelete tagId id of the tag to delete
	 * @return updated BlobJ
	 */
	public BlobJDTO deleteTagFromBlobJ(Long blobjId, TagDTO tagToDelete) {

		BlobJDTO retrievedBlobJ = retrieveById(blobjId);

		Set<TagDTO> blobJTags = retrievedBlobJ.getTags();

		if (!blobJTags.contains(tagToDelete)) {
			StringBuilder message = new StringBuilder();
			message.append("The BlobJ: ");
			message.append(retrievedBlobJ.getId());
			message.append(" - ");
			message.append(retrievedBlobJ.getName());
			message.append(" doesn't have the tag: ");
			message.append(tagToDelete.getName());
			LOG.info(message);
			throw new AapiEntityException(message.toString());
		}

		blobJTags.remove(tagToDelete);

		retrievedBlobJ.setTags(blobJTags);

		BlobJ entity = blobJMapper.blobJDTOtoBlobJEntity(retrievedBlobJ);

		blobJRepository.save(entity);

		return retrievedBlobJ;
	}

	/**
	 * Add a linked BlobJ to a BlobJ.
	 * 
	 * @param blobjId    id of the BlobJ to add the linked BlobJ to
	 * @param lnkBlobjId id of the BlobJ to add
	 * @return updated BlobJ
	 */
	public BlobJDTO addLinkedBlobJToBlobJ(Long blobjId, Long lnkBlobjId) {

		BlobJDTO retrievedBlobJ = retrieveById(blobjId);
		BlobJDTO lnkRetrievedBlobJ = retrieveById(lnkBlobjId);

		if (retrievedBlobJ.getLinkedBlobJ().contains(lnkRetrievedBlobJ)) {
			StringBuilder message = new StringBuilder();
			message.append("The BlobJ: ");
			message.append(retrievedBlobJ.getId());
			message.append(" ");
			message.append(retrievedBlobJ.getName());
			message.append(" already has the linked BlobJ: ");
			message.append(lnkRetrievedBlobJ.getName());
			LOG.info(message);
			throw new AapiEntityException(message.toString());
		}

		retrievedBlobJ.getLinkedBlobJ().add(lnkRetrievedBlobJ);

		BlobJ entity = blobJMapper.blobJDTOtoBlobJEntity(retrievedBlobJ);

		blobJRepository.save(entity);

		return retrievedBlobJ;
	}

	/**
	 * Delete a linked BlobJ to a BlobJ.
	 * 
	 * @param blobjId    id of the BlobJ to delete the linked BlobJ from
	 * @param lnkBlobjId id of the BlobJ to delete
	 * @return updated BlobJ
	 */
	public BlobJDTO deleteLinkedBlobJFromBlobJ(Long blobjId, Long lnkBlobjId) {

		BlobJDTO retrievedBlobJ = retrieveById(blobjId);
		BlobJDTO lnkRetrievedBlobJ = retrieveById(lnkBlobjId);

		if (!retrievedBlobJ.getLinkedBlobJ().contains(lnkRetrievedBlobJ)) {
			StringBuilder message = new StringBuilder();
			message.append("The BlobJ: ");
			message.append(retrievedBlobJ.getId());
			message.append(" ");
			message.append(retrievedBlobJ.getName());
			message.append(" doesn't have for linked BlobJ: ");
			message.append(lnkRetrievedBlobJ.getName());
			LOG.info(message);
			throw new AapiEntityException(message.toString());
		}

		retrievedBlobJ.getLinkedBlobJ().remove(lnkRetrievedBlobJ);

		BlobJ entity = blobJMapper.blobJDTOtoBlobJEntity(retrievedBlobJ);

		blobJRepository.save(entity);

		return retrievedBlobJ;
	}

	/**
	 * Workaround to avoid lazy loading exception in the DBcleaner
	 * deleteBlobJsWithoutSign method TODO: find a better solution
	 * 
	 * @param pageNumber
	 * @return page of blobJ entity
	 */
	public Page<BlobJ> retrieveAllBlobJsEntity(Integer pageNumber) {

		Pageable pageable = PageRequest.of(pageNumber, NUM_OF_BLOBJ_PER_PAGE, Sort.by("rank"));

		Page<BlobJ> blobjsEntities = blobJRepository.findAll(pageable);

		return blobjsEntities;
	}

	/**
	 * Check if a BlobJ with the same name already exist in the database. Throws
	 * AapiEntityException if a BlobJ with the same name is found
	 * 
	 * @param name name of the BlobJ to check
	 */
	private void checkIfBlobJAlreadyExist(String name) {
		Set<BlobJ> blobJWithSimilarNames = retrieveByName(name);

		for (BlobJ b : blobJWithSimilarNames) {
			if (b.getName().trim().equalsIgnoreCase(name.trim())) {
				StringBuilder message = new StringBuilder();
				message.append("A BlobJ already exist with the name: ");
				message.append(name);
				LOG.warn(message);
				throw new AapiEntityException(message.toString());
			}
		}
	}

	/**
	 * Format BlobJ data.
	 * 
	 * @param blobJToFormat BlobJ data to format
	 * @return formatted BlobJ - BlobJ
	 */
	private BlobJDTO checkAndFormatBlobJData(BlobJDTO blobJToFormat) {

		// regex : [a-zA-Z\\-]*\\s*Blob$

		String formattedName = StringFormatHelper.capitalizeFully(blobJToFormat.getName());

		if (!formattedName.matches("[a-zA-Z\\-]*\\s*Blob$")) {
			StringBuilder message = new StringBuilder();
			message.append("This BlobJ don't have a correct name: ");
			message.append(formattedName);
			message.append(". BlobJ name must contain a word and then end by 'Blob'.");
			LOG.warn(message);
			throw new AapiEntityException(message.toString());
		}

		blobJToFormat.setName(formattedName);

		return blobJToFormat;
	}
}
