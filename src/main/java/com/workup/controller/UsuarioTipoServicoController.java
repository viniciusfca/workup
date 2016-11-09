package com.workup.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.workup.business.UsuarioTipoServicoBusiness;
import com.workup.vo.UsuarioTipoServicoVO;

@RestController
public class UsuarioTipoServicoController {
	
	@Autowired
	private UsuarioTipoServicoBusiness usuarioTipoServicoBusiness;
	
	/*
	
	@CrossOrigin
	@RequestMapping(value = "/listatiposervicousuario", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UsuarioTipoServicoVO>> retornarListaTipoServicoUsuario(@RequestParam(value = "idPrestador", required = true) Integer idPrestador) throws Exception {

		ResponseEntity<List<UsuarioTipoServicoVO>> listaTipoServicoUsuarioVO = usuarioTipoServicoBusiness.listarTipoServicoUsuario(idPrestador);
		return listaTipoServicoUsuarioVO;

	}
	
	@CrossOrigin
	@RequestMapping(value = "/listausuariotiposervico", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UsuarioTipoServicoVO>> retornarListaUsuarioTipoServico(@RequestParam(value = "idTipoServico", required = true) Integer idTipoServico) throws Exception {

		ResponseEntity<List<UsuarioTipoServicoVO>> listaUsuarioTipoServicoVO = usuarioTipoServicoBusiness.listarUsuarioTipoServico(idTipoServico);
		return listaUsuarioTipoServicoVO;

	}
	
	*/
	
	@CrossOrigin
	@RequestMapping(value = "/listausuariotiposervico", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UsuarioTipoServicoVO>> getListaUsuarioTipoServico(@RequestParam(value = "idTipoServico", required = true) Integer idTipoServico, @RequestParam(value = "idUsuario", required = true) Integer idUsuario) throws SQLException, Exception {
		ResponseEntity<List<UsuarioTipoServicoVO>> listaUsuarioTipoServicoVO = usuarioTipoServicoBusiness.getListaUsuarioTipoServico(idUsuario, idTipoServico);
		return listaUsuarioTipoServicoVO;
	}
	
}
