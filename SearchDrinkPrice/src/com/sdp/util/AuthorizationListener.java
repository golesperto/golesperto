package com.sdp.util;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.sdp.bo.LogAcessoBO;
import com.sdp.bo.UsuarioBO;
import com.sdp.entity.LogAcesso;
import com.sdp.entity.Objeto;
import com.sdp.entity.Usuario;
import com.sdp.managed.GenericManagedBean;
import com.sdp.managed.SessionManaged;

public class AuthorizationListener implements PhaseListener {
	private static final long serialVersionUID = -8237087853801435858L;

	private static Logger log = Logger.getLogger(AuthorizationListener.class);

	public void beforePhase(PhaseEvent event) {
		HttpSession httpSession = JSFHelper.getSession();
		SessionManaged sessionManaged = (SessionManaged) httpSession.getAttribute("sessionManaged");
		HttpServletResponse response = JSFHelper.getResponse();
		String currentPage = FacesContext.getCurrentInstance().getViewRoot().getViewId();
		if (currentPage.contains("/")) {
			String[] diretorios = currentPage.split("/");
			currentPage = diretorios[diretorios.length - 1];
		}
		if (currentPage.contains("xhtml")) {
			currentPage = currentPage.replaceAll("xhtml", "jsf");
		}
		Usuario usuarioLogado = sessionManaged != null ? sessionManaged.getUsuarioLogado() : null;
		if (usuarioLogado == null) {
			String strUsu = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("usu");
			String pswUsu = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pwd");
			if (strUsu != null && pswUsu != null) {
				try {
					usuarioLogado = new UsuarioBO().loginCrypt(strUsu, pswUsu);
					if (usuarioLogado != null) {
						sessionManaged = GenericManagedBean.createInstance();
						sessionManaged.setUsuarioLogado(usuarioLogado);
						sessionManaged.setEmpresaLogada(usuarioLogado.getEmpresa());
						sessionManaged.setEmpresaSelecionada(usuarioLogado.getEmpresa());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		Objeto objetoAtual = isPageDenied(currentPage, usuarioLogado, sessionManaged);
		/*if ((!currentPage.contains("login") && !currentPage.contains("dashboard") && !currentPage.contains("esqueceu")
				&& !currentPage.contains("sobre") && !currentPage.contains("troca") && usuarioLogado == null)) {
			if (!response.isCommitted()) {
				log.debug("Redirecionou da pagina : " + currentPage + " UserLogado : " + usuarioLogado.getUsuStrNome());
				JSFHelper.redirect("login.jsf");
			}
		}*/
		if (usuarioLogado != null)
			try {
				String paginaAnterior = (String) JSFHelper.getSession().getAttribute("PAGINA_ANTERIOR");
				if (!currentPage.equals(paginaAnterior)) {
					new LogAcessoBO()
							.persist(new LogAcesso(usuarioLogado, currentPage, JSFHelper.getRequest().getRemoteAddr()));
					JSFHelper.getSession().setAttribute("PAGINA_ANTERIOR", currentPage);
				}
			} catch (Exception e) {
				log.error(Mensagens.getMensagem(Mensagens.ERRO_AO_SALVAR_REGISTRO), e);
			}
		response.setHeader("Expires", "-1");
		response.setHeader("Cache-Control",
				"no-store, no-cache, must-revalidade, proxy-revalidade, private, post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");
	}

	private Objeto isPageDenied(String currentPage, Usuario usuarioLogado, SessionManaged managed) {
		if (managed != null) {
			List<Objeto> objetos = managed.getObjetos();
			if (objetos != null) {
				for (Objeto objeto : objetos) {
					if (currentPage != null && currentPage.contains(objeto.getObjStrObjeto())) {
						return objeto;
					}
				}
			}
		}
		return null;
	}

	@Override
	public void afterPhase(PhaseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}
}
