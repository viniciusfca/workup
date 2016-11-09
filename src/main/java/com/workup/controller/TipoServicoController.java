package com.workup.controller;
import org.springframework.web.bind.annotation.RestController;

/*
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.workup.business.TipoServicoBusiness;
import com.workup.vo.TipoServicoVO;

*/

@RestController
public class TipoServicoController {
	
	/*
	
	@Autowired
	private TipoServicoBusiness tipoServicoBusiness;

	@CrossOrigin
	@RequestMapping(value = "/listatiposervico", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TipoServicoVO>> retornarListaTipoServico() {

		ResponseEntity<List<TipoServicoVO>> listaTipoServico = tipoServicoBusiness.retornarListaTipoServico();
		return listaTipoServico;

	}

	@CrossOrigin
	@RequestMapping(value = "/tiposervico", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TipoServicoVO> retornarTipoServico(
			@RequestParam(value = "id", required = true) Integer id) throws Exception {

		ResponseEntity<TipoServicoVO> tipoServicoVO = tipoServicoBusiness.retornarTipoServico(id);
		return tipoServicoVO;

	}
	
	*/
}
