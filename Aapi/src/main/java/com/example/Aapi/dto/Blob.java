package com.example.Aapi.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Database entity for Blob
 * @author Aymeric Neumann
 */
@Entity
public class Blob {
	
	/** Id of the blob.*/
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	
	/** Blob name.*/
    @NotNull
    @NotBlank
    private String name;
    
    /*
     * Override method toSTring
     */
    @Override
    public String toString() {
        return "Blob{" +
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

}
