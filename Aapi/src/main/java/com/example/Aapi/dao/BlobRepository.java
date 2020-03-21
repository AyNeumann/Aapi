package com.example.Aapi.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.Aapi.dto.Blob;

/**
 * Blob repository
 * @author Aymeric Neumann

 */
@Repository
public interface BlobRepository extends CrudRepository<Blob, Long> {

}
