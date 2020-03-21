package com.example.Aapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Aapi.service.BlobService;

/**
 * Manage request about blob.
 * @author Aymeric Neumann
 *
 */
@RestController
@RequestMapping("/user")
public class BlobController {
	
	@Autowired
	private BlobService blobService;

}
