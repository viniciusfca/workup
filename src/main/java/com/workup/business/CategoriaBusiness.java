package com.workup.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.workup.dao.WorkUpDAO;
import com.workup.entity.CategoriaEntity;
import com.workup.entity.UsuarioTipoServicoEntity;
import com.workup.entity.TipoServicoEntity;
import com.workup.entity.UsuarioEntity;
import com.workup.repository.CategoriaRepository;
import com.workup.repository.UsuarioTipoServicoRepository;
import com.workup.repository.TipoServicoRepository;
import com.workup.repository.UsuarioRepository;
import com.workup.vo.CategoriaVO;
import com.workup.vo.TipoServicoVO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class CategoriaBusiness {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private TipoServicoRepository tipoServicoRepository;

	@Autowired
	private UsuarioTipoServicoRepository usuarioTipoServicoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private TipoServicoBusiness tipoServicoBusiness;

	@Autowired
	private WorkUpDAO workUpDAO;

	public ResponseEntity<CategoriaVO> getCategoria(Integer id) throws Exception {

		try {
			CategoriaEntity categoriaEntity = categoriaRepository.findById(id);

			if (categoriaEntity == null) {
				throw new Exception("Categoria não encontrada!");
			}

			else {
				CategoriaVO categoriaVO = this.prepararCategoria(categoriaEntity);
				return ResponseEntity.status(HttpStatus.OK).body(categoriaVO);
			}
		}

		catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}

	}

	private CategoriaVO prepararCategoria(CategoriaEntity categoriaEntity) {

		CategoriaVO categoriaVO = new CategoriaVO();

		categoriaVO.setId(categoriaEntity.getId());
		categoriaVO.setNome(categoriaEntity.getNome());

		List<TipoServicoEntity> listaTipoServicoEntity = tipoServicoRepository
				.findByIdCategoria(categoriaEntity.getId());

		List<TipoServicoVO> listaTipoServicoVO = tipoServicoBusiness.prepararListaTipoServico(listaTipoServicoEntity);

		categoriaVO.setListaTipoServico(listaTipoServicoVO);

		return categoriaVO;
	}

	public ResponseEntity<List<CategoriaVO>> getListaCategoria() throws Exception {

		try {

			List<CategoriaEntity> listaCategoriaEntity = categoriaRepository.findAll();
			List<CategoriaVO> listaCategoriaVO = this.prepararListaCategoria(listaCategoriaEntity);

			return ResponseEntity.status(HttpStatus.OK).body(listaCategoriaVO);

		}

		catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	private List<CategoriaVO> prepararListaCategoria(List<CategoriaEntity> listaCategoriaEntity) {

		List<CategoriaVO> listaCategoriaVO = new ArrayList<CategoriaVO>();

		for (CategoriaEntity categoriaEntity : listaCategoriaEntity) {

			CategoriaVO categoriaVO = new CategoriaVO();

			categoriaVO.setId(categoriaEntity.getId());
			categoriaVO.setNome(categoriaEntity.getNome());

			List<TipoServicoEntity> listaTipoServicoEntity = tipoServicoRepository
					.findByIdCategoria(categoriaEntity.getId());

			List<TipoServicoVO> listaTipoServicoVO = tipoServicoBusiness
					.prepararListaTipoServico(listaTipoServicoEntity);

			categoriaVO.setListaTipoServico(listaTipoServicoVO);

			listaCategoriaVO.add(categoriaVO);
		}

		return listaCategoriaVO;
	}

	public ResponseEntity<CategoriaVO> getCategoriaUsuario(Integer idUsuario) throws Exception {

		try {

			if (idUsuario == null || idUsuario <= 0) {
				throw new Exception("ID de Usuário inválido!");
			}

			UsuarioEntity usuarioEntity = usuarioRepository.findById(idUsuario);

			if (usuarioEntity == null) {
				throw new Exception("Usuário não encontrado!");
			}
			
			List<UsuarioTipoServicoEntity> listaUsuarioTipoServicoEntity = usuarioTipoServicoRepository
					.findByIdPrestador(usuarioEntity.getId());

			if (listaUsuarioTipoServicoEntity == null || listaUsuarioTipoServicoEntity.size() <= 0) {
				return ResponseEntity.status(HttpStatus.OK).body(null);
			}

			// Para saber o idCategoria do prestador deve-se pegar o primeiro
			// tipo de serviço prestado pelo usuário e capturar a categoria dele
			Integer idCategoria = tipoServicoRepository
					.findById(listaUsuarioTipoServicoEntity.get(0).getIdTipoServico()).getIdCategoria();

			CategoriaEntity categoriaEntity = categoriaRepository.findById(idCategoria);

			// Se não tiver categoria ele retorna nulo
			// OBS: A princípio, não se pode cair aqui!
			if (categoriaEntity == null) {
				return ResponseEntity.status(HttpStatus.OK).body(null);
			}

			else {
				CategoriaVO categoriaVO = this.prepararCategoria(categoriaEntity);

				for (TipoServicoVO tipoServicoVO : categoriaVO.getListaTipoServico()) {

					tipoServicoVO.setChecked(false);
					tipoServicoVO.setValor(0.00f);

					for (UsuarioTipoServicoEntity usuarioTipoServicoEntity : listaUsuarioTipoServicoEntity) {

						if (tipoServicoVO.getId().equals(usuarioTipoServicoEntity.getIdTipoServico())) {
							tipoServicoVO.setChecked(true);

							if (usuarioTipoServicoEntity.getValor() != null) {
								tipoServicoVO.setValor(usuarioTipoServicoEntity.getValor());
							}

							break;
						}

					}
				}

				return ResponseEntity.status(HttpStatus.OK).body(categoriaVO);
			}
		}

		catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}

	}

	public void putCategoriaUsuario(Integer idUsuario, CategoriaVO categoriaVO) throws Exception {

		try {
			
			if (categoriaVO == null) {
				throw new Exception("Categoria VO inválida!");
			}
			
			if (idUsuario == null || idUsuario <= 0) {
				throw new Exception("ID de Usuário inválido!");
			}

			UsuarioEntity usuarioEntity = usuarioRepository.findById(idUsuario);

			if (usuarioEntity == null) {
				throw new Exception("Usuário não encontrado!");
			}

			else{

				if (usuarioEntity.getStatus() == 5 && workUpDAO.getEstouRealizandoAtendimento(usuarioEntity.getId())) {
					throw new Exception(
							"Você não pode finalizar essa ação!\nFavor finalizar sua atual solicitação de atendimento.");
				}
			}

			List<UsuarioTipoServicoEntity> listaUsuarioTipoServicoEntity = usuarioTipoServicoRepository
					.findByIdPrestador(idUsuario);

			// deletando os tipos de serviço do usuário
			for (UsuarioTipoServicoEntity usuarioTipoServicoEntity : listaUsuarioTipoServicoEntity) {
				usuarioTipoServicoRepository.delete(usuarioTipoServicoEntity);
			}

			if (categoriaVO.getListaTipoServico() == null || categoriaVO.getListaTipoServico().size() <= 0) {
				// Alterar status do usuário para 0 caso a lista esteja vazia
				// para que ele não preste mais nenhum tipo de serviço

				usuarioEntity.setStatus(0);				
				usuarioRepository.save(usuarioEntity);
			}

			if (categoriaVO.getListaTipoServico() != null) {

				// incluindo os tipos de serviço do usuário
				for (TipoServicoVO tipoServicoVO : categoriaVO.getListaTipoServico()) {

					if (tipoServicoVO.getChecked()) {
						UsuarioTipoServicoEntity usuarioTipoServicoEntity = new UsuarioTipoServicoEntity();

						usuarioTipoServicoEntity.setIdPrestador(idUsuario);
						usuarioTipoServicoEntity.setIdTipoServico(tipoServicoVO.getId());
						usuarioTipoServicoEntity.setValor(tipoServicoVO.getValor());

						usuarioTipoServicoRepository.save(usuarioTipoServicoEntity);
					}
				}
			}
		}

		catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

}
