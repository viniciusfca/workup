package com.workup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.workup.entity.TipoServicoEntity;

@Repository
public interface TipoServicoRepository extends JpaRepository<TipoServicoEntity, Integer> {
	
	TipoServicoEntity findById(Integer id);
	
	@Query("Select ts From TipoServicoEntity ts WHERE ts.idCategoria = ?1")
	public List<TipoServicoEntity> findByIdCategoria(Integer idCategoria);
	
}
