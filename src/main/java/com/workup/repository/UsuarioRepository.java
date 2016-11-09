package com.workup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.workup.entity.UsuarioEntity;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {
	
	List<UsuarioEntity> findAll();
	UsuarioEntity findByUsername(String username);
	UsuarioEntity findById(Integer id);
	List<UsuarioEntity> findByCpfCnpj(String cpfCnpj);
	List<UsuarioEntity> findByEmail(String email);

}
