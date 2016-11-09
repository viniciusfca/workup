package com.workup.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.workup.entity.TipoServicoEntity;
import com.workup.repository.TipoServicoRepository;
import com.workup.vo.TipoServicoVO;

@Component
public class TipoServicoBusiness {

	@Autowired
	private TipoServicoRepository tipoServicoRepository;

	public ResponseEntity<List<TipoServicoVO>> retornarListaTipoServico() {

		List<TipoServicoEntity> listaServicoEntity = tipoServicoRepository.findAll();
		List<TipoServicoVO> listaServicoVO = this.prepararListaTipoServico(listaServicoEntity);

		return ResponseEntity.status(HttpStatus.OK).body(listaServicoVO);
	}

	public List<TipoServicoVO> prepararListaTipoServico(List<TipoServicoEntity> listaTipoServicoEntity) {

		List<TipoServicoVO> listaTipoServicoVO = new ArrayList<TipoServicoVO>();

		for (TipoServicoEntity tipoServicoEntity : listaTipoServicoEntity) {

			TipoServicoVO tipoServicoVO = new TipoServicoVO();

			tipoServicoVO.setId(tipoServicoEntity.getId());
			tipoServicoVO.setNome(tipoServicoEntity.getNome());
			tipoServicoVO.setDescricao(tipoServicoEntity.getDescricao());
			//tipoServicoVO.setIdCategoria(tipoServicoEntity.getCategoriaEntity().getId());

			listaTipoServicoVO.add(tipoServicoVO);
		}

		return listaTipoServicoVO;
	}

	public ResponseEntity<TipoServicoVO> retornarTipoServico(Integer idTipoServico) throws Exception {

		TipoServicoVO tipoServicoVO = new TipoServicoVO();

		try {
			TipoServicoEntity tipoServicoEntity = tipoServicoRepository.findById(idTipoServico);

			if (tipoServicoEntity == null) {
				throw new Exception("Tipo de Serviço não encontrado!");
			}

			else {
				tipoServicoVO = this.prepararTipoServico(tipoServicoEntity);
				return ResponseEntity.status(HttpStatus.OK).body(tipoServicoVO);
			}
		}

		catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}

	}

 	private TipoServicoVO prepararTipoServico(TipoServicoEntity tipoServicoEntity) {

		TipoServicoVO tipoServicoVO = new TipoServicoVO();

		tipoServicoVO.setId(tipoServicoEntity.getId());
		tipoServicoVO.setNome(tipoServicoEntity.getNome());
		tipoServicoVO.setDescricao(tipoServicoEntity.getDescricao());
		//tipoServicoVO.setIdCategoria(tipoServicoEntity.getCategoriaEntity().getId());

		return tipoServicoVO;
	}
 	
}
