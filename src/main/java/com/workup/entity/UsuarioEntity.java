package com.workup.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Usuario", schema = "aplicativotcc")
public class UsuarioEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;

	@Column(name = "Nome")
	private String nome;

	@Column(name = "sobrenome")
	private String sobrenome;

	@Column(name = "sexo")
	private String sexo;

	@Column(name = "datanascimento")
	private Date dataNascimento;

	@Column(name = "username")
	private String username;

	@Column(name = "senha")
	private String senha;

	@Column(name = "tipo")
	private String tipo;

	@Column(name = "cpfcnpj")
	private String cpfCnpj;

	@Column(name = "email")
	private String email;

	@Column(name = "telefone")
	private Long telefone;

	@Column(name = "celular")
	private Long celular;

	@Column(name = "endereco")
	private String endereco;

	@Column(name = "numero")
	private Long numero;

	@Column(name = "bairro")
	private String bairro;

	@Column(name = "complemento")
	private String complemento;

	@Column(name = "cidade")
	private String cidade;

	@Column(name = "uf")
	private String uf;

	@Column(name = "cep")
	private String cep;

	@Column(name = "pais")
	private String pais;

	@Column(name = "homepage")
	private String homepage;

	@Column(name = "datacadastro")
	private Date dataCadastro;

	@Column(name = "dataultimoacesso")
	private Date dataUltimoAcesso;

	@Column(name = "dataexclusao")
	private Date dataExclusao;

	@Column(name = "status")
	private Integer status;

	@Column(name = "logado")
	private Boolean logado;

	@Column(name = "foto")
	private String foto;
	
	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public Boolean getLogado() {
		return logado;
	}

	public void setLogado(Boolean logado) {
		this.logado = logado;
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

	public Date getDataNascimento() {
		return dataNascimento;
	}

	@SuppressWarnings("deprecation")
	public void setDataNascimento(Date dataNascimento) {
		
		dataNascimento.setHours(0);
		dataNascimento.setMinutes(0);
		dataNascimento.setSeconds(0);
		
		this.dataNascimento = dataNascimento;
	}
 
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
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

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Date getDataUltimoAcesso() {
		return dataUltimoAcesso;
	}

	public void setDataUltimoAcesso(Date dataUltimoAcesso) {
		this.dataUltimoAcesso = dataUltimoAcesso;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getDataExclusao() {
		return dataExclusao;
	}

	public void setDataExclusao(Date dataExclusao) {
		this.dataExclusao = dataExclusao;
	}
	
}
