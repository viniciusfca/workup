package com.workup.vo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class UsuarioVO {

	private int id;

	@JsonInclude(Include.NON_NULL)
	private String nome;

	@JsonInclude(Include.NON_NULL)
	private String sobrenome;

	@JsonInclude(Include.NON_NULL)
	private String sexo;

	@JsonInclude(Include.NON_NULL)
	private String dataNascimento;

	@JsonInclude(Include.NON_NULL)
	private String username;

	@JsonInclude(Include.NON_NULL)
	private String senha;

	@JsonInclude(Include.NON_NULL)
	private String tipo;

	@JsonInclude(Include.NON_NULL)
	private String foto;

	@JsonInclude(Include.NON_NULL)
	private String cpfCnpj;

	@JsonInclude(Include.NON_NULL)
	private String email;

	@JsonInclude(Include.NON_NULL)
	private Long telefone;

	@JsonInclude(Include.NON_NULL)
	private Long celular;

	@JsonInclude(Include.NON_NULL)
	private String endereco;

	@JsonInclude(Include.NON_NULL)
	private Long numero;

	@JsonInclude(Include.NON_NULL)
	private String bairro;

	@JsonInclude(Include.NON_NULL)
	private String complemento;

	@JsonInclude(Include.NON_NULL)
	private String cidade;

	@JsonInclude(Include.NON_NULL)
	private String uf;

	@JsonInclude(Include.NON_NULL)
	private String cep;

	@JsonInclude(Include.NON_NULL)
	private String pais;

	@JsonInclude(Include.NON_NULL)
	private String homepage;

	@JsonInclude(Include.NON_NULL)
	private String dataCadastro;

	@JsonInclude(Include.NON_NULL)
	private String dataUltimoAcesso;

	@JsonInclude(Include.NON_NULL)
	private Integer status;

	@JsonInclude(Include.NON_NULL)
	private Boolean logado;
	
	@JsonInclude(Include.NON_NULL)
	private Float mediaGeral;
	
	@JsonInclude(Include.NON_NULL)
	private Long qtdAtendimentosRealizados;
	
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Long getQtdAtendimentosRealizados() {
		return qtdAtendimentosRealizados;
	}

	public void setQtdAtendimentosRealizados(Long qtdAtendimentosRealizados) {
		this.qtdAtendimentosRealizados = qtdAtendimentosRealizados;
	}

	public Float getMediaGeral() {
		return mediaGeral;
	}

	public void setMediaGeral(Float mediaGeral) {
		this.mediaGeral = mediaGeral;
	}

	public Boolean getLogado() {
		return logado;
	}

	public void setLogado(Boolean logado) {
		this.logado = logado;
	}

	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		
		if(dataNascimento != null){
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			this.dataNascimento = dateFormat.format(dataNascimento);
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getTelefone() {
		return telefone;
	}

	public void setTelefone(Long telefone) {
		this.telefone = telefone;
	}

	public Long getCelular() {
		return celular;
	}

	public void setCelular(Long celular) {
		this.celular = celular;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
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

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		
		if(dataCadastro != null){
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			this.dataCadastro = dateFormat.format(dataCadastro);
		}
	}

	public String getDataUltimoAcesso() {
		return dataUltimoAcesso;
	}

	public void setDataUltimoAcesso(Date dataUltimoAcesso) {
		
		if(dataUltimoAcesso != null){
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			this.dataUltimoAcesso = dateFormat.format(dataUltimoAcesso);
		}
	}

}
