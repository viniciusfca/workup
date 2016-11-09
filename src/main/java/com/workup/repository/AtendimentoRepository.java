package com.workup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.workup.entity.AtendimentoEntity;

@Repository
public interface AtendimentoRepository extends JpaRepository<AtendimentoEntity, Integer>{
	
	@Query("SELECT ae FROM AtendimentoEntity ae WHERE ae.idPrestador = ?1")
	List<AtendimentoEntity> findByIdPrestador(Integer idPrestador);
	
	@Query("SELECT ae FROM AtendimentoEntity ae WHERE ae.idContratante = ?1")
	List<AtendimentoEntity> findByIdContratante(Integer idContratante);

	AtendimentoEntity findById(Integer id);
}
