package com.example.Aapi.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.Aapi.dto.BlobJ;


/**
 * Blob repository
 * @author Aymeric Neumann

 */
@Repository
public interface BlobJRepository extends CrudRepository<BlobJ, Long>, PagingAndSortingRepository<BlobJ, Long> {

	Page<BlobJ> findAll(Pageable pageable);
}
