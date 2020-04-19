package com.example.Aapi.cron;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.Aapi.dto.BlobJ;
import com.example.Aapi.service.BlobJService;

/**
 * Database cleaner cron, will delete unused or non-compliant object every 10 minutes
 * @author Aymeric NEUMANN
 *
 */
@Component
public class DatabaseCleaner {
	
	/** Reference to the log4j logger. */
	private static final Logger LOG = LogManager.getLogger();
	
	/** Reference to BlobJService. */
	@Autowired
	private BlobJService blobJService;
	
	/**
	 * Delete all BlobJ without a sign.
	 */
	@PostConstruct
	@Scheduled(cron = "0 */10 * * * *")
	public void deleteBlobJsWithoutSign() {
		Integer pageNumber = 0;
		
		LOG.info("Deleting non-compliant BlobJs");
		
		Integer numberOfPages = blobJService.retrieveAllBlobJs(pageNumber).getTotalPages();
		
		for(Integer i = 0; i < numberOfPages; i++ ) {
			Page<BlobJ> blobjs = blobJService.retrieveAllBlobJs(i);
			for(BlobJ b : blobjs) {
				if(b.getSign() == null || b.getSign().isEmpty()) {
					StringBuilder message = new StringBuilder();
					message.append("Deleted non-compliant BlobJs - no sign - id: ");
					message.append(b.getId());
					message.append(" Name: ");
					message.append(b.getName());
					LOG.info(message);
					blobJService.deleteBlobJ(b.getId());
				}
			}
			pageNumber++;
		}
		
		pageNumber = 0;
		LOG.info("Non-compliant BlobJs cleaning done");
		
		
	}

}
