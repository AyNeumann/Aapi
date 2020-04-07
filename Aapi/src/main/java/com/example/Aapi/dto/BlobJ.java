package com.example.Aapi.dto;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Database entity for Blob
 * @author Aymeric NEUMANN
 */
@Entity
public class BlobJ {
	
	/** Id of the blob.*/
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	
	/** Blob name.*/
    @NotNull
    @NotBlank
    private String name;
    
    /** Blob count. */
    @NotNull
    private Integer count;
    
    /** Blob rank. */
    @NotNull
    private Integer rank;
    
	/** Blob type. */
    @NotNull
    @Enumerated(EnumType.STRING)
    private BlobJType type;
    
    /** Many to many relationship with tags. */
    @ManyToMany(cascade = CascadeType.MERGE)
    private Set<Tag> tags;
    
    /** Many to many relationship with linkedBlobJ. */
    @JsonIgnoreProperties("linkedBlobJ")
    @ManyToMany(cascade = CascadeType.MERGE)
    private Set<BlobJ> linkedBlobJ;
    
	/*
     * Override method toSTring
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
	public Set<Tag> getTags() {
		return tags;
	}

	/**
	 * @param tags the tags to set
	 */
	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}
	
	/**
	 * @return the linkedBlobJ
	 */
	public Set<BlobJ> getLinkedBlobJ() {
		return linkedBlobJ;
	}

	/**
	 * @param linkedBlobJ the linkedBlobJ to set
	 */
	public void setLinkedBlobJ(Set<BlobJ> linkedBlobJ) {
		this.linkedBlobJ = linkedBlobJ;
	}

}
