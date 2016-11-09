package com.workup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.workup.business.UsuarioBusiness;
import com.workup.entity.UsuarioEntity;
import com.workup.vo.UsuarioVO;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
public class UsuarioController {

	@Autowired
	private UsuarioBusiness usuarioBusiness;

	@CrossOrigin
	@RequestMapping(value = "/usuario", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UsuarioVO> getUsuario(@RequestParam(value = "idUsuario", required = true) Integer idUsuario)
			throws Exception {

		ResponseEntity<UsuarioVO> usuario = usuarioBusiness.getUsuario(idUsuario);
		return usuario;
	}

	@CrossOrigin
	@RequestMapping(value = "/usuario", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public int postUsuario(@RequestBody UsuarioEntity usuarioEntity) throws Exception {
		// incluir
		return usuarioBusiness.postUsuario(usuarioEntity);
	}

	@CrossOrigin
	@RequestMapping(value = "/usuario", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void putUsuario(@RequestBody UsuarioEntity usuarioEntity) throws Exception {
		// editar
		usuarioBusiness.putUsuario(usuarioEntity);
	}

	@CrossOrigin
	@RequestMapping(value = "/loginusuario", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UsuarioVO> loginUsuario(@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "senha", required = true) String senha) throws Exception {

		ResponseEntity<UsuarioVO> usuarioVO = usuarioBusiness.loginUsuario(username, senha);
		return usuarioVO;

	}

	@CrossOrigin
	@RequestMapping(value = "/alterardisponibilidadeusuario", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void alterarDisponibilidadeUsuario(@RequestParam(value = "idUsuario", required = true) Integer idUsuario,
			@RequestParam(value = "status", required = true) Integer status) throws Exception {
		usuarioBusiness.alterarDisponibilidadeUsuario(idUsuario, status);
	}

	@CrossOrigin
	@RequestMapping(value = "/sairoudeletarcontausuario", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void sairOuDeletarContaUsuario(@RequestParam(value = "idUsuario", required = true) Integer idUsuario,
			@RequestParam(value = "status", required = true) Integer status) throws Exception {
		usuarioBusiness.sairOuDeletarContaUsuario(idUsuario, status);
	}

	@CrossOrigin
	@RequestMapping(value = "/uploadavatar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void uploadFileHandler(@RequestBody String img, @RequestParam(value = "nome", required = false) String nome, @RequestParam(value = "idUsuario", required = true) int idUsuario)
			throws Exception {

		usuarioBusiness.atualizarAvatar(idUsuario, img, nome);
	}
	
}
