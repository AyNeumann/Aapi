package com.example.Aapi.dto;

import java.util.Set;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TagDTO {
	
	/** Id of the Tag. */
	@Id
    private Long id;
	
	/** Tag name. */
    @NotNull
    @NotBlank
    private String name;
    
	/** Many to may relationship with BlobJ. */
    @JsonIgnore
    private Set<BlobJDTO> blobjs;
    
	/*
     * Override method toSTring.
     */
    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
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
	 * @return the blobjs
	 */
	public Set<BlobJDTO> getBlobjs() {
		return blobjs;
	}

	/**
	 * @param blobjs the blobjs to set
	 */
	public void setBlobjs(Set<BlobJDTO> blobjs) {
		this.blobjs = blobjs;
	}

}
