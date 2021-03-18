package com.example.Aapi.dto;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.example.Aapi.entity.BlobJType;

public class BlobJDTO {

	/** Id of the blob.*/
    private Long id;
	
	/** Blob name.*/
    @NotNull
    @NotBlank
    private String name;
    
    /** Blob associated sign */
    private String sign;
    
    /** Blob count. */
    @NotNull
    private Integer count;
    
    /** Blob rank. */
    @NotNull
    private Integer rank;
    
	/** Blob type. */
    @NotNull
    private BlobJType type;
    
	/** Many to many relationship with tags. */
    private Set<TagDTO> tags;
    
    /** Many to many relationship with linkedBlobJ. */
    private Set<BlobJDTO> linkedBlobJ;
    
	/*
     * Override method toString
     */
    @Override
    public String toString() {
        return "BlobJ{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", count='" + count + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
    /**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the sign
	 */
	public String getSign() {
		return sign;
	}

	/**
	 * @param sign the sign to set
	 */
	public void setSign(String sign) {
		this.sign = sign;
	}
	
	/**
	 * @return the count
	 */
	public Integer getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(Integer count) {
		this.count = count;
	}
	
	/**
	 * @return the rank
	 */
	public Integer getRank() {
		return rank;
	}

	/**
	 * @param rank the rank to set
	 */
	public void setRank(Integer rank) {
		this.rank = rank;
	}
		
	/**
	 * @return the type
	 */
	public BlobJType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(BlobJType type) {
		this.type = type;
	}
	
	/**
	 * @return the tags
	 */
	public Set<TagDTO> getTags() {
		return tags;
	}

	/**
	 * @param tags the tags to set
	 */
	public void setTags(Set<TagDTO> tags) {
		this.tags = tags;
	}
	
	/**
	 * @return the linkedBlobJ
	 */
	public Set<BlobJDTO> getLinkedBlobJ() {
		return linkedBlobJ;
	}

	/**
	 * @param linkedBlobJ the linkedBlobJ to set
	 */
	public void setLinkedBlobJ(Set<BlobJDTO> linkedBlobJ) {
		this.linkedBlobJ = linkedBlobJ;
	}
}
