package com.example.Aapi.dao;

import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.example.Aapi.dto.Tag;

public interface TagRepository extends CrudRepository<Tag, Long>, PagingAndSortingRepository<Tag, Long> {
	
	Page<Tag> findAll(Pageable pageable);

	Set<Tag> findByNameContainingOrderByNameAsc(String name);

	/**
	 * Update the Tag with the matching id.
	 * @param tagId id of the Tag to update
	 * @param tagName new name of the Tag to update
	 * @return number of updated entities
	 */
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("UPDATE Tag t SET t.name = :tagName WHERE t.id = :tagId")
	Integer updateTag(@Param("tagId") Long tagId, @Param("tagName") String tagName);

}
