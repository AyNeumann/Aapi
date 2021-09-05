package com.example.Aapi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.example.Aapi.dao.TagRepository;
import com.example.Aapi.dto.TagDTO;
import com.example.Aapi.entity.Tag;
import com.example.Aapi.mapper.TagMapper;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class TagServiceUnitTest {
	
	/** Reference to the log4j logger. */
	private static final Logger LOG = LogManager.getLogger();

	@Mock
	private TagRepository tagRepository;
	
	@Mock
	private TagMapper tagMapper;
	
	@InjectMocks
	private TagService tagService;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldSaveTags() {
		List<TagDTO> mockTagDTOList = Mockito.spy(new ArrayList<TagDTO>());
		List<Tag> mockTagList = Mockito.spy(new ArrayList<Tag>());
		
		TagDTO aMockTagDTO = new TagDTO();
		aMockTagDTO.setName("ATag");
		
		mockTagDTOList.add(aMockTagDTO);
		
		List<Tag> tags = tagMapper.convertTagDTOListToTagEntityList(mockTagDTOList);
		LOG.info("*** tags = {}", tags.toString());
		
		mockTagList.addAll(tags);
		
		
		when(tagRepository.saveAll(mockTagList)).thenReturn(mockTagList);
		
		LOG.info("*** mockTagDTOList = {}", mockTagDTOList.toString());
		LOG.info("*** mockTagList = {}", mockTagList.toString());
		
		List<TagDTO> savedTagDTO = tagService.saveTags(mockTagDTOList);
		
		LOG.info("*** savedTagDTO = {}", savedTagDTO.toString());
		
		assertThat(mockTagDTOList).isEqualTo(savedTagDTO);
	}
}
