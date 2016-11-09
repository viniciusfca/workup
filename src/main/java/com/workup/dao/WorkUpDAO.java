package com.workup.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
//import java.util.ArrayList;
//import java.util.List;
import java.util.List;

import org.springframework.stereotype.Component;

import com.workup.util.Conexao;
import com.workup.vo.AtendimentoVO;
import com.workup.vo.UsuarioTipoServicoVO;

@Component
public class WorkUpDAO {

	/***
	 * Esse método retorna a média de nota geral de um prestador baseado nos
	 * tipos de serviço que ele já prestou e teve como status de atendimento
	 * igual a 5 (finalizado e com avaliação).
	 * 
	 * @param idPrestador
	 * @return
	 * @throws SQLException
	 */
	public Float getMediaGeralPrestador(int idPrestador) throws SQLException {

		Conexao conexao = new Conexao();

		PreparedStatement ps = null;
		Float notaMediaGeral = 0f;
		ResultSet rs = null;

		try {
			ps = conexao.conectar().prepareStatement(
					"SELECT distinct idprestador, nome as nomePrestador, SUM(notacliente) / COUNT(notacliente) AS media "
							+ " FROM  atendimento inner join  usuario ON  atendimento.idPrestador =  usuario.id "
							+ " where  atendimento.status = 5 and idprestador = ? GROUP BY idPrestador, nome ORDER BY media DESC");

			ps.setInt(1, idPrestador);

			rs = ps.executeQuery();

			// Tem de retornar apenas uma linha
			if (rs.next()) {
				notaMediaGeral = rs.getFloat("media");
			}

			return notaMediaGeral;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new SQLException(e.getMessage());
		} finally {
			rs.close();
			ps.close();
			conexao.desconectar();
		}

	}

	public Long getQuantidadeAtendimentosRealizados(int idPrestador) throws SQLException {

		Conexao conexao = new Conexao();

		PreparedStatement ps = null;
		Long qtdAtendimentosRealizados = 0l;
		ResultSet rs = null;

		try {
			ps = conexao.conectar().prepareStatement(
					"SELECT COUNT(*) AS qtd FROM  atendimento inner join  usuario ON  atendimento.idPrestador =  usuario.id "
							+ " where  atendimento.status = 5 and idprestador = ? GROUP BY idPrestador");

			ps.setInt(1, idPrestador);

			rs = ps.executeQuery();

			// Tem de retornar apenas uma linha
			if (rs.next()) {
				qtdAtendimentosRealizados = rs.getLong("qtd");
			}

			return qtdAtendimentosRealizados;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new SQLException(e.getMessage());
		} finally {
			rs.close();
			ps.close();
			conexao.desconectar();
		}

	}

	public boolean getEstouRealizandoAtendimento(int idUsuario) throws SQLException {

		Conexao conexao = new Conexao();

		PreparedStatement ps = null;
		boolean retorno = false;
		ResultSet rs = null;

		try {
			ps = conexao.conectar()
					.prepareStatement("select distinct id from atendimento where status = 2 and idPrestador = ?");

			ps.setInt(1, idUsuario);

			rs = ps.executeQuery();

			if (rs.next()) {
				retorno = true;
			}

			return retorno;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new SQLException(e.getMessage());
		} finally {
			rs.close();
			ps.close();
			conexao.desconectar();
		}

	}

	public boolean getEstouRecebendoAtendimento(int idUsuario) throws SQLException {

		Conexao conexao = new Conexao();

		PreparedStatement ps = null;
		boolean retorno = false;
		ResultSet rs = null;

		try {
			ps = conexao.conectar()
					.prepareStatement("select distinct id from atendimento where status = 2 and idContratante = ?");

			ps.setInt(1, idUsuario);

			rs = ps.executeQuery();

			if (rs.next()) {
				retorno = true;
			}

			return retorno;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new SQLException(e.getMessage());
		} finally {
			rs.close();
			ps.close();
			conexao.conectar().close();
		}

	}

