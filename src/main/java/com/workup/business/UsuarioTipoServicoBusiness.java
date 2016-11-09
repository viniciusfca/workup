package com.workup.business;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.workup.entity.UsuarioTipoServicoEntity;
import com.workup.dao.WorkUpDAO;
import com.workup.entity.TipoServicoEntity;
import com.workup.entity.UsuarioEntity;
import com.workup.repository.UsuarioTipoServicoRepository;
import com.workup.repository.TipoServicoRepository;
import com.workup.repository.UsuarioRepository;
import com.workup.vo.UsuarioTipoServicoVO;
import com.workup.vo.UsuarioVO;

@Component
public class UsuarioTipoServicoBusiness {

	/*
	
	@Autowired
	private UsuarioTipoServicoRepository usuarioTipoServicoRepository;

	*/
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private TipoServicoRepository tipoServicoRepository;

	@Autowired
	private WorkUpDAO workUpDAO;
	
	
	/**
	 * Retorna os tipos de serviço que determinado usuário fornece.
	 * 
	 * @param idPrestador
	 * @return
	 */
	/*public ResponseEntity<List<UsuarioTipoServicoVO>> listarTipoServicoUsuario(Integer idPrestador) throws Exception {

		try {
			if (idPrestador == null || idPrestador <= 0) {
				throw new Exception("ID do Usuário Prestador inválido!");
			}

			UsuarioEntity usuarioEntity = usuarioRepository.findById(idPrestador);

			if (usuarioEntity == null) {
				throw new Exception("Usuário Prestador não encontrado!");
			}

			List<UsuarioTipoServicoEntity> listaTipoServicoUsuarioEntity = usuarioTipoServicoRepository
					.findByIdPrestador(idPrestador);

			List<UsuarioTipoServicoVO> listaTipoServicoUsuarioVO = this.prepararLista(listaTipoServicoUsuarioEntity);

			return ResponseEntity.status(HttpStatus.OK).body(listaTipoServicoUsuarioVO);
		}

		catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}*/

	/**
	 * Retorna uma lista de usuários que fornecem o tipo de servico passado como
	 * parâmetro.
	 * 
	 * @param idPrestador
	 * @return
	 */
	/*public ResponseEntity<List<UsuarioTipoServicoVO>> listarUsuarioTipoServico(Integer idTipoServico) throws Exception {

		try {
			if (idTipoServico == null || idTipoServico <= 0) {
				throw new Exception("ID do Tipo de Serviço inválido!");
			}

			TipoServicoEntity tipoServicoEntity = tipoServicoRepository.findById(idTipoServico);

			if (tipoServicoEntity == null) {
				throw new Exception("Tipo de Serviço inexistente!");
			}

			List<UsuarioTipoServicoEntity> listaUsuarioTipoServicoEntity = usuarioTipoServicoRepository
					.findByIdTipoServico(idTipoServico);

			List<UsuarioTipoServicoVO> listaUsuarioTipoServicoVO = this.prepararLista(listaUsuarioTipoServicoEntity);

			return ResponseEntity.status(HttpStatus.OK).body(listaUsuarioTipoServicoVO);
		}

		catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	private List<UsuarioTipoServicoVO> prepararLista(List<UsuarioTipoServicoEntity> listaUsuarioTipoServicoEntity) {

		List<UsuarioTipoServicoVO> listaUsuarioTipoServicoVO = new ArrayList<UsuarioTipoServicoVO>();

		for (UsuarioTipoServicoEntity usuarioTipoServicoEntity : listaUsuarioTipoServicoEntity) {

			UsuarioTipoServicoVO usuarioTipoServicoVO = new UsuarioTipoServicoVO();

			usuarioTipoServicoVO.setId(usuarioTipoServicoEntity.getId());
			usuarioTipoServicoVO.setIdPrestador(usuarioTipoServicoEntity.getIdPrestador());
			usuarioTipoServicoVO.setIdTipoServico(usuarioTipoServicoEntity.getIdTipoServico());
			usuarioTipoServicoVO.setValor(usuarioTipoServicoEntity.getValor());

			listaUsuarioTipoServicoVO.add(usuarioTipoServicoVO);
		}

		return listaUsuarioTipoServicoVO;
	}*/

	// Usado na tela de contratação de serviços
	public ResponseEntity<List<UsuarioTipoServicoVO>> getListaUsuarioTipoServico(Integer idUsuario, Integer idTipoServico)
			throws Exception, SQLException {

		try {
			if (idUsuario == null || idUsuario <= 0) {
				throw new Exception("ID do Usuário inválido!");
			}
			
			UsuarioEntity usuarioEntity = usuarioRepository.findById(idUsuario);
			
			if(usuarioEntity == null){
				throw new Exception("Usuário não encontrado!");
			}
						
			if (idTipoServico == null || idTipoServico <= 0) {
				throw new Exception("ID do Tipo de Serviço inválido!");
			}

			TipoServicoEntity tipoServicoEntity = tipoServicoRepository.findById(idTipoServico);

			if (tipoServicoEntity == null) {
				throw new Exception("Tipo de Serviço não encontrado!");
			}

			List<UsuarioTipoServicoVO> listaUsuarioTipoServicoVO = workUpDAO
					.getListaUsuarioTipoServico(idUsuario, idTipoServico, usuarioEntity.getCidade(), usuarioEntity.getUf());
			return ResponseEntity.status(HttpStatus.OK).body(listaUsuarioTipoServicoVO);
		}

		catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
	
}
