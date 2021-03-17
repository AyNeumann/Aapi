package com.example.Aapi.mapper;

import java.util.Set;

import org.mapstruct.Mapper;

import com.example.Aapi.dto.BlobJDTO;
import com.example.Aapi.dto.TagDTO;
import com.example.Aapi.entity.BlobJ;
import com.example.Aapi.entity.Tag;

@Mapper
public interface TagMapper {
	
	Tag tagDTOToTagEntity(TagDTO dto);
	TagDTO tagEntityToTagDTo(Tag entity);
	
	Set<BlobJ> convertBlobJDTOListToBlobJEntityList(Set<BlobJDTO> list);
	Set<BlobJDTO> convertBlobJEntityListToBlobJDTOList(Set<BlobJ> list);

}
