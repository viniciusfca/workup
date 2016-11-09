package com.workup.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class CategoriaVO {

	@JsonInclude(Include.NON_NULL)
	private Integer id;

	@JsonInclude(Include.NON_NULL)
	private String nome;

	@JsonInclude(Include.NON_NULL)
	private List<TipoServicoVO> listaTipoServico;

	public List<TipoServicoVO> getListaTipoServico() {
		return listaTipoServico;
	}

	public void setListaTipoServico(List<TipoServicoVO> listaTipoServico) {
		this.listaTipoServico = listaTipoServico;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
