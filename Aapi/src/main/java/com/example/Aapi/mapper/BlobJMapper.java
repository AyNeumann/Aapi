package com.example.Aapi.mapper;

import java.util.Set;

import org.mapstruct.Mapper;

import com.example.Aapi.dto.BlobJDTO;
import com.example.Aapi.dto.TagDTO;
import com.example.Aapi.entity.BlobJ;
import com.example.Aapi.entity.Tag;

@Mapper
public interface BlobJMapper {
	BlobJ blobJDTOtoBlobJEntity(BlobJDTO dto);
	BlobJDTO blobJEntityToBlobJDTO(BlobJ entity);
	
	Set<BlobJ> convertBlobJDTOListToBlobJEntityList(Set<BlobJDTO> list);
	Set<BlobJDTO> convertBlobJEntityListToBlobJDTOList(Set<BlobJ> list);
	
	Tag tagDTOToTagEntity(TagDTO dto);
	TagDTO tagEntityToTagDTo(Tag entity);
}
