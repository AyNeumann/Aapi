package com.example.Aapi.dao;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.Aapi.dto.Tag;

public interface TagRepository extends CrudRepository<Tag, Long>, PagingAndSortingRepository<Tag, Long> {
	
	Page<Tag> findAll(Pageable pageable);

	Set<Tag> findByNameContainingOrderByNameAsc(String name);

	Integer updateTag(Long id, String name);

}
