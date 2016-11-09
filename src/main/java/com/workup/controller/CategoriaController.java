package com.workup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.workup.business.CategoriaBusiness;
import com.workup.vo.CategoriaVO;
import java.util.List;

@RestController
public class CategoriaController {

	@Autowired
	private CategoriaBusiness categoriaBusiness;

	@CrossOrigin
	@RequestMapping(value = "/listacategoria", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CategoriaVO>> retornarListaCategoria() throws Exception{
		ResponseEntity<List<CategoriaVO>> categorias = categoriaBusiness.getListaCategoria();
		return categorias;
	}

	/*
	
	@CrossOrigin
	@RequestMapping(value = "/categoria", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CategoriaVO> retornarCategoria(@RequestParam(value = "id", required = true) Integer id) throws Exception{
		ResponseEntity<CategoriaVO> categoriaVO = categoriaBusiness.getCategoria(id);
		return categoriaVO;
	}

	*/
	
	@CrossOrigin
	@RequestMapping(value = "/categoriausuario", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CategoriaVO> retornarCategoriaUsuario(@RequestParam(value = "id", required = true) Integer id) throws Exception{
		ResponseEntity<CategoriaVO> categoriaVO = categoriaBusiness.getCategoriaUsuario(id);
		return categoriaVO;
	}

	@CrossOrigin
	@RequestMapping(value = "/categoriausuario", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void editarCategoriaUsuario(@RequestParam(value = "id", required = true) Integer id, @RequestBody CategoriaVO categoriaVO) throws Exception{
		categoriaBusiness.putCategoriaUsuario(id, categoriaVO);
	}
	
}
