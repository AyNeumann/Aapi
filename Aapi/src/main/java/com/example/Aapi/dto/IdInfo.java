package com.example.Aapi.dto;

import javax.validation.constraints.NotNull;

/**
 * DTO to contains one Id of a BlobJ and one ID for another object which 
 * will be related to this BlobJ like a Tag id or another BlobJ id. 
 * Avoid to use URL parameter for PUT request.
 * @author Aymeric NEUMANN
 *
 */
public class IdInfo {
	
	/** Id of the BlobJ to update. */
	@NotNull
	private Long blobJId;
	
	/** Id of the object to add or delete from the BlobJ. */
	@NotNull
	private Long objectId;

	/**
	 * @return the blobJId
	 */
	public Long getBlobJId() {
		return blobJId;
	}

	/**
	 * @param blobJId the blobJId to set
	 */
	public void setBlobJId(Long blobJId) {
		this.blobJId = blobJId;
	}

	/**
	 * @return the objectId
	 */
	public Long getObjectId() {
		return objectId;
	}

	/**
	 * @param objectId the objectId to set
	 */
	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}
	
}
