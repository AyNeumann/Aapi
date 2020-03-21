package com.example.Aapi.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
	
	/** Reference to the log4j logger. */
	private static final Logger LOG = LogManager.getLogger();
	
	/** Reference to BlobJService. */
	@Autowired
	private BlobJService blobJService;
	
	/**
	 * Create a blobj and return created blobj.
	 * @param blobj blobj to create
	 * @param bindingResult spring framework validation interface
	 * @return the created blobj
	 */
	@PostMapping("save")
	public BlobJ saveBlob(@RequestBody final BlobJ blobj, final BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			String message = "Attempt to create a Blobj with invalid data";
			LOG.warn(message);
			throw new IllegalArgumentException("Attempt to create a Blobj with invalid data");
		}
		
		BlobJ savedBlobJ = blobJService.saveBlobj(blobj);
		
		return savedBlobJ;
	}

}
