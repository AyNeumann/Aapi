package com.example.Aapi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.Aapi.dao.BlobJRepository;
import com.example.Aapi.dto.BlobJDTO;
import com.example.Aapi.entity.BlobJ;
import com.example.Aapi.mapper.BlobJMapper;

@ExtendWith(MockitoExtension.class)
public class BlobJServiceUnitTest {
	
	/** Reference to the log4j logger. */
	private static final Logger LOG = LogManager.getLogger();
	
	@Mock
	private BlobJRepository blobJRepository;
	
	@Mock
	private BlobJMapper mapper;
	
	@InjectMocks
	private BlobJService blobJService;
	
	@Test
	void shouldReturnListOfSavedBlobJ() {
		BlobJDTO blob = new BlobJDTO();
		blob.setName("TheTest Blob");
		blob.setSign("TEST");
		blob.setCount(4);
		blob.setRank(9999);
		List<BlobJDTO> blobList = new ArrayList<>();
		blobList.add(blob);
		
		BlobJ blobEntity = new BlobJ();
		blobEntity.setName("TheTest Blob");
		blobEntity.setSign("TEST");
		blobEntity.setCount(4);
		blobEntity.setRank(9999);
		List<BlobJ> blobEntityList = new ArrayList<>();
		blobEntityList.add(blobEntity);
		
		
		LOG.debug(blobList.toString());
		
		when(mapper.convertBlobJDTOListToBlobJEntityList(blobList)).thenReturn(blobEntityList);
		
		when(blobJRepository.saveAll(blobEntityList)).thenReturn(blobEntityList);
		
		when(mapper.convertBlobJEntityListToBlobJDTOList(blobEntityList)).thenReturn(blobList);
		
		List<BlobJDTO> savedBlobList = blobJService.saveAllBlobj(blobList);
		
		LOG.debug(savedBlobList.toString());
		
		assertThat(savedBlobList).isEqualTo(blobList);
	}

}
