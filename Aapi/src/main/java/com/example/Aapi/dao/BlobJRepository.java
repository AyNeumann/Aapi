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
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("UPDATE BlobJ b SET b.name = :blobJname, b.count = :blobJcount, b.type = :blobJtype WHERE b.id = :blobJid")
	void updateBlobJ(
			@Param("blobJid") Long blobJid, 
			@Param("blobJname") String blobJname,
			@Param("blobJcount") Integer blobJcount,
			@Param("blobJtype") BlobJType blobJtype
			);
	
}
/*
@Param("blobJcount") Integer blobJcount,
@Param("blobJtype") BlobJType blobJtype
*/
