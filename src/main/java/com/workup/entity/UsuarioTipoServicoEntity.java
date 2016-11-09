package com.workup.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="usuariotiposervico", schema="aplicativotcc")
public class UsuarioTipoServicoEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@Column(name= "idtiposervico")
	private Integer idTipoServico;
	
	@Column(name="idprestador")
	private Integer idPrestador;

	@Column(name="valor")
	private Float valor;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Float getValor() {
		return valor;
	}

	public void setValor(Float valor) {
		this.valor = valor;
	}

	public Integer getIdPrestador() {
		return idPrestador;
	}

	public void setIdPrestador(Integer idPrestador) {
		this.idPrestador = idPrestador;
	}

	public Integer getIdTipoServico() {
		return idTipoServico;
	}

	public void setIdTipoServico(Integer idTipoServico) {
		this.idTipoServico = idTipoServico;
	}
	
}

