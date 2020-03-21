package com.example.Aapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Aapi.dao.BlobRepository;

/**
 * Service for Blob
 * @author Aymeric Neumann
 */
@Service
public class BlobService {
	
	@Autowired
	BlobRepository blobRepository;

}
