package com.workup.business;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.workup.dao.WorkUpDAO;
//import com.workup.entity.TipoServicoEntity;
import com.workup.entity.UsuarioEntity;
import com.workup.entity.UsuarioTipoServicoEntity;
//import com.workup.repository.TipoServicoRepository;
import com.workup.repository.UsuarioRepository;
import com.workup.repository.UsuarioTipoServicoRepository;
import com.workup.vo.CategoriaVO;
import com.workup.vo.UsuarioVO;

import sun.misc.BASE64Decoder;

@SuppressWarnings("restriction")
@Component
public class UsuarioBusiness {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private UsuarioTipoServicoRepository usuarioTipoServicoRepository;

	@Autowired
	private CategoriaBusiness categoriaBusiness;

	// @Autowired
	// private TipoServicoRepository tipoServicoRepository;

	@Autowired
	private WorkUpDAO workUpDAO;

	public ResponseEntity<UsuarioVO> getUsuario(Integer idUsuario) throws Exception {

		try {
			if (idUsuario == null || idUsuario <= 0) {
				throw new Exception("ID de usuário inválido!");
			}

			UsuarioEntity usuarioEntity = usuarioRepository.findById(idUsuario);

			if (usuarioEntity == null) {
				throw new Exception("Usuário não encontrado!");
			}

			else {

				UsuarioVO usuarioVO = prepararUsuario(usuarioEntity);
				return ResponseEntity.status(HttpStatus.OK).body(usuarioVO);
			}
		}

		catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	private UsuarioVO prepararUsuario(UsuarioEntity usuarioEntity) throws SQLException {

		UsuarioVO usuarioVO = new UsuarioVO();

		usuarioVO.setId(usuarioEntity.getId());
		usuarioVO.setNome(usuarioEntity.getNome());
		usuarioVO.setSobrenome(usuarioEntity.getSobrenome());
		usuarioVO.setSexo(usuarioEntity.getSexo());
		usuarioVO.setDataNascimento(usuarioEntity.getDataNascimento());
		usuarioVO.setUsername(usuarioEntity.getUsername());
		usuarioVO.setSenha(usuarioEntity.getSenha());
		usuarioVO.setTipo(usuarioEntity.getTipo());
		usuarioVO.setFoto(usuarioEntity.getFoto());
		usuarioVO.setCpfCnpj(usuarioEntity.getCpfCnpj());
		usuarioVO.setEmail(usuarioEntity.getEmail());
		usuarioVO.setTelefone(usuarioEntity.getTelefone());
		usuarioVO.setCelular(usuarioEntity.getCelular());
		usuarioVO.setEndereco(usuarioEntity.getEndereco());
		usuarioVO.setNumero(usuarioEntity.getNumero());
		usuarioVO.setBairro(usuarioEntity.getBairro());
		usuarioVO.setComplemento(usuarioEntity.getComplemento());
		usuarioVO.setCidade(usuarioEntity.getCidade().toUpperCase());
		usuarioVO.setUf(usuarioEntity.getUf());
		usuarioVO.setCep(usuarioEntity.getCep());
		usuarioVO.setPais(usuarioEntity.getPais());
		usuarioVO.setHomepage(usuarioEntity.getHomepage());
		usuarioVO.setDataCadastro(usuarioEntity.getDataCadastro());
		usuarioVO.setDataUltimoAcesso(usuarioEntity.getDataUltimoAcesso());
		usuarioVO.setStatus(usuarioEntity.getStatus());
		usuarioVO.setLogado(usuarioEntity.getLogado());

		usuarioVO.setMediaGeral(workUpDAO.getMediaGeralPrestador(usuarioEntity.getId()));
		usuarioVO.setQtdAtendimentosRealizados(workUpDAO.getQuantidadeAtendimentosRealizados(usuarioEntity.getId()));

		return usuarioVO;
	}

	public ResponseEntity<UsuarioVO> loginUsuario(String username, String senha) throws Exception {

		try {

			if (username == null || username.trim() == "") {
				throw new Exception("Parâmetro de username inválido!");
			}

			else if (senha == null || senha.trim() == "") {
				throw new Exception("Parâmetro de senha inválido!");
			}

			UsuarioEntity usuarioEntity = usuarioRepository.findByUsername(username);

			if (usuarioEntity == null) {
				throw new Exception("Credenciais de acesso inválidas!");
			}

			else if (!usuarioEntity.getSenha().equals(senha)) {
				throw new Exception("Credenciais de acesso inválidas!");
			}

			else {

				// Descomentar!
				/*
				 * if (usuarioEntity.isLogado() == true) { throw new Exception(
				 * "O usuário já se encontra logado em outro aparelho!"); }
				 * 
				 * else
				 */ if (usuarioEntity.getStatus() == 9) {
					throw new Exception("Essa conta foi excluída!");
				}

				else {

					UsuarioVO usuarioVO = new UsuarioVO();

					usuarioVO.setId(usuarioEntity.getId());
					usuarioVO.setNome(usuarioEntity.getNome());
					usuarioVO.setSobrenome(usuarioEntity.getSobrenome());
					// usuarioVO.setUsername(usuarioEntity.getUsername());

					usuarioEntity.setLogado(true);

					usuarioRepository.save(usuarioEntity);

					return ResponseEntity.status(HttpStatus.OK).body(usuarioVO);
				}
			}
		}

		catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	public int postUsuario(UsuarioEntity usuarioEntity) throws Exception {

		try {

			if (usuarioEntity == null) {
				throw new Exception("Entidade de Usuário nula!");
			}

			if (usuarioEntity.getId() != 0) {
				throw new Exception("Você não pode salvar um novo usuário pelo fato da entidade já possuir um código!");
			}

			else {

				List<UsuarioEntity> listaUsuarioEntityAuxCpfCnpj = usuarioRepository.findByCpfCnpj(usuarioEntity.getCpfCnpj());

				// Validar de existe algum usuário com o mesmo CPF/CNPJ e que
				// esteja com status != 9
				// OBS: O CPF/CNPJ não é unique pelo fato de existir a exclusão
				// lógica (status 9) na tabela de Usuario
				// É bom gravar o CPF/CNPJ dos antigos usuários por questão de
				// análise de histórico
				if (listaUsuarioEntityAuxCpfCnpj != null && listaUsuarioEntityAuxCpfCnpj.size() > 0) {

					for (UsuarioEntity usuarioEntityAux : listaUsuarioEntityAuxCpfCnpj) {

						if (!usuarioEntityAux.getStatus().equals(9)) {
							throw new Exception("CPF/CNPJ já existente!");
						}
					}

				}

				if(usuarioEntity.getTipo().equals("F") && !isCPF(usuarioEntity.getCpfCnpj())){
					throw new Exception("CPF inválido!");
				}

				else if(usuarioEntity.getTipo().equals("J") && !isCNPJ(usuarioEntity.getCpfCnpj())){
					throw new Exception("CNPJ inválido!");
				}
				
				List<UsuarioEntity> listaUsuarioEntityAuxEmail = usuarioRepository
						.findByEmail(usuarioEntity.getEmail());

				// Validar de existe algum usuário com o mesmo email e que
				// esteja com status != 9
				// OBS: O email não é unique pelo fato de existir a exclusão
				// lógica (status 9) na tabela de Usuario
				// É bom gravar o email dos antigos usuários por questão de
				// análise de histórico
				if (listaUsuarioEntityAuxEmail != null && listaUsuarioEntityAuxEmail.size() > 0) {

					for (UsuarioEntity usuarioEntityAux : listaUsuarioEntityAuxEmail) {

						if (!usuarioEntityAux.getStatus().equals(9)) {
							throw new Exception("E-mail já existente!");
						}
					}

				}

				if(!isEmailValido(usuarioEntity.getEmail())){
					throw new Exception("E-mail inválido!");
				}
				
				// OBS: No caso do username, ele é UNIQUE. Independente do caso
				// ele nunca poderá ser repetido.
				UsuarioEntity usuarioEntityAuxUsername = usuarioRepository.findByUsername(usuarioEntity.getUsername());

				if (usuarioEntityAuxUsername != null) {
					throw new Exception("Username já existente!");
				}

				// Incrementando um dia na Data de Nascimento
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(usuarioEntity.getDataNascimento());
				calendar.add(Calendar.DATE, 1);

				if(calendar.getTime().getTime() > new Date().getTime()){
					throw new Exception("Data de nascimento inválida!");
				}
				
				usuarioEntity.setDataNascimento(calendar.getTime());

				// Data de Cadastro
				usuarioEntity.setDataCadastro(new Date());

				// Se for Jurídico seta sexo para nulo
				if (usuarioEntity.getTipo() == "J") {
					usuarioEntity.setSexo(null);
				}

				usuarioEntity.setLogado(false);
				usuarioEntity.setStatus(0);

				if (usuarioEntity.getTipo().equals("J")) {
					usuarioEntity.setSexo(null);
				}

				usuarioRepository.save(usuarioEntity);

				return usuarioEntity.getId();
			}
		}

		catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	public void putUsuario(UsuarioEntity pUsuario) throws Exception {

		try {
			if (pUsuario == null) {
				throw new Exception("Parâmetro de Entidade de Usuário inválido!");
			}

			UsuarioEntity usuarioEntity = usuarioRepository.findById(pUsuario.getId());

			if (usuarioEntity == null) {
				throw new Exception("Usuário não encontrado!");
			}

			else {

				// Atributos editados

				if (pUsuario.getDataNascimento() != null) {

					// Incrementando um dia
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(pUsuario.getDataNascimento());
					calendar.add(Calendar.DATE, 1);

					if(calendar.getTime().getTime() > new Date().getTime()){
						throw new Exception("Data de nascimento inválida!");
					}
					
					usuarioEntity.setDataNascimento(calendar.getTime());
				}

				if (pUsuario.getNome() != null) {
					usuarioEntity.setNome(pUsuario.getNome());
				}

				if (pUsuario.getSobrenome() != null) {
					usuarioEntity.setSobrenome(pUsuario.getSobrenome());
				}

				if (pUsuario.getSexo() != null) {
					usuarioEntity.setSexo(pUsuario.getSexo());
				}

				if (pUsuario.getEmail() != null) {

					List<UsuarioEntity> listaUsuarioEntityAuxEmail = usuarioRepository.findByEmail(pUsuario.getEmail());

					// Validar de existe algum usuário com o mesmo email e que
					// esteja com status != 9
					// OBS: O email não é unique pelo fato de existir a exclusão
					// lógica (status 9) na tabela de Usuario
					// É bom gravar o email dos antigos usuários por questão de
					// análise de histórico
					if (listaUsuarioEntityAuxEmail != null && listaUsuarioEntityAuxEmail.size() > 0) {

						for (UsuarioEntity usuarioEntityAux : listaUsuarioEntityAuxEmail) {

							if (!usuarioEntityAux.getStatus().equals(9)
									&& usuarioEntityAux.getId() != pUsuario.getId()) {
								throw new Exception("E-mail já existente!");
							}
						}

					}

					if(!isEmailValido(pUsuario.getEmail())){
						throw new Exception("E-mail inválido!");
					}
					
					usuarioEntity.setEmail(pUsuario.getEmail());
				}

				/*
				 * if (pUsuario.getTipo() != null) {
				 * usuarioEntity.setTipo(pUsuario.getTipo()); }
				 * 
				 * if (pUsuario.getCpfCnpj() != null) {
				 * usuarioEntity.setCpfCnpj(pUsuario.getCpfCnpj()); }
				 */

				usuarioEntity.setHomepage(pUsuario.getHomepage());

				usuarioEntity.setTelefone(pUsuario.getTelefone());

				usuarioEntity.setCelular(pUsuario.getCelular());

				if (pUsuario.getPais() != null) {
					usuarioEntity.setPais(pUsuario.getPais());
				}

				if (pUsuario.getUf() != null) {
					usuarioEntity.setUf(pUsuario.getUf());
				}

				if (pUsuario.getCidade() != null) {
					usuarioEntity.setCidade(pUsuario.getCidade().toUpperCase());
				}

				if (pUsuario.getEndereco() != null) {
					usuarioEntity.setEndereco(pUsuario.getEndereco());
				}

				if (pUsuario.getNumero() != null) {
					usuarioEntity.setNumero(pUsuario.getNumero());
				}

				if (pUsuario.getBairro() != null) {
					usuarioEntity.setBairro(pUsuario.getBairro());
				}

				usuarioEntity.setComplemento(pUsuario.getComplemento());

				if (pUsuario.getCep() != null) {
					usuarioEntity.setCep(pUsuario.getCep());
				}

				if (pUsuario.getStatus() != null) {
					usuarioEntity.setStatus(pUsuario.getStatus());
				}

				if (pUsuario.getLogado() != null) {
					usuarioEntity.setLogado(pUsuario.getLogado());
				}

				if (pUsuario.getSenha() != null) {
					usuarioEntity.setSenha(pUsuario.getSenha());
				}

				// usuarioEntity.setUsername(pUsuario.getUsername());

				usuarioRepository.save(usuarioEntity);
			}
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
	
	public void alterarDisponibilidadeUsuario(Integer idUsuario, Integer statusAlteracao) throws Exception {

		try {

			if (idUsuario == null || idUsuario <= 0 || statusAlteracao == null || statusAlteracao < 0) {
				throw new Exception("ID do usuário e/ou STATUS inválido(s)!");
			}

			UsuarioEntity usuarioEntity = usuarioRepository.findById(idUsuario);

			if (usuarioEntity == null) {
				throw new Exception("Usuário não encontrado!");
			}

			else {

				int statusAtual = usuarioEntity.getStatus();

				List<UsuarioTipoServicoEntity> listaUsuarioTipoServico = usuarioTipoServicoRepository
						.findByIdPrestador(idUsuario);

				// Se o prestador não possui nenhum tipo de serviço para
				// oferecer
				if ((listaUsuarioTipoServico == null || listaUsuarioTipoServico.size() <= 0) && statusAtual == 0
						&& statusAlteracao == 1) {
					throw new Exception("Você não cadastrou nenhum tipo de serviço para oferecer atendimento!");
				}

				// Se o prestador se encontra bloqueado para efetuar qualquer
				// outro tipo de atendimento
				else if (statusAtual == 5) {
					throw new Exception(
							"Você não pode alterar sua disponibilidade até finalizar sua atual solicitação de atendimento!");
				}

				else {
					usuarioEntity.setStatus(statusAlteracao);
					usuarioRepository.save(usuarioEntity);
				}
			}
		}

		catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	public void sairOuDeletarContaUsuario(Integer idUsuario, Integer statusAlteracao) throws Exception {

		try {

			if (idUsuario == null || idUsuario <= 0 || statusAlteracao == null || statusAlteracao < 0) {
				throw new Exception("ID do usuário e/ou STATUS inválido(s)!");
			}

			UsuarioEntity usuarioEntity = usuarioRepository.findById(idUsuario);

			if (usuarioEntity == null) {
				throw new Exception("Usuário não encontrado!");
			}

			else {

				int statusAtual = usuarioEntity.getStatus();

				if (statusAtual == 5) {
					throw new Exception(
							"Você não pode efetuar essa ação!\nFavor finalizar sua atual solicitação de atendimento.");
				}

				else {

					// Se for deletar a conta
					if (statusAlteracao == 9) {

						// usuarioEntity.setUsername(null);
						// usuarioEntity.setSenha(null);

						usuarioEntity.setDataExclusao(new Date());

						// Remover os tipos de serviço que o usuário prestava
						categoriaBusiness.putCategoriaUsuario(idUsuario, new CategoriaVO());

					}

					usuarioEntity.setLogado(false);
					usuarioEntity.setStatus(statusAlteracao);

					usuarioRepository.save(usuarioEntity);
				}
			}

		}

		catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	public void atualizarAvatar(int idUsuario, String img, String nome) throws Exception {

		if (img != null && img.contains("\"")) {
			img = img.replace('"', ' ').trim();
		}

		if (idUsuario <= 0) {
			throw new Exception("ID do usuário inválido!");
		}

		UsuarioEntity usuarioEntity = usuarioRepository.findById(idUsuario);

		if (usuarioEntity == null) {
			throw new Exception("Usuário não encontrado!");
		}

		// Apenas deletar o arquivo (caso existir)
		if (nome == null || nome.trim().equals("")) {

			if (usuarioEntity.getFoto() != null && !usuarioEntity.getFoto().trim().equals("")) {

				String arquivoAntigo = "C:\\inetpub\\wwwroot\\img\\" + usuarioEntity.getFoto();
				File f = new File(arquivoAntigo);

				if (f.exists()) {
					f.delete();
				}

				usuarioEntity.setFoto(null);
				usuarioRepository.save(usuarioEntity);
			}
		}

		// Alterar o arquivo
		else if (nome != null && !nome.trim().equals("") && img != null && !img.equals("")) {

			BufferedImage image = null;
			byte[] imageByte;
			try {

				// Gravando o arquivo de imagem no diretório
				BASE64Decoder decoder = new BASE64Decoder();
				imageByte = decoder.decodeBuffer(img);
				ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
				image = ImageIO.read(bis);

				bis.close();

				File outputfile = new File("C:\\inetpub\\wwwroot\\img\\" + nome);
				// File outputfile = new File("C:\\xampp\\htdocs\\img\\" +
				// nome);

				String formato = nome.replace(idUsuario + ".", "");
				ImageIO.write(image, formato, outputfile);

				// Excluindo o arquivo caso ele seja em um formato diferente
				if (usuarioEntity.getFoto() != null && !usuarioEntity.getFoto().trim().equals("")) {

					String formatoAntigo = usuarioEntity.getFoto().replace(idUsuario + ".", "");

					if (!(formatoAntigo.equals(formato))) {

						String arquivoAntigo = "C:\\inetpub\\wwwroot\\img\\" + usuarioEntity.getFoto();
						File f = new File(arquivoAntigo);

						if (f.exists()) {
							f.delete();
						}

					}
				}

				usuarioEntity.setFoto(nome);
				usuarioRepository.save(usuarioEntity);

			} catch (Exception ex) {
				throw new Exception(ex.getMessage());
			}
		}
	}

	public static boolean isEmailValido(String email) {
		if ((email == null) || (email.trim().length() == 0)) {
			return false;
		}
		String emailPattern = "\\b(^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@([A-Za-z0-9-])+(\\.[A-Za-z0-9-]+)*((\\.[A-Za-z0-9]{2,})|(\\.[A-Za-z0-9]{2,}\\.[A-Za-z0-9]{2,}))$)\\b";
		Pattern pattern = Pattern.compile(emailPattern, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	public static boolean isCNPJ(String CNPJ) {
		// considera-se erro CNPJ's formados por uma sequencia de numeros iguais
		if (CNPJ.equals("00000000000000") || CNPJ.equals("11111111111111") || CNPJ.equals("22222222222222")
				|| CNPJ.equals("33333333333333") || CNPJ.equals("44444444444444") || CNPJ.equals("55555555555555")
				|| CNPJ.equals("66666666666666") || CNPJ.equals("77777777777777") || CNPJ.equals("88888888888888")
				|| CNPJ.equals("99999999999999") || (CNPJ.length() != 14))
			return (false);

		char dig13, dig14;
		int sm, i, r, num, peso;

		// "try" - protege o código para eventuais erros de conversao de tipo
		// (int)
		try {
			// Calculo do 1o. Digito Verificador
			sm = 0;
			peso = 2;
			for (i = 11; i >= 0; i--) {
				// converte o i-ésimo caractere do CNPJ em um número:
				// por exemplo, transforma o caractere '0' no inteiro 0
				// (48 eh a posição de '0' na tabela ASCII)
				num = (int) (CNPJ.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso + 1;
				if (peso == 10)
					peso = 2;
			}

			r = sm % 11;
			if ((r == 0) || (r == 1))
				dig13 = '0';
			else
				dig13 = (char) ((11 - r) + 48);

			// Calculo do 2o. Digito Verificador
			sm = 0;
			peso = 2;
			for (i = 12; i >= 0; i--) {
				num = (int) (CNPJ.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso + 1;
				if (peso == 10)
					peso = 2;
			}

			r = sm % 11;
			if ((r == 0) || (r == 1))
				dig14 = '0';
			else
				dig14 = (char) ((11 - r) + 48);

			// Verifica se os dígitos calculados conferem com os dígitos
			// informados.
			if ((dig13 == CNPJ.charAt(12)) && (dig14 == CNPJ.charAt(13)))
				return (true);
			else
				return (false);
		} catch (InputMismatchException erro) {
			return (false);
		}
	}

	public static boolean isCPF(String pCPF) {
		String CPF = pCPF.replace(".", "").replace("-", "").trim();
		// considera-se erro CPF's formados por uma sequencia de numeros iguais
		if (CPF.equals("00000000000") || CPF.equals("11111111111") || CPF.equals("22222222222")
				|| CPF.equals("33333333333") || CPF.equals("44444444444") || CPF.equals("55555555555")
				|| CPF.equals("66666666666") || CPF.equals("77777777777") || CPF.equals("88888888888")
				|| CPF.equals("99999999999") || (CPF.length() != 11)) {
			return (false);
		}

		char dig10, dig11;
		int sm, i, r, num, peso;

		// "try" - protege o codigo para eventuais erros de conversao de tipo
		// (int)
		try {
			// Calculo do 1o. Digito Verificador
			sm = 0;
			peso = 10;
			for (i = 0; i < 9; i++) {
				// converte o i-esimo caractere do CPF em um numero:
				// por exemplo, transforma o caractere '0' no inteiro 0
				// (48 eh a posicao de '0' na tabela ASCII)
				num = (int) (CPF.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11)) {
				dig10 = '0';
			} else {
				dig10 = (char) (r + 48); // converte no respectivo caractere
											// numerico
			}
			// Calculo do 2o. Digito Verificador
			sm = 0;
			peso = 11;
			for (i = 0; i < 10; i++) {
				num = (int) (CPF.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11)) {
				dig11 = '0';
			} else {
				dig11 = (char) (r + 48);
			}

			// Verifica se os digitos calculados conferem com os digitos
			// informados.
			if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10))) {
				return (true);
			} else {
				return (false);
			}
		} catch (Exception erro) {
			return (false);
		}
	}
}
