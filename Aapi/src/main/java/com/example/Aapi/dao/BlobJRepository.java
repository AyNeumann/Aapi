package com.example.Aapi.dao;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.Aapi.dto.BlobJ;
import com.example.Aapi.dto.BlobJType;


/**
 * Blob repository
 * @author Aymeric NEUMANN

 */
@Repository
public interface BlobJRepository extends CrudRepository<BlobJ, Long>, PagingAndSortingRepository<BlobJ, Long> {

	Page<BlobJ> findAll(Pageable pageable);
	
	Set<BlobJ> findByCountEqualsOrCountGreaterThan(int minCount, int minCount2);
		
	Set<BlobJ> findByNameContainingOrderByNameAsc(String name);
	
	Set<BlobJ> findByTypeOrderByNameAsc(BlobJType type);
	
}
