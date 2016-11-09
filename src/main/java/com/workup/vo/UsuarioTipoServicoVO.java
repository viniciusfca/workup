package com.workup.vo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UsuarioTipoServicoVO {
	
	private Integer id;
	
	private Integer idPrestador;
	
	private Integer idTipoServico;
	
	private Float valor;

	private String nomePrestador;
	
	private String sobrenomePrestador;
	
	private String dataCadastroPrestador;
	
	private String emailPrestador;
	
	private String homepagePrestador;
	
	private String fotoPrestador;
	
	private Float mediaGeralPrestador;
	
	private Long qtdAtendimentosRealizados;

	private String tipoPrestador;

	private String endereco;
	private Long numero;
	private String bairro;
	private String cidade;
	private String uf;
	
	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getTipoPrestador() {
		return tipoPrestador;
	}

	public void setTipoPrestador(String tipoPrestador) {
		this.tipoPrestador = tipoPrestador;
	}

	public String getNomePrestador() {
		return nomePrestador;
	}

	public void setNomePrestador(String nomePrestador) {
		this.nomePrestador = nomePrestador;
	}

	public String getSobrenomePrestador() {
		return sobrenomePrestador;
	}

	public void setSobrenomePrestador(String sobrenomePrestador) {
		this.sobrenomePrestador = sobrenomePrestador;
	}

	public String getDataCadastroPrestador() {
		return dataCadastroPrestador;
	}

	public void setDataCadastroPrestador(Date dataCadastroPrestador) {
		
		if(dataCadastroPrestador != null){
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			this.dataCadastroPrestador = dateFormat.format(dataCadastroPrestador);
		}
	}

	public String getEmailPrestador() {
		return emailPrestador;
	}

	public void setEmailPrestador(String emailPrestador) {
		this.emailPrestador = emailPrestador;
	}

	public String getHomepagePrestador() {
		return homepagePrestador;
	}

	public void setHomepagePrestador(String homepagePrestador) {
		this.homepagePrestador = homepagePrestador;
	}

	public String getFotoPrestador() {
		return fotoPrestador;
	}

	public void setFotoPrestador(String fotoPrestador) {
		this.fotoPrestador = fotoPrestador;
	}

	public Float getMediaGeralPrestador() {
		return mediaGeralPrestador;
	}

	public void setMediaGeralPrestador(Float mediaGeralPrestador) {
		this.mediaGeralPrestador = mediaGeralPrestador;
	}

	public Long getQtdAtendimentosRealizados() {
		return qtdAtendimentosRealizados;
	}

	public void setQtdAtendimentosRealizados(Long qtdAtendimentosRealizados) {
		this.qtdAtendimentosRealizados = qtdAtendimentosRealizados;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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
