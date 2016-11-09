package com.workup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.workup.entity.CategoriaEntity;

@Repository
public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Integer> {

	CategoriaEntity findById(Integer id);
}
