package com.workup.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.workup.business.AtendimentoBusiness;
import com.workup.vo.AtendimentoVO;

import javassist.NotFoundException;

@RestController
public class AtendimentoController {

	@Autowired
	private AtendimentoBusiness atendimentoBusiness;

//	@CrossOrigin
//	@RequestMapping(value = "/listaavaliacao", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<List<AtendimentoAvaliacaoVO>> getListaAvaliacaoPrestador() throws SQLException {
//		ResponseEntity<List<AtendimentoAvaliacaoVO>> listaAtendimentoAvaliacaoVO = atendimentoBusiness.getListaAvaliacaoGeral();
//		return listaAtendimentoAvaliacaoVO;
//	}

	@CrossOrigin
	@RequestMapping(value = "/listaservicoprestado", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AtendimentoVO>> retornarListaServicoPrestado(@RequestParam(value = "idPrestador", required = true) Integer idPrestador) throws NotFoundException, Exception{
		ResponseEntity<List<AtendimentoVO>> listaAtendimentoServicoPrestado = atendimentoBusiness.getListaAtendimentoPrestador(idPrestador);
		return listaAtendimentoServicoPrestado;
	}

	@CrossOrigin
	@RequestMapping(value = "/listaservicosolicitado", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AtendimentoVO>> retornarListaServicoSolicitado(@RequestParam(value = "idContratante", required = true) Integer idContratante) throws NotFoundException, Exception{
		ResponseEntity<List<AtendimentoVO>> listaAtendimentoServicoSolicitado = atendimentoBusiness.getListaAtendimentoContratante(idContratante);
		return listaAtendimentoServicoSolicitado;
	}

	@CrossOrigin
	@RequestMapping(value = "/cancelaratendimento", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void cancelarAtendimento(@RequestParam(value = "idAtendimento", required = true) Integer idAtendimento, @RequestParam(value = "idUsuario", required = true) Integer idUsuario, @RequestParam(value = "motivoCancelamento", required = true) String motivoCancelamento) throws NotFoundException, Exception {
		atendimentoBusiness.cancelarAtendimento(idAtendimento, idUsuario, motivoCancelamento);
	}

	@CrossOrigin
	@RequestMapping(value = "/finalizaratendimento", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void finalizarAtendimento(@RequestParam(value = "idAtendimento", required = true) Integer idAtendimento) throws NotFoundException, Exception{
		atendimentoBusiness.finalizarAtendimento(idAtendimento);
	}

	@CrossOrigin
	@RequestMapping(value = "/rejeitarfinalizacaoatendimento", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void rejeitarFinalizacaoAtendimento(@RequestParam(value = "idAtendimento", required = true) Integer idAtendimento) throws NotFoundException, Exception{
		atendimentoBusiness.rejeitarFinalizacaoAtendimento(idAtendimento);
	}

	@CrossOrigin
	@RequestMapping(value = "/recusaratendimento", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void recusarAtendimento(@RequestParam(value = "idAtendimento", required = true) Integer idAtendimento) throws NotFoundException, Exception{
		atendimentoBusiness.responderSolicitacaoAtendimento(idAtendimento, 3);
	}

	@CrossOrigin
	@RequestMapping(value = "/aceitaratendimento", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void aceitarAtendimento(@RequestParam(value = "idAtendimento", required = true) Integer idAtendimento) throws NotFoundException, Exception{
		atendimentoBusiness.responderSolicitacaoAtendimento(idAtendimento, 2);
	}

	@CrossOrigin
	@RequestMapping(value = "/avaliaratendimento", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void avaliarAtendimento(@RequestParam(value = "idAtendimento", required = true) Integer idAtendimento, @RequestParam(value = "notaCliente", required = true) Integer notaCliente, @RequestParam(value = "comentarioAvaliacao", required = true) String comentarioAvaliacao) throws NotFoundException, Exception{
		atendimentoBusiness.avaliarAtendimento(idAtendimento, notaCliente, comentarioAvaliacao);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/contrataratendimento", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void contratarAtendimento(@RequestParam(value = "idUsuarioTipoServico", required = true) Integer idUsuarioTipoServico, @RequestParam(value = "idContratante", required = true) Integer idContratante) throws NotFoundException, Exception{
		atendimentoBusiness.contratarAtendimento(idContratante, idUsuarioTipoServico);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/listasolicitacaoatendimento", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Long>> getListaSolicitacaoAtendimento(@RequestParam(value = "idUsuario", required = true) Integer idUsuario) throws NotFoundException, Exception{
		ResponseEntity<List<Long>> listaIdAtendimento = atendimentoBusiness.getListaSolicitacaoAtendimento(idUsuario);
		return listaIdAtendimento;
	}
	
	@CrossOrigin
	@RequestMapping(value = "/alterarstatusatendimentonotificado", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void putListaSolicitacaoAtendimento(@RequestParam(value = "listaIdAtendimento", required = true) List<Integer> listaIdAtendimento) throws NotFoundException, Exception{
		atendimentoBusiness.putListaSolicitacaoAtendimento(listaIdAtendimento);
	}

	@CrossOrigin
	@RequestMapping(value = "/listaatendimentoconfirmadoprestador", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Long>> getListaAtendimentoAndamento(@RequestParam(value = "idContratante", required = true) Integer idContratante) throws NotFoundException, Exception{
		ResponseEntity<List<Long>> listaIdAtendimento = atendimentoBusiness.getListaAtendimentoNaoNotificado(idContratante, 'C', 2);
		return listaIdAtendimento;
	}

	@CrossOrigin
	@RequestMapping(value = "/listaatendimentorecusadoprestador", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Long>> getListaAtendimentoRecusadoPrestador(@RequestParam(value = "idContratante", required = true) Integer idContratante) throws NotFoundException, Exception{
		ResponseEntity<List<Long>> listaIdAtendimento = atendimentoBusiness.getListaAtendimentoNaoNotificado(idContratante, 'C', 3);
		return listaIdAtendimento;
	}

	@CrossOrigin
	@RequestMapping(value = "/listaatendimentofinalizadosemavaliacao", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Long>> getListaAtendimentoSemAvaliacao(@RequestParam(value = "idContratante", required = true) Integer idContratante) throws NotFoundException, Exception{
		ResponseEntity<List<Long>> listaIdAtendimento = atendimentoBusiness.getListaAtendimentoNaoNotificado(idContratante, 'C', 4);
		return listaIdAtendimento;
	}

	@CrossOrigin
	@RequestMapping(value = "/listaatendimentofinalizadocomavaliacao", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Long>> getListaAtendimentoComAvaliacao(@RequestParam(value = "idPrestador", required = true) Integer idPrestador) throws NotFoundException, Exception{
		ResponseEntity<List<Long>> listaIdAtendimento = atendimentoBusiness.getListaAtendimentoNaoNotificado(idPrestador, 'P', 5);
		return listaIdAtendimento;
	}

	@CrossOrigin
	@RequestMapping(value = "/listaatendimentorecusadocontratante", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Long>> getListaAtendimentoRecusadoContratante(@RequestParam(value = "idPrestador", required = true) Integer idPrestador) throws NotFoundException, Exception{
		ResponseEntity<List<Long>> listaIdAtendimento = atendimentoBusiness.getListaAtendimentoNaoNotificado(idPrestador, 'P', 6);
		return listaIdAtendimento;
	}
	
	@CrossOrigin
	@RequestMapping(value = "/listaatendimentocanceladoprestador", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Long>> getListaAtendimentoCanceladoPrestador(@RequestParam(value = "idContratante", required = true) Integer idContratante) throws NotFoundException, Exception{
		ResponseEntity<List<Long>> listaIdAtendimento = atendimentoBusiness.getListaAtendimentoNaoNotificado(idContratante, 'C', 9);
		return listaIdAtendimento;
	}

	@CrossOrigin
	@RequestMapping(value = "/listaatendimentocanceladocontratante", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Long>> getListaAtendimentoCanceladoContratante(@RequestParam(value = "idPrestador", required = true) Integer idPrestador) throws NotFoundException, Exception{
		ResponseEntity<List<Long>> listaIdAtendimento = atendimentoBusiness.getListaAtendimentoNaoNotificado(idPrestador, 'P', 8);
		return listaIdAtendimento;
	}
	
	/*
	@CrossOrigin
	@RequestMapping(value = "/listaatendimentocanceladosistema", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Long>> getListaAtendimentoCanceladoSistema(@RequestParam(value = "idUsuario", required = true) Integer idUsuario) throws NotFoundException, Exception{
		ResponseEntity<List<Long>> listaIdAtendimento = atendimentoBusiness.getListaAtendimentoNaoNotificado(idUsuario, 'T', 7);
		return listaIdAtendimento;
	}
	*/
	
	@CrossOrigin
	@RequestMapping(value = "/alterarstatusnotificacaoatendimento", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void putListaAtendimentoNotificado(@RequestParam(value = "listaIdAtendimento", required = true) List<Integer> listaIdAtendimento) throws NotFoundException, Exception{
		atendimentoBusiness.putListaAtendimentoNotificado(listaIdAtendimento);
	}
	
	// OK ^
	
	@CrossOrigin
	@RequestMapping(value = "/cancelaratendimentospendentes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public void consultarAtendimentosCancelamento() throws Exception{
		atendimentoBusiness.consultarAtendimentosCancelamento();
	}
}
