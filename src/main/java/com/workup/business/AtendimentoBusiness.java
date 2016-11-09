package com.workup.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.workup.dao.WorkUpDAO;
import com.workup.entity.AtendimentoEntity;
import com.workup.entity.TipoServicoEntity;
import com.workup.entity.UsuarioEntity;
import com.workup.entity.UsuarioTipoServicoEntity;
import com.workup.repository.AtendimentoRepository;
import com.workup.repository.TipoServicoRepository;
import com.workup.repository.UsuarioRepository;
import com.workup.repository.UsuarioTipoServicoRepository;
import com.workup.vo.AtendimentoVO;

@Component
public class AtendimentoBusiness {

	@Autowired
	private WorkUpDAO workUpDAO;

	@Autowired
	private AtendimentoRepository atendimentoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private TipoServicoRepository tipoServicoRepository;

	@Autowired
	UsuarioTipoServicoRepository usuarioTipoServicoRepository;

	// AtendimentoVO

	public ResponseEntity<List<AtendimentoVO>> getListaAtendimentoPrestador(Integer idPrestador) throws Exception {

		try {
			if (idPrestador == null || idPrestador <= 0) {
				throw new Exception("ID do Prestador inválido!");
			}

			UsuarioEntity usuarioEntity = usuarioRepository.findById(idPrestador);

			if (usuarioEntity == null) {
				throw new Exception("Prestador não encontrado!");
			}

			List<AtendimentoEntity> listaAtendimentoEntity = atendimentoRepository.findByIdPrestador(idPrestador);
			List<AtendimentoVO> listaAtendimentoVO = this.prepararListaAtendimento(listaAtendimentoEntity);

			return ResponseEntity.status(HttpStatus.OK).body(listaAtendimentoVO);
		}

		catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	public ResponseEntity<List<AtendimentoVO>> getListaAtendimentoContratante(Integer idContratante) throws Exception {

		try {
			if (idContratante == null || idContratante <= 0) {
				throw new Exception("ID do Contratante inválido!");
			}

			UsuarioEntity usuarioEntity = usuarioRepository.findById(idContratante);

			if (usuarioEntity == null) {
				throw new Exception("Contratante não encontrado!");
			}

			List<AtendimentoEntity> listaAtendimentoEntity = atendimentoRepository.findByIdContratante(idContratante);
			List<AtendimentoVO> listaAtendimentoVO = this.prepararListaAtendimento(listaAtendimentoEntity);

			return ResponseEntity.status(HttpStatus.OK).body(listaAtendimentoVO);
		}

		catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	private List<AtendimentoVO> prepararListaAtendimento(List<AtendimentoEntity> listaAtendimentoEntity) {

		List<AtendimentoVO> listaAtendimentoVO = new ArrayList<AtendimentoVO>();

		for (AtendimentoEntity atendimentoEntity : listaAtendimentoEntity) {

			AtendimentoVO atendimentoVO = new AtendimentoVO();

			UsuarioEntity prestadorEntity = usuarioRepository.findById(atendimentoEntity.getIdPrestador());
			UsuarioEntity contratanteEntity = usuarioRepository.findById(atendimentoEntity.getIdContratante());
			TipoServicoEntity tipoServicoEntity = tipoServicoRepository.findById(atendimentoEntity.getIdTipoServico());

			atendimentoVO.setId(atendimentoEntity.getId());

			atendimentoVO.setIdContratante(atendimentoEntity.getIdContratante());
			atendimentoVO.setNomeContratante(contratanteEntity.getNome());
			atendimentoVO.setSobrenomeContratante(contratanteEntity.getSobrenome());

			atendimentoVO.setIdPrestador(atendimentoEntity.getIdPrestador());
			atendimentoVO.setNomePrestador(prestadorEntity.getNome());
			atendimentoVO.setSobrenomePrestador(prestadorEntity.getSobrenome());

			atendimentoVO.setIdTipoServico(atendimentoEntity.getIdTipoServico());
			atendimentoVO.setNomeTipoServico(tipoServicoEntity.getNome());

			atendimentoVO.setMotivoCancelamento(atendimentoEntity.getMotivoCancelamento());
			atendimentoVO.setStatus(atendimentoEntity.getStatus());
			atendimentoVO.setDataFinal(atendimentoEntity.getDataFinal());
			atendimentoVO.setDataInicial(atendimentoEntity.getDataInicial());
			atendimentoVO.setDataSolicitacao(atendimentoEntity.getDataSolicitacao());

			atendimentoVO.setValor(atendimentoEntity.getValor());

			atendimentoVO.setNotaCliente(atendimentoEntity.getNotaCliente());
			atendimentoVO.setDataAvaliacao(atendimentoEntity.getDataAvaliacao());
			atendimentoVO.setComentarioAvaliacao(atendimentoEntity.getComentarioAvaliacao());

			atendimentoVO.setDataCancelamento(atendimentoEntity.getDataCancelamento());

			listaAtendimentoVO.add(atendimentoVO);
		}

		return listaAtendimentoVO;
	}

	public void cancelarAtendimento(Integer idAtendimento, Integer idUsuario, String motivoCancelamento)
			throws Exception {

		try {
			if (idAtendimento == null || idAtendimento <= 0 || idUsuario == null || idUsuario == 0
					|| motivoCancelamento == null || motivoCancelamento.trim() == "") {
				throw new Exception("ID de Atendimento, ID do Usuário ou Motivo de Cancelamento inválido!");
			}

			AtendimentoEntity atendimentoEntity = atendimentoRepository.findById(idAtendimento);

			if (atendimentoEntity == null) {
				throw new Exception("Solicitação de Atendimento não encontrada!");
			}

			else {

				Integer statusAtual = atendimentoEntity.getStatus();

				if (statusAtual == STATUS.NAO_NOTIFICADO || statusAtual == STATUS.NOTIFICADO_E_AGUARDANDO_RESPOSTA
						|| statusAtual == STATUS.EM_ANDAMENTO) {

					if (idUsuario.equals(atendimentoEntity.getIdContratante())) {
						atendimentoEntity.setStatus(STATUS.CANCELADO_PELO_CONTRATANTE);
					}

					else if (idUsuario.equals(atendimentoEntity.getIdPrestador())) {
						atendimentoEntity.setStatus(STATUS.CANCELADO_PELO_PRESTADOR);
					}

					else if (idUsuario.equals(-1) && (statusAtual == STATUS.NAO_NOTIFICADO
							|| statusAtual == STATUS.NOTIFICADO_E_AGUARDANDO_RESPOSTA)) {
						atendimentoEntity.setStatus(STATUS.CANCELADO_PELO_SISTEMA);
					}

					else {
						throw new Exception("O Usuário que solicitou o cancelamento não foi encontrado!");
					}

					atendimentoEntity.setMotivoCancelamento(motivoCancelamento.trim());
					atendimentoEntity.setDataCancelamento(new Date());

					atendimentoEntity.setNotificado("N");
					atendimentoRepository.save(atendimentoEntity);

					// Voltando os status do contratante e do prestador

					if (statusAtual == STATUS.EM_ANDAMENTO) {

						UsuarioEntity usuarioPrestadorEntity = usuarioRepository
								.findById(atendimentoEntity.getIdPrestador());

						if (usuarioPrestadorEntity == null) {
							throw new Exception("O Usuário Prestador não foi encontrado!");
						}

						UsuarioEntity usuarioContratanteEntity = usuarioRepository
								.findById(atendimentoEntity.getIdContratante());

						if (usuarioContratanteEntity == null) {
							throw new Exception("O Usuário Contratante não foi encontrado!");
						}

						usuarioPrestadorEntity.setStatus(1);
						usuarioContratanteEntity.setStatus(0);

						usuarioRepository.save(usuarioPrestadorEntity);
						usuarioRepository.save(usuarioContratanteEntity);
					}
				}

				else {
					throw new Exception("Não foi possível cancelar a solicitação de atendimento! (STATUS ATUAL: "
							+ statusAtual + ")");
				}
			}
		}

		catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	public void finalizarAtendimento(Integer idAtendimento) throws Exception {

		try {
			if (idAtendimento == null || idAtendimento <= 0) {
				throw new Exception("ID de Atendimento inválido!");
			}

			AtendimentoEntity atendimentoEntity = atendimentoRepository.findById(idAtendimento);

			if (atendimentoEntity == null) {
				throw new Exception("Solicitação de Atendimento não encontrada!");
			}

			else {

				Integer statusAtual = atendimentoEntity.getStatus();

				if (statusAtual == STATUS.EM_ANDAMENTO) {
					atendimentoEntity.setStatus(STATUS.FINALIZADO_SEM_AVALIACAO_E_SEM_CONFIRMACAO_CONTRATANTE);
					atendimentoEntity.setDataFinal(new Date());

					atendimentoEntity.setNotificado("N");
					atendimentoRepository.save(atendimentoEntity);

					UsuarioEntity usuarioPrestadorEntity = usuarioRepository
							.findById(atendimentoEntity.getIdPrestador());

					if (usuarioPrestadorEntity == null) {
						throw new Exception("O Usuário Prestador não foi encontrado!");
					}

					// Quando o prestador finaliza o atendimento, o status do
					// mesmo volta pra 1
					usuarioPrestadorEntity.setStatus(1);
					usuarioRepository.save(usuarioPrestadorEntity);
				}

				else {
					throw new Exception("Não foi possível finalizar a solicitação de atendimento! (STATUS ATUAL: "
							+ statusAtual + ")");
				}
			}
		}

		catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	public void rejeitarFinalizacaoAtendimento(Integer idAtendimento) throws Exception {

		try {
			if (idAtendimento == null || idAtendimento <= 0) {
				throw new Exception("ID de Atendimento inválido!");
			}

			AtendimentoEntity atendimentoEntity = atendimentoRepository.findById(idAtendimento);

			if (atendimentoEntity == null) {
				throw new Exception("Solicitação de Atendimento não encontrada!");
			}

			else {

				Integer statusAtual = atendimentoEntity.getStatus();

				if (statusAtual == STATUS.FINALIZADO_SEM_AVALIACAO_E_SEM_CONFIRMACAO_CONTRATANTE) {

					atendimentoEntity.setStatus(STATUS.NAO_EFETUADO_E_RECUSADO_PELO_CONTRATANTE);

					// Setando a nota pra zero, pois o fato do contratante
					// rejeitar a finalização do atendimento trata-se de uma
					// tentativa de golpe por parte do prestador.
					atendimentoEntity.setNotaCliente(0);
					atendimentoEntity.setDataAvaliacao(new Date());
					atendimentoEntity.setComentarioAvaliacao(
							"O usuário contratante rejeitou a solicitação de finalização do atendimento.");


					atendimentoEntity.setNotificado("N");
					atendimentoRepository.save(atendimentoEntity);

					UsuarioEntity usuarioContratanteEntity = usuarioRepository
							.findById(atendimentoEntity.getIdContratante());

					if (usuarioContratanteEntity == null) {
						throw new Exception("O Usuário Contratante não foi encontrado!");
					}

					// Quando o contratante rejeita o atendimento, o status do
					// mesmo volta pra 0
					usuarioContratanteEntity.setStatus(0);
					usuarioRepository.save(usuarioContratanteEntity);
				}

				else {
					throw new Exception("Não foi possível rejeitar a solicitação de atendimento! (STATUS ATUAL: "
							+ statusAtual + ")");
				}
			}
		}

		catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	public void responderSolicitacaoAtendimento(Integer idAtendimento, int statusAlteracao) throws Exception {

		try {
			if (idAtendimento == null || idAtendimento <= 0) {
				throw new Exception("ID de Atendimento inválido!");
			}

			AtendimentoEntity atendimentoEntity = atendimentoRepository.findById(idAtendimento);

			if (atendimentoEntity == null) {
				throw new Exception("Solicitação de Atendimento não encontrada!");
			}

			else {

				Integer statusAtual = atendimentoEntity.getStatus();

				if (statusAtual == STATUS.NAO_NOTIFICADO || statusAtual == STATUS.NOTIFICADO_E_AGUARDANDO_RESPOSTA) {

					UsuarioEntity usuarioPrestadorEntity = usuarioRepository
							.findById(atendimentoEntity.getIdPrestador());

					if (usuarioPrestadorEntity == null) {
						throw new Exception("O usuário prestador da solicitação de atendimento não foi encontrado!");
					}

					// Se o prestador se encontrar bloqueado para efetuar outra
					// solicitação de atendimento
					if (usuarioPrestadorEntity.getStatus() == 5 && statusAlteracao == STATUS.EM_ANDAMENTO) {
						throw new Exception(
								"Você não pode aceitar duas ou mais solicitações de atendimento ao mesmo tempo!");
					}

					else {

						if (statusAlteracao != STATUS.RECUSADO_PELO_PRESTADOR
								&& statusAlteracao != STATUS.EM_ANDAMENTO) {
							throw new Exception("Não foi possível alterar o status do atendimento! (STATUS ALTERAÇÃO: "
									+ statusAlteracao + ")");
						}

						atendimentoEntity.setStatus(statusAlteracao);

						// Se for para aceitar a solicitação, deve-se atualizar
						// dataInicial para sysdate e alterar o status do
						// prestador e do contratante
						// para 5 (bloqueado para atender ou contratar qualquer
						// outra solicitação de atendimento)
						if (statusAlteracao == STATUS.EM_ANDAMENTO) {

							usuarioPrestadorEntity.setStatus(5);

							UsuarioEntity usuarioContratanteEntity = usuarioRepository
									.findById(atendimentoEntity.getIdContratante());

							if (usuarioContratanteEntity == null) {
								throw new Exception(
										"O usuário contratante da solicitação de atendimento não foi encontrado!");
							}

							// Se o contratante estiver no meio de uma
							// solicitação de atendimento em andamento
							if (usuarioContratanteEntity.getStatus() == 5) {
								throw new Exception(
										"O usuário contratante está sendo atendido por outra solicitação de atendimento!");
							}

							usuarioContratanteEntity.setStatus(5);

							usuarioRepository.save(usuarioPrestadorEntity);
							usuarioRepository.save(usuarioContratanteEntity);

							atendimentoEntity.setDataInicial(new Date());
						}

						else {
							// atendimentoEntity.setDataCancelamento(new
							// Date());
						}

						atendimentoEntity.setNotificado("N");
						atendimentoRepository.save(atendimentoEntity);

					}
				}

				else {
					throw new Exception(
							"Não foi possível alterar o status do atendimento! (STATUS ATUAL: " + statusAtual + ")");
				}
			}
		}

		catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	public void avaliarAtendimento(Integer idAtendimento, Integer notaCliente, String comentarioAvaliacao)
			throws Exception {

		try {
			if (idAtendimento == null || idAtendimento <= 0) {
				throw new Exception("ID do Atendimento inválido!");
			}

			else if (notaCliente == null || notaCliente < 0) {
				throw new Exception("Nota do Cliente inválida!");
			}

			else if (comentarioAvaliacao == null || comentarioAvaliacao.trim() == "") {
				throw new Exception("Comentário do Cliente inválido!");
			}

			AtendimentoEntity atendimentoEntity = atendimentoRepository.findById(idAtendimento);

			if (atendimentoEntity == null) {
				throw new Exception("Solicitação de Atendimento não encontrada!");
			}

			else {

				Integer statusAtual = atendimentoEntity.getStatus();

				if (statusAtual == STATUS.FINALIZADO_SEM_AVALIACAO_E_SEM_CONFIRMACAO_CONTRATANTE) {

					atendimentoEntity.setStatus(STATUS.FINALIZADO_COM_AVALIACAO);
					atendimentoEntity.setNotaCliente(notaCliente);
					atendimentoEntity.setDataAvaliacao(new Date());
					atendimentoEntity.setComentarioAvaliacao(comentarioAvaliacao);

					atendimentoEntity.setNotificado("N");
					atendimentoRepository.save(atendimentoEntity);

					UsuarioEntity usuarioContratanteEntity = usuarioRepository
							.findById(atendimentoEntity.getIdContratante());

					if (usuarioContratanteEntity == null) {
						throw new Exception("O Usuário Contratante não foi encontrado!");
					}

					// Quando o contratante avalia o atendimento, o status do
					// mesmo volta pra 0
					usuarioContratanteEntity.setStatus(0);
					usuarioRepository.save(usuarioContratanteEntity);
				}

				else {
					throw new Exception("Não foi possível finalizar a solicitação de atendimento! (STATUS ATUAL: "
							+ statusAtual + ")");
				}
			}
		}

		catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	public void contratarAtendimento(Integer idContratante, Integer idUsuarioTipoServico) throws Exception {

		try {

			// Validando o Contratante
			if (idContratante == null || idContratante <= 0) {
				throw new Exception("ID do Contratante inválido!");
			}

			UsuarioEntity usuarioContratanteEntity = usuarioRepository.findById(idContratante);

			if (usuarioContratanteEntity == null) {
				throw new Exception("Contratante não encontrado!");
			}

			else if (usuarioContratanteEntity.getStatus() == 5) {
				throw new Exception("Você não está disponível para contratar serviços!");
			}

			// Validando o UsuarioTipoServico
			if (idUsuarioTipoServico == null || idUsuarioTipoServico <= 0) {
				throw new Exception("ID do Tipo de Serviço do Usuário inválido!");
			}

			UsuarioTipoServicoEntity usuarioTipoServicoEntity = usuarioTipoServicoRepository
					.findById(idUsuarioTipoServico);

			if (usuarioTipoServicoEntity == null) {
				throw new Exception("Tipo de Serviço do Usuário não encontrado!");
			}

			// Validando o Prestador
			UsuarioEntity usuarioPrestadorEntity = usuarioRepository
					.findById(usuarioTipoServicoEntity.getIdPrestador());

			if (usuarioPrestadorEntity == null) {
				throw new Exception("Prestador não encontrado!");
			}

			else if (usuarioPrestadorEntity.getStatus() != 1) {
				throw new Exception("O prestador não está disponível para contratar serviços!");
			}

			// Validando o Tipo de Serviço
			TipoServicoEntity tipoServicoEntity = tipoServicoRepository
					.findById(usuarioTipoServicoEntity.getIdTipoServico());

			if (tipoServicoEntity == null) {
				throw new Exception("Tipo de Serviço não encontrado!");
			}

			if (workUpDAO.jaSolicitouAtendimento(idContratante, usuarioTipoServicoEntity.getIdPrestador(),
					usuarioTipoServicoEntity.getIdTipoServico())) {
				throw new Exception("Você já solicitou esse serviço!");
			}

			else {

				// Contratando serviço
				AtendimentoEntity atendimentoEntity = new AtendimentoEntity();

				atendimentoEntity.setIdContratante(idContratante);
				atendimentoEntity.setIdPrestador(usuarioTipoServicoEntity.getIdPrestador());
				atendimentoEntity.setIdTipoServico(usuarioTipoServicoEntity.getIdTipoServico());
				atendimentoEntity.setDataSolicitacao(new Date());
				atendimentoEntity.setValor(usuarioTipoServicoEntity.getValor());

				atendimentoEntity.setStatus(0);

				atendimentoEntity.setNotificado("N");
				atendimentoRepository.save(atendimentoEntity);
			}
		}

		catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	// API que valida se existe ou não solicitações de atendimento de acordo com
	// o usuário.
	public ResponseEntity<List<Long>> getListaSolicitacaoAtendimento(Integer idUsuario) throws Exception {

		try {
			if (idUsuario == null || idUsuario <= 0) {
				throw new Exception("ID de Usuário inválido!");
			}

			UsuarioEntity usuarioEntity = usuarioRepository.findById(idUsuario);

			if (usuarioEntity == null) {
				throw new Exception("Usuário não encontrado!");
			}

			else {
				usuarioEntity.setDataUltimoAcesso(new Date());
				usuarioRepository.save(usuarioEntity);
			}

			List<Long> listaIdAtendimento = workUpDAO.getListaSolicitacaoAtendimento(idUsuario);

			return ResponseEntity.status(HttpStatus.OK).body(listaIdAtendimento);
		}

		catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	public void putListaSolicitacaoAtendimento(List<Integer> listaIdAtendimento) throws Exception {

		try {
			if (listaIdAtendimento == null || listaIdAtendimento.size() <= 0) {
				throw new Exception("Lista de Atendimento inválida ou vazia!");
			}

			for (Integer id : listaIdAtendimento) {

				AtendimentoEntity atendimentoEntity = atendimentoRepository.findById(id);

				atendimentoEntity.setStatus(1);

				atendimentoEntity.setNotificado("N");
				atendimentoRepository.save(atendimentoEntity);
			}

		}

		catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	// STATUS

	private interface STATUS {
		public static int NAO_NOTIFICADO = 0;
		public static int NOTIFICADO_E_AGUARDANDO_RESPOSTA = 1;
		public static int EM_ANDAMENTO = 2;
		public static int RECUSADO_PELO_PRESTADOR = 3;
		public static int FINALIZADO_SEM_AVALIACAO_E_SEM_CONFIRMACAO_CONTRATANTE = 4;
		public static int FINALIZADO_COM_AVALIACAO = 5;
		public static int NAO_EFETUADO_E_RECUSADO_PELO_CONTRATANTE = 6;
		public static int CANCELADO_PELO_SISTEMA = 7;
		public static int CANCELADO_PELO_CONTRATANTE = 8;
		public static int CANCELADO_PELO_PRESTADOR = 9;
	}

	public void consultarAtendimentosCancelamento() throws Exception {

		try {
			List<AtendimentoVO> listaAtendimentoVO = workUpDAO.getListaAtendimentoCancelamento();

			for (AtendimentoVO aVO : listaAtendimentoVO) {
				cancelarAtendimento(aVO.getId(), -1,
						"O sistema cancelou a solicitação de atendimento automaticamente devido à demora de resposta do prestador.");
			}
		}

		catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	// API que retorna as listas de atendimentos pendentes de notificação por parte do Contratante
	public ResponseEntity<List<Long>> getListaAtendimentoNaoNotificado(Integer idContratante, char tipo, int status) throws Exception {

		try {
			if (idContratante == null || idContratante <= 0) {
				throw new Exception("ID de Usuário inválido!");
			}

			UsuarioEntity usuarioEntity = usuarioRepository.findById(idContratante);

			if (usuarioEntity == null) {
				throw new Exception("Usuário não encontrado!");
			}

			else {
				usuarioEntity.setDataUltimoAcesso(new Date());
				usuarioRepository.save(usuarioEntity);
			}

			List<Long> listaIdAtendimento = workUpDAO.getListaAtendimentoNaoNotificadoContratante(idContratante, tipo, status);

			return ResponseEntity.status(HttpStatus.OK).body(listaIdAtendimento);
		}

		catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
	

	public void putListaAtendimentoNotificado(List<Integer> listaIdAtendimento) throws Exception {

		try {
			if (listaIdAtendimento == null || listaIdAtendimento.size() <= 0) {
				throw new Exception("Lista de Atendimento inválida ou vazia!");
			}

			for (Integer id : listaIdAtendimento) {

				AtendimentoEntity atendimentoEntity = atendimentoRepository.findById(id);

				atendimentoEntity.setNotificado("S");
				atendimentoRepository.save(atendimentoEntity);
			}

		}

		catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
}