	public boolean jaSolicitouAtendimento(int idContratante, int idPrestador, int idTipoServico) throws SQLException {

		Conexao conexao = new Conexao();

		PreparedStatement ps = null;
		boolean retorno = false;
		ResultSet rs = null;

		try {
			ps = conexao.conectar().prepareStatement(
					"select 1 from atendimento where status in (0,1) and idContratante = ? and idPrestador = ? and idTipoServico = ?");

			ps.setInt(1, idContratante);
			ps.setInt(2, idPrestador);
			ps.setInt(3, idTipoServico);

			rs = ps.executeQuery();

			if (rs.next()) {
				retorno = true;
			}

			return retorno;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new SQLException(e.getMessage());
		} finally {
			rs.close();
			ps.close();
			conexao.desconectar();
		}

	}

	public List<Long> getListaSolicitacaoAtendimento(int idUsuario) throws SQLException {

		Conexao conexao = new Conexao();

		List<Long> listaIdAtendimento = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conexao.conectar().prepareStatement("select id from atendimento where status = 0 and idPrestador = ?");

			ps.setInt(1, idUsuario);

			rs = ps.executeQuery();

			while (rs.next()) {
				listaIdAtendimento.add(rs.getLong("id"));
			}

			return listaIdAtendimento;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new SQLException(e.getMessage());
		} finally {
			rs.close();
			ps.close();
			conexao.desconectar();
		}

	}

	public List<AtendimentoVO> getListaAtendimentoCancelamento() throws SQLException {

		Conexao conexao = new Conexao();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.HOUR, -1);

		List<AtendimentoVO> listaAtendimentoVO = new ArrayList<AtendimentoVO>();

		Date data = calendar.getTime();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strData = dateFormat.format(data);

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conexao.conectar().prepareStatement(
					"select * from atendimento where status in (0,1) and datasolicitacao <= '" + strData + "'");

			rs = ps.executeQuery();

			// Tem de retornar apenas uma linha
			while (rs.next()) {
				AtendimentoVO atendimentoVO = new AtendimentoVO();

				atendimentoVO.setId(rs.getInt("id"));
				atendimentoVO.setIdContratante(rs.getInt("idcontratante"));
				atendimentoVO.setIdPrestador(rs.getInt("idprestador"));
				atendimentoVO.setIdTipoServico(rs.getInt("idtiposervico"));
				atendimentoVO.setStatus(rs.getInt("status"));

				listaAtendimentoVO.add(atendimentoVO);
			}

			return listaAtendimentoVO;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new SQLException(e.getMessage());
		} finally {
			rs.close();
			ps.close();
			conexao.desconectar();
		}

	}

	/***
	 * Esse método retorna a média de nota dos prestadores baseado no tipo de
	 * serviço passado como parâmetro, com status de atendimento igual a 5
	 * (finalizado e com avaliação).
	 * 
	 * @param idPrestador
	 * @return
	 * @throws SQLException
	 */
	public List<UsuarioTipoServicoVO> getListaUsuarioTipoServico(int idUsuario, int idTipoServico, String cidade,
			String uf) throws SQLException {

		Conexao conexao = new Conexao();

		PreparedStatement ps = null;
		List<UsuarioTipoServicoVO> listaUsuarioTipoServicoVO = new ArrayList<UsuarioTipoServicoVO>();
		ResultSet rs = null;

		try {
			ps = conexao.conectar().prepareStatement(
					"select usuariotiposervico.id as idUsuarioTipoServico, usuariotiposervico.idtiposervico, usuariotiposervico.valor, usuario.* from usuariotiposervico inner join usuario on usuario.id = usuariotiposervico.idPrestador where usuario.status = 1 and usuariotiposervico.idtiposervico = ? and usuariotiposervico.idprestador <> ? and usuario.cidade = ? and usuario.uf = ?");

			ps.setInt(1, idTipoServico);
			ps.setInt(2, idUsuario);
			ps.setString(3, cidade);
			ps.setString(4, uf);

			rs = ps.executeQuery();

			while (rs.next()) {

				UsuarioTipoServicoVO usuarioTipoServicoVO = new UsuarioTipoServicoVO();

				usuarioTipoServicoVO.setId(rs.getInt("idUsuarioTipoServico"));
				usuarioTipoServicoVO.setIdPrestador(rs.getInt("id"));
				usuarioTipoServicoVO.setIdTipoServico(rs.getInt("idtiposervico"));
				usuarioTipoServicoVO.setValor(rs.getFloat("valor"));
				usuarioTipoServicoVO.setNomePrestador(rs.getString("nome"));
				usuarioTipoServicoVO.setSobrenomePrestador(rs.getString("sobrenome"));
				usuarioTipoServicoVO.setDataCadastroPrestador(rs.getDate("dataCadastro"));
				usuarioTipoServicoVO.setEmailPrestador(rs.getString("email"));
				usuarioTipoServicoVO.setHomepagePrestador(rs.getString("homepage"));
				usuarioTipoServicoVO.setFotoPrestador(rs.getString("foto"));
				usuarioTipoServicoVO.setMediaGeralPrestador(null);// (rs.getFloat("media"));
				usuarioTipoServicoVO.setQtdAtendimentosRealizados(0l);
				usuarioTipoServicoVO.setTipoPrestador(rs.getString("tipo"));

				usuarioTipoServicoVO.setEndereco(rs.getString("endereco"));
				usuarioTipoServicoVO.setNumero(rs.getLong("numero"));
				usuarioTipoServicoVO.setBairro(rs.getString("bairro"));
				usuarioTipoServicoVO.setCidade(rs.getString("cidade"));
				usuarioTipoServicoVO.setUf(rs.getString("uf"));

				listaUsuarioTipoServicoVO.add(usuarioTipoServicoVO);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new SQLException(e.getMessage());
		} finally {
			rs.close();
			ps.close();
			conexao.desconectar();
		}

		rs = null;
		ps = null;

		try {

			ps = conexao.conectar().prepareStatement(
					"SELECT usuario.id, usuario.nome, SUM(notacliente) / COUNT(notacliente) AS media, count(*) as qtd FROM usuario right join atendimento ON atendimento.idPrestador = usuario.id where usuario.status = 1 and atendimento.status = 5 and atendimento.idtiposervico = ? and atendimento.idprestador <> ? and usuario.cidade = ? and usuario.uf = ? GROUP BY atendimento.idPrestador, atendimento.idTipoServico ORDER BY media DESC");

			ps.setInt(1, idTipoServico);
			ps.setInt(2, idUsuario);
			ps.setString(3, cidade);
			ps.setString(4, uf);

			rs = ps.executeQuery();

			while (rs.next()) {

				for (UsuarioTipoServicoVO usuarioTipoServicoVO : listaUsuarioTipoServicoVO) {

					if (rs.getInt("id") == usuarioTipoServicoVO.getIdPrestador()) {
						usuarioTipoServicoVO.setMediaGeralPrestador(rs.getFloat("media"));
						usuarioTipoServicoVO.setQtdAtendimentosRealizados(rs.getLong("qtd"));
						break;
					}
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new SQLException(e.getMessage());
		} finally {
			rs.close();
			ps.close();
			conexao.desconectar();
		}

		return listaUsuarioTipoServicoVO;
	}

	//

	public List<Long> getListaAtendimentoNaoNotificadoContratante(int idContratante, char tipo, int status)
			throws SQLException {

		Conexao conexao = new Conexao();

		List<Long> listaIdAtendimento = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String str = null;
		
		if (tipo == 'C') {
			str = "select id from atendimento where status = ? and notificado = 'N' and idContratante = ?";
		} else if (tipo == 'P') {
			str = "select id from atendimento where status = ? and notificado = 'N' and idPrestador = ?";
		}
		
		try {

			ps = conexao.conectar().prepareStatement(str);

			ps.setInt(1, status);
			ps.setInt(2, idContratante);

			rs = ps.executeQuery();

			while (rs.next()) {
				listaIdAtendimento.add(rs.getLong("id"));
			}

			return listaIdAtendimento;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new SQLException(e.getMessage());
		} finally {
			rs.close();
			ps.close();
			conexao.desconectar();
		}

	}
}