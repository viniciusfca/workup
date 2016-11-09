package com.workup.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "atendimento", schema="aplicativotcc")
public class AtendimentoEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@Column(name = "idcontratante")
	private Integer idContratante;
	
	@Column(name = "idprestador")
	private Integer idPrestador;
	
	@Column(name = "idtiposervico")
	private Integer idTipoServico;

	@Column(name = "datasolicitacao")
	private Date dataSolicitacao;

	@Column(name="valor")
	private float valor;
	
	@Column(name = "datainicial")
	private Date dataInicial;
	
	@Column(name = "datafinal")
	private Date dataFinal;
	
	@Column(name = "motivocancelamento")
	private String motivoCancelamento;
	
	@Column(name = "status")
	private int status;

	@Column(name = "notacliente")
	private Integer notaCliente;

	@Column(name = "comentarioavaliacao")
	private String comentarioAvaliacao;

	@Column(name = "dataavaliacao")
	private Date dataAvaliacao;

	@Column(name = "datacancelamento")
	private Date dataCancelamento;

	@Column(name = "notificado")
	private String notificado;
	
	public String getNotificado() {
		return notificado;
	}

	public void setNotificado(String notificado) {
		this.notificado = notificado;
	}

	public Date getDataCancelamento() {
		return dataCancelamento;
	}

	public void setDataCancelamento(Date dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}

	public Integer getNotaCliente() {
		return notaCliente;
	}

	public void setNotaCliente(Integer notaCliente) {
		this.notaCliente = notaCliente;
	}

	public String getComentarioAvaliacao() {
		return comentarioAvaliacao;
	}

	public void setComentarioAvaliacao(String comentarioAvaliacao) {
		this.comentarioAvaliacao = comentarioAvaliacao;
	}

	public Date getDataAvaliacao() {
		return dataAvaliacao;
	}

	public void setDataAvaliacao(Date dataAvaliacao) {
		this.dataAvaliacao = dataAvaliacao;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public String getMotivoCancelamento() {
		return motivoCancelamento;
	}

	public void setMotivoCancelamento(String motivoCancelamento) {
		this.motivoCancelamento = motivoCancelamento;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getDataSolicitacao() {
		return dataSolicitacao;
	}

	public void setDataSolicitacao(Date dataSolicitacao) {
		this.dataSolicitacao = dataSolicitacao;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

}
