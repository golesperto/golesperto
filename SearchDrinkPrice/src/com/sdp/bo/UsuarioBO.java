package com.sdp.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.sdp.entity.Empresa;
import com.sdp.entity.Usuario;
import com.sdp.exception.SenhaInvalidaException;
import com.sdp.exception.TechnicalException;
import com.sdp.exception.UsuarioBloqueadoException;
import com.sdp.exception.UsuarioNaoEncontradoException;
import com.sdp.util.CryptMD5;
import com.sdp.util.GeradorSenha;
import com.sdp.util.Gmail;
import com.sdp.util.Mensagens;
import com.sdp.util.Status;
import com.sdp.util.Utils;

public class UsuarioBO extends BO<Usuario> {
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(UsuarioBO.class);

	public UsuarioBO() {
		super();
		setClazz(Usuario.class);
	}

	public List<Usuario> listAtivos() throws Exception {
		List<Usuario> usuario = new ArrayList<Usuario>();
		String jpql = "select u from Usuario u where u.usuChaStatus != 'B' order by u.usuIntCod";
		Query query = getDao().createQuery(jpql);
		usuario = list(query);
		return usuario;
	}

	public Usuario findUsuarioByLoginOrEmail(String usuStrLogin, String usuStrEmail) {
		try {
			String jpql = "select distinct(u) from Usuario u where upper(u.usuStrLogin)=:usuStrLogin or upper(u.usuStrEmail)=:usuStrEmail";
			Query query = getDao().createQuery(jpql);
			query.setParameter("usuStrLogin", usuStrLogin.toUpperCase());
			query.setParameter("usuStrEmail", usuStrEmail);
			List<Usuario> users = list(query);
			if (users != null && users.size() > 0) {
				return users.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Erro no getUsuarioByLogin", e);
		}
		return null;
	}

	public Usuario criarUsuario(Usuario usuario) {
		try {
			String login = Utils.substituirCaracterEspecial(gerarLogin(usuario.getUsuStrNome()));
			String senha = Mensagens.getMensagem(Mensagens.SENHA_PADRAO);
			usuario.setUsuStrSenha(CryptMD5.encrypt(login.toUpperCase(), senha.toUpperCase()));
			usuario.setUsuStrLogin(login.toUpperCase());
			persist(usuario);
			enviarEmailNovoUsuario(usuario, senha);
			return usuario;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String gerarLogin(String nomeUsuario) {
		try {
			String login = "";
			String sobrenome = obtemSobrenome(nomeUsuario);
			String nome = nomeUsuario.replace(" ", "");
			int cont = 0;
			int qtdCaracter = 1;
			boolean prefixo = true;
			while (cont <= nome.trim().length() - qtdCaracter) {
				login = nome.substring(cont, cont + qtdCaracter);
				if (prefixo)
					login += sobrenome;
				else
					login = sobrenome + login;
				login = (login.trim()).toUpperCase();
				if (login.length() > 20) {
					login = "";
					break;
				}
				if (existeLogin(login))
					break;

				++cont;
				if (cont > nome.trim().length() - qtdCaracter) {
					if (!prefixo)
						qtdCaracter++;
					prefixo = !prefixo;
					cont = 0;
					login = "";
				}
			}
			return login;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private String obtemSobrenome(String usuStrNme) {
		int tamanho = usuStrNme.length();
		String sobrenome = "";
		try {
			while (!(usuStrNme.substring(tamanho - 1, tamanho).equalsIgnoreCase(" ")))
				--tamanho;
			sobrenome = usuStrNme.substring(tamanho, usuStrNme.length());

		} catch (Exception e) {

		}
		return (sobrenome);
	}

	public Boolean existeLogin(String login) {
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("UPPER(o.usuStrLogin) = '" + login.toUpperCase() + "'", BO.FILTRO_GENERICO_QUERY);
			Usuario usuarioBanco = super.findByFields(param);
			return usuarioBanco == null ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void enviarEmailNovoUsuario(Usuario usuario, String senha) {
		String conteudo = Mensagens.getMensagem("novo.usuario.senha.conteudo");
		conteudo = conteudo.replace("[login]", usuario.getUsuStrLogin());
		conteudo = conteudo.replace("[senha]", senha);
		Gmail.getInstance(usuario.getUsuStrEmail(), Mensagens.getMensagem("novo.usuario.senha.titulo"), conteudo)
				.enviarEmail();
	}

	public String doSenhaPadrao(Usuario usuario, boolean enviaEmail) throws Exception {
		String senhaPadrao = GeradorSenha.gerarSenha().toUpperCase();
		System.out.println(senhaPadrao);
		usuario.setUsuStrSenha(CryptMD5.encrypt(usuario.getUsuStrLogin().toUpperCase(), senhaPadrao));
		enviarEmailComSenhaPadrao(usuario, senhaPadrao, enviaEmail);
		return senhaPadrao;
	}

	private void enviarEmailComSenhaPadrao(Usuario usuario, String senhaPadrao, boolean enviaEmail) throws Exception {
		if (enviaEmail) {
			String corpo = "Seu login : " + usuario.getUsuStrLogin() + "\n";
			corpo += "Sua senha de acesso : " + senhaPadrao + "\n";
			log.debug(corpo);
			Gmail.getInstance(usuario.getUsuStrEmail(), "Sua senha de acesso.", corpo).enviarEmail();
		}
	}

	public Usuario findByEmail(String email) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("UPPER(o.usuStrEmail) = '" + email.toUpperCase() + "'", BO.FILTRO_GENERICO_QUERY);
		Usuario usuarioBanco = super.findByFields(param);
		return usuarioBanco;
	}

	public List<Usuario> getAllUsuariosByEmpresa(Empresa empresa) {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		if (empresa != null) {
			try {
				String jpql = "select u from Usuario u where u.empresa.empIntCod =:empIntCod order by u.usuStrNome";
				Query query = getDao().createQuery(jpql);
				query.setParameter("empIntCod", empresa.getEmpIntCod());
				usuarios = list(query);
			} catch (Exception e) {
				log.error("Erro no getAllUsuariosByEmpresa", e);
			}
		}
		return usuarios;
	}

	public List<Usuario> listByIdEmpresaAndStatus(Integer empIntCod, Character usuChaStatus) {
		try {
			String jpql = "select u from Usuario u where u.empresa.empIntCod =:empIntCod and u.usuChaStatus =:usuChaStatus order by u.usuStrNome asc";
			Query q = getDao().createQuery(jpql);
			q.setParameter("empIntCod", empIntCod);
			q.setParameter("usuChaStatus", usuChaStatus);
			return q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public String resetSenha(String email) throws Exception {
		Usuario usu = findByEmail(email);
		if (usu != null) {
			try {
				String senha = GeradorSenha.gerarSenha();
				usu.setUsuStrSenha(CryptMD5.encrypt(usu.getUsuStrLogin().toUpperCase(), senha.toUpperCase()));
				merge(usu);
				String conteudo = Mensagens.getMensagem("recuperacao.conteudo");
				conteudo = conteudo.replace("[senha]", senha);
				Gmail.getInstance(usu.getUsuStrEmail(), Mensagens.getMensagem("recuperacao.titulo"), conteudo)
						.enviarEmail();
				return senha;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			throw new UsuarioNaoEncontradoException();
		}
		return null;
	}

	public Usuario getUsuarioByLogin(Usuario usuario) {
		if (usuario != null) {
			try {
				String jpql = "select distinct(u) from Usuario u where u.usuStrLogin=:usuStrLogin ";
				Query query = getDao().createQuery(jpql);
				query.setParameter("usuStrLogin", usuario.getUsuStrLogin());
				List<Usuario> users = list(query);
				if (users != null && users.size() > 0) {
					return users.get(0);
				}
			} catch (Exception e) {
				log.error("Erro no getUsuarioByLogin", e);
			}
		}
		return null;
	}

	public Usuario findByLogin(String usuStrLogin) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("UPPER(o.usuStrLogin) = '" + usuStrLogin.toUpperCase() + "'", BO.FILTRO_GENERICO_QUERY);
		Usuario usuarioBanco = super.findByFields(param);
		return usuarioBanco;
	}

	public Usuario login(String login, String senha) throws UsuarioNaoEncontradoException, UsuarioBloqueadoException,
			SenhaInvalidaException, TechnicalException {
		Usuario usu = buscarPorLogin(login);
		if (usu == null)
			throw new UsuarioNaoEncontradoException();
		else if (usu.getUsuChaStatus().equals(Status.STATUS_BLOQUEADO))
			throw new UsuarioBloqueadoException();
		else if (senha == null || !usu.getUsuStrSenha()
				.equals(CryptMD5.encrypt(usu.getUsuStrLogin().toUpperCase(), senha.toUpperCase())))
			throw new SenhaInvalidaException();
		return usu;
	}

	public Usuario loginCrypt(String usuStrLogin, String usuStrSenha) throws UsuarioNaoEncontradoException,
			UsuarioBloqueadoException, SenhaInvalidaException, TechnicalException {
		Usuario usu = buscarPorLogin(usuStrLogin);
		if (usu == null)
			throw new UsuarioNaoEncontradoException();
		else if (usu.getUsuChaStatus().equals(Status.STATUS_BLOQUEADO))
			throw new UsuarioBloqueadoException();
		else if (usuStrSenha == null || !usu.getUsuStrSenha().equals(usuStrSenha))
			throw new SenhaInvalidaException();
		return usu;
	}

	public Usuario buscarPorLogin(String usuStrLogin) throws TechnicalException, UsuarioNaoEncontradoException {
		if (usuStrLogin == null)
			throw new UsuarioNaoEncontradoException();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("usuStrLogin", usuStrLogin.toUpperCase());
		return super.findByFields(param);
	}
}
