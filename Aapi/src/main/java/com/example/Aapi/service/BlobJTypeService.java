package com.example.Aapi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Aapi.dto.BlobJType;

/**
 * Service for Blob types
 * @author Aymeric NEUMANN
 */
@Service
public class BlobJTypeService {

	/**
	 * Retrieve all BlobJ types
	 * @return all BlobJ types - List<BlobJType>
	 */
	public List<BlobJType> getAllTypes() {
		
		List<BlobJType> types = new ArrayList<BlobJType>();
		
		for (BlobJType type : BlobJType.values()) { 
			types.add(type);
		}
				
		return types;
	}

}
