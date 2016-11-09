package com.workup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.workup.entity.UsuarioTipoServicoEntity;

@Repository
public interface UsuarioTipoServicoRepository extends JpaRepository<UsuarioTipoServicoEntity, Integer> {
	
	UsuarioTipoServicoEntity findById(Integer id);
	
	@Query("SELECT uts FROM UsuarioTipoServicoEntity uts WHERE uts.idPrestador = ?1")
	public List<UsuarioTipoServicoEntity> findByIdPrestador(Integer idPrestador);
	
	@Query("SELECT uts FROM UsuarioTipoServicoEntity uts WHERE uts.idTipoServico = ?1")
	public List<UsuarioTipoServicoEntity> findByIdTipoServico(Integer idTipoServico);
	
}
