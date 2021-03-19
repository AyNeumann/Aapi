package com.example.Aapi.mapper;

import java.util.List;
import java.util.Set;

import org.mapstruct.Mapper;

import com.example.Aapi.dto.TagDTO;
import com.example.Aapi.entity.Tag;

@Mapper(componentModel="spring")
public interface TagMapper {
	
	Tag tagDTOToTagEntity(TagDTO dto);
	TagDTO tagEntityToTagDTo(Tag entity);
	
	Set<Tag> convertTagDTOSetToBlobJEntitySet(Set<TagDTO> list);
	Set<TagDTO> convertTagEntitySetToTagDTOSet(Set<Tag> list);
	
	List<Tag> convertTagDTOListToTagEntityList(List<TagDTO> list);
	List<TagDTO> convertTagEntityListToTagDTOList(List<Tag> list);

}
