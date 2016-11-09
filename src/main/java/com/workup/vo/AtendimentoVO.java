package com.workup.vo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AtendimentoVO {
	
	private Integer id;
	
	private Integer idContratante;
	private String nomeContratante;
	private String sobrenomeContratante;
	
	private Integer idPrestador;
	private String nomePrestador;
	private String sobrenomePrestador;
	
	private Integer idTipoServico;
	private String nomeTipoServico;

	private String dataInicial;
	private String dataSolicitacao;
	private String dataFinal;

	private Float valor;
	private String motivoCancelamento;
	private String dataCancelamento;

	private Integer status;

	private String comentarioAvaliacao;
	private Integer notaCliente;
	private String dataAvaliacao;

	public String getComentarioAvaliacao() {
		return comentarioAvaliacao;
	}

	public void setComentarioAvaliacao(String comentarioAvaliacao) {
		this.comentarioAvaliacao = comentarioAvaliacao;
	}

	public Integer getNotaCliente() {
		return notaCliente;
	}

	public void setNotaCliente(Integer notaCliente) {
		this.notaCliente = notaCliente;
	}

	public String getDataAvaliacao() {
		return dataAvaliacao;
	}

	public void setDataAvaliacao(String dataAvaliacao) {
		this.dataAvaliacao = dataAvaliacao;
	}
	
	public void setDataAvaliacao(Date dataAvaliacao){
		if(dataAvaliacao != null){
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			this.dataAvaliacao = dateFormat.format(dataAvaliacao);
		}
	}

	public void setDataSolicitacao(String dataSolicitacao) {
		this.dataSolicitacao = dataSolicitacao;
	}

	public String getSobrenomeContratante() {
		return sobrenomeContratante;
	}

	public void setSobrenomeContratante(String sobrenomeContratante) {
		this.sobrenomeContratante = sobrenomeContratante;
	}

	public String getSobrenomePrestador() {
		return sobrenomePrestador;
	}

	public void setSobrenomePrestador(String sobrenomePrestador) {
		this.sobrenomePrestador = sobrenomePrestador;
	}

	public String getNomeContratante() {
		return nomeContratante;
	}

	public void setNomeContratante(String nomeContratante) {
		this.nomeContratante = nomeContratante;
	}

	public String getNomePrestador() {
		return nomePrestador;
	}

	public void setNomePrestador(String nomePrestador) {
		this.nomePrestador = nomePrestador;
	}

	public String getNomeTipoServico() {
		return nomeTipoServico;
	}

	public void setNomeTipoServico(String nomeTipoServico) {
		this.nomeTipoServico = nomeTipoServico;
	}

	public void setDataInicial(String dataInicial) {
		this.dataInicial = dataInicial;
	}

	public void setDataFinal(String dataFinal) {
		this.dataFinal = dataFinal;
	}

	public Integer getId() {
		return id;
	}

	public Integer getIdContratante() {
		return idContratante;
	}

	public void setIdContratante(Integer idContratante) {
		this.idContratante = idContratante;
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

	public String getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {

		if(dataInicial != null){
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			this.dataInicial = dateFormat.format(dataInicial);
		}
	}

	public String getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {

		if(dataFinal != null){
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			this.dataFinal = dateFormat.format(dataFinal);
		}
	}

	public String getMotivoCancelamento() {
		return motivoCancelamento;
	}

	public void setMotivoCancelamento(String motivoCancelamento) {
		this.motivoCancelamento = motivoCancelamento;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getDataSolicitacao() {
		return dataSolicitacao;
	}

	public void setDataSolicitacao(Date dataSolicitacao) {

		if(dataSolicitacao != null){
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			this.dataSolicitacao = dateFormat.format(dataSolicitacao);
		}
	}

	
	public String getDataCancelamento() {
		return dataCancelamento;
	}

	public void setDataCancelamento(String dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}
	
	public void setDataCancelamento(Date dataCancelamento) {

		if(dataCancelamento != null){
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			this.dataCancelamento = dateFormat.format(dataCancelamento);
		}
	}
	
	public Float getValor() {
		return valor;
	}

	public void setValor(Float valor) {
		this.valor = valor;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
}
