package com.example.Aapi.dto;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Database entity for Tag
 * @author Aymeric NEUMANN
 */
@Entity
public class Tag {
	
	/** Id of the Tag.*/
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	
	/** Tag name.*/
    @NotNull
    @NotBlank
    private String name;
    
    /** Many to may relationship with BlobJ. */
    @JsonIgnore
    @ManyToMany(mappedBy = "tags")
    private Set<BlobJ> blobjs;
    
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
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

}
