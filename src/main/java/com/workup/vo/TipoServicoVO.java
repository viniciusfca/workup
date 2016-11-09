package com.workup.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class TipoServicoVO {

	// @JsonInclude(Include.NON_NULL)
	// private Integer idCategoria;

	@JsonInclude(Include.NON_NULL)
	private Integer id;

	@JsonInclude(Include.NON_NULL)
	private String nome;

	@JsonInclude(Include.NON_NULL)
	private Boolean checked;

	@JsonInclude(Include.NON_NULL)
	private Float valor;

	@JsonInclude(Include.NON_NULL)
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Float getValor() {
		return valor;
	}

	public void setValor(Float valor) {
		this.valor = valor;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
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
