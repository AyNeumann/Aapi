package com.example.Aapi.mapper;

import java.util.List;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.Aapi.dto.BlobJDTO;
import com.example.Aapi.dto.TagDTO;
import com.example.Aapi.entity.BlobJ;
import com.example.Aapi.entity.Tag;

@Mapper(componentModel="spring")
public interface BlobJMapper {
	
	BlobJMapper INSTANCE = Mappers.getMapper(BlobJMapper.class);
	
	BlobJ blobJDTOtoBlobJEntity(BlobJDTO dto);
	BlobJDTO blobJEntityToBlobJDTO(BlobJ entity);
	
	Set<BlobJ> convertBlobJDTOSetToBlobJEntitySet(Set<BlobJDTO> list);
	Set<BlobJDTO> convertBlobJEntitySetToBlobJDTOSet(Set<BlobJ> list);
	
	List<BlobJ> convertBlobJDTOListToBlobJEntityList(List<BlobJDTO> list);
	List<BlobJDTO> convertBlobJEntityListToBlobJDTOList(List<BlobJ> list);
	
	Tag tagDTOToTagEntity(TagDTO dto);
	TagDTO tagEntityToTagDTo(Tag entity);
}
