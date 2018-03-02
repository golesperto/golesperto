package com.sdp.managed;

import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.sdp.bo.LogAcessoBO;
import com.sdp.bo.UsuarioBO;
import com.sdp.entity.Usuario;
import com.sdp.exception.SenhaInvalidaException;
import com.sdp.exception.TechnicalException;
import com.sdp.exception.UsuarioBloqueadoException;
import com.sdp.exception.UsuarioNaoEncontradoException;
import com.sdp.util.JSFHelper;
import com.sdp.util.Mensagens;

@ManagedBean
@RequestScoped
public class LoginMB extends GenericManagedBean<Usuario> {
	private static Logger log = Logger.getLogger(LoginMB.class);

	private LogAcessoBO logAcessoBO;

	public LoginMB() {
		setClazz(Usuario.class);
		setUsuarioLogado(null);
		JSFHelper.getSession().invalidate();
		setBo(new UsuarioBO());
		logAcessoBO = new LogAcessoBO();
		String logoff = JSFHelper.getRequestParameterMap("logoff");
		if (logoff != null && logoff.equals("true")) {
			setUsuarioLogado(null);
			JSFHelper.redirect("login.xhtml");
		}
	}

	private void validaUsuarioLogadoDuplicado(Usuario usuarioLogin) {
		Map<String, Object> context = JSFHelper.getApplicationMap();
		if (!isUsuarioAdministrador()) {
			if (context.get("" + usuarioLogin.getUsuIntCod()) != null) {
				try {
					((HttpSession) context.get("" + usuarioLogin.getUsuIntCod())).invalidate();
					context.remove("" + usuarioLogin.getUsuIntCod());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			context.put("" + usuarioLogin.getUsuIntCod(), JSFHelper.getSession());
		}
	}

	public String actionLogin() {
		try {
			Usuario usuarioLogando = ((UsuarioBO) getBo()).login(getEntity().getUsuStrLogin(), getEntity().getUsuStrSenha());
			if (usuarioLogando != null) {
				setUsuarioLogado(usuarioLogando);
				validaUsuarioLogadoDuplicado(usuarioLogando);
				setEmpresaLogada(usuarioLogando.getEmpresa());
				setEmpresaSelecionada(usuarioLogando.getEmpresa());
				log.info("Usuário logando: " + usuarioLogando.getUsuStrNome());
				getSessionManaged().carregarMenu();
				JSFHelper.redirect("home.jsf");
			} else {
				addError(Mensagens.getMensagem(Mensagens.LOGIN_ERRO_GENERICO), "");
			}
		} catch (UsuarioNaoEncontradoException e) {
			addError(Mensagens.getMensagem("exception.usuario.nao.encontrado"), "");
			e.printStackTrace();
		} catch (UsuarioBloqueadoException e) {
			addError(Mensagens.getMensagem("exception.usuario.bloqueado"), "");
			e.printStackTrace();
		} catch (SenhaInvalidaException e) {
			addError(Mensagens.getMensagem("exception.senha.invalida"), "");
			e.printStackTrace();
		} catch (TechnicalException e) {
			addError(Mensagens.getMensagem(Mensagens.LOGIN_ERRO_GENERICO), "");
			e.printStackTrace();
		}
		return "";
	}

	public void redirectToRecuperar() {
		JSFHelper.redirect("esqueceu.jsf");
	}

	public void actionRecuperar() {
		try {
			if (getEntity().getUsuStrEmail() != null && !getEntity().getUsuStrEmail().equals("")) {
				String senha = ((UsuarioBO) getBo()).resetSenha(getEntity().getUsuStrEmail());
				if (!senha.equals("")) {
					addInfo(Mensagens.getMensagem("login.reset.sucesso"), "");
				} else {
					addError(Mensagens.getMensagem("login.reset.nao.encontrado"), "");
				}
			} else {
				addError(Mensagens.getMensagem("login.reset.login.obrigatorio"), "");
			}
		} catch (Exception e) {
			addError(Mensagens.getMensagem("login.reset.erro"), "");
			log.error("Erro ao efetuar login.", e);
		}
	}
}
