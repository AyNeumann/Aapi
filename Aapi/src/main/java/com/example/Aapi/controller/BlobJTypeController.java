package com.example.Aapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Aapi.dto.BlobJType;
import com.example.Aapi.service.BlobJTypeService;

/**
 * Manage request about blob types.
 * @author Aymeric NEUMANN
 *
 */
@RestController
@RequestMapping("/type/")
public class BlobJTypeController {
	
	@Autowired
	private BlobJTypeService blobJTypeService;
	
	/**
	 * Retrieve all BlobJ types
	 * @return all BlobJ types - List<BlobJType>
	 */
	@GetMapping("all")
	public List<BlobJType> retrieveAllTypes() {
		
		List<BlobJType> types = blobJTypeService.getAllTypes();
				
		return types;
	}
}
