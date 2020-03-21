package com.example.Aapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Aapi.dto.BlobJ;
import com.example.Aapi.service.BlobJService;

/**
 * Manage request about blob.
 * @author Aymeric Neumann
 *
 */
@RestController
@RequestMapping("/blobj")
public class BlobJController {
	
	@Autowired
	private BlobJService blobJService;
	
	@PostMapping("create")
	public BlobJ createBlob(@RequestBody final BlobJ blobj, final BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			String message = "Attempt to create a Blobj with invalid data";
			// TODO setup logger
			System.out.println(message);
			throw new IllegalArgumentException("Attempt to create a Blobj with invalid data");
		}
		
		BlobJ savedBlobJ = blobJService.createBlobj(blobj);
		
		return savedBlobJ;
	}

}
