package com.example.Aapi.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.Aapi.dto.BlobJ;


/**
 * Blob repository
 * @author Aymeric Neumann

 */
@Repository
public interface BlobJRepository extends CrudRepository<BlobJ, Long> {

}
