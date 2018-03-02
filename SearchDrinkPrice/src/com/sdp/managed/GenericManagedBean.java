package com.sdp.managed;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.sdp.bo.BO;
import com.sdp.entity.Empresa;
import com.sdp.entity.Usuario;
import com.sdp.util.JSFHelper;
import com.sdp.util.Mensagens;

public abstract class GenericManagedBean<E extends Serializable> implements Serializable {
	private static final long serialVersionUID = 1L;

	private BO<E> bo;

	private Class<E> clazz;

	private E entity;

	private List<E> entityList;

	private static Logger log = Logger.getLogger(GenericManagedBean.class);

	private TimeZone timeZone = TimeZone.getDefault();

	private Locale locale = new Locale("pt", "BR");

	private SessionManaged sessionManaged;

	@PostConstruct
	public void init() {
		getEntity();
	}

	public GenericManagedBean() {
		this.bo = new BO<E>();
	}

	public Class<E> getClazz() {
		return clazz;
	}

	public void setClazz(Class<E> clazz) {
		this.clazz = clazz;
		this.bo.setClazz(clazz);
	}

	@SuppressWarnings("unchecked")
	public E getEntity() {
		if (entity == null) {
			try {
				entity = (E) Class.forName(getClazz().getName()).newInstance();
				// entity = (E) clazz.newInstance();
			} catch (Exception e) {
				log.error("Erro no getEntity", e);
			}
		}
		return entity;
	}

	public void setEntity(E entity) {
		this.entity = entity;
	}

	public void actionNew(ActionEvent event) {
		entity = null;
		// getLumeSecurity().clearLumeSecurity();
		JSFHelper.initUIViewRoot();
	}

	public void initUIViewRoot() {
		JSFHelper.initUIViewRoot();
	}

	public void actionPersist(ActionEvent event) {
		try {
			if (bo.persist(getEntity())) {
				actionNew(event);
				addInfo(Mensagens.getMensagem(Mensagens.REGISTRO_SALVO_COM_SUCESSO), "");
			} else
				addError(Mensagens.getMensagem(Mensagens.ERRO_AO_SALVAR_REGISTRO), "");
		} catch (Exception e) {
			e.printStackTrace();
			addError(Mensagens.getMensagem(Mensagens.ERRO_AO_SALVAR_REGISTRO), "");
			log.error("Erro no actionPersist", e);
		}
	}

	public void actionRemove(ActionEvent event) {
		try {
			if (bo.remove(getEntity())) {
				actionNew(event);
				addInfo(Mensagens.getMensagem(Mensagens.REGISTRO_REMOVIDO_COM_SUCESSO), "");
			} else
				addError(Mensagens.getMensagem(Mensagens.ERRO_AO_REMOVER_REGISTRO), "");
		} catch (Exception e) {
			log.error("Erro no actionRemove", e);
			addError(Mensagens.getMensagem(Mensagens.ERRO_AO_REMOVER_REGISTRO), "");
		}
	}

	public List<E> getEntityList() {
		return entityList;
	}

	public void validationFailed(FacesContext context, UIComponent validate) {
		((UIInput) validate).setValid(false);
		context.validationFailed();
		context.addMessage(validate.getClientId(context), new FacesMessage(((UIInput) validate).getValidatorMessage()));
	}

	public void addInfo(String summary, String detail) {
		addMessage(FacesMessage.SEVERITY_INFO, summary, detail);
	}

	public void addWarn(String summary, String detail) {
		addMessage(FacesMessage.SEVERITY_WARN, summary, detail);
	}

	public void addError(String summary, String detail, Exception ex) {
		log.error(summary, ex);
		addMessage(FacesMessage.SEVERITY_ERROR, summary, detail);
	}

	public void addError(String summary, String detail) {
		addError(summary, detail, null);
	}

	public void addFatal(String summary, String detail) {
		addMessage(FacesMessage.SEVERITY_FATAL, summary, detail);
	}

	private void addMessage(Severity severity, String summary, String detail) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
	}

	public boolean isUsuarioAdministrador() {
		return getSessionManaged().isUsuarioAdministrador();
	}

	public TimeZone getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(TimeZone timeZone) {
		this.timeZone = timeZone;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public BO<E> getBo() {
		return bo;
	}

	public void setBo(BO<E> bo) {
		this.bo = bo;
	}

	public void setEntityList(List<E> entityList) {
		this.entityList = entityList;
	}

	public Usuario getUsuarioLogado() {
		return getSessionManaged().getUsuarioLogado();
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.getSessionManaged().setUsuarioLogado(usuarioLogado);
	}

	public void recarregaCombos() {
		getSessionManaged().recarregaCombos();
	}

	public void carregarEmpresas() {
		getSessionManaged().carregarEmpresas();
	}

	public SessionManaged getSessionManaged() {
		HttpSession httpSession = JSFHelper.getSession();
		sessionManaged = (SessionManaged) httpSession.getAttribute("sessionManaged");
		if (sessionManaged == null) {
			sessionManaged = new SessionManaged();
			httpSession.setAttribute("sessionManaged", sessionManaged);
		}
		return sessionManaged;
	}

	public static SessionManaged createInstance() {
		HttpSession httpSession = JSFHelper.getSession();
		SessionManaged sessionManaged = (SessionManaged) httpSession.getAttribute("sessionManaged");
		if (sessionManaged == null) {
			sessionManaged = new SessionManaged();
			httpSession.setAttribute("sessionManaged", sessionManaged);
		}
		return sessionManaged;
	}

	public void setSessionManaged(SessionManaged sessao) {
		this.sessionManaged = sessao;
	}

	public void carregarMenu() {
		getSessionManaged().carregarMenu();
	}

	public Usuario getUsuarioSelecionado() {
		return getSessionManaged().getUsuarioSelecionado();
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		getSessionManaged().setUsuarioSelecionado(usuarioSelecionado);
	}

	public List<Usuario> getUsuarios() {
		return getSessionManaged().getUsuarios();
	}

	public List<Empresa> getEmpresas() {
		return getSessionManaged().getEmpresas();
	}

	public void setEmpresas(List<Empresa> empresas) {
		getSessionManaged().setEmpresas(empresas);
	}

	public Empresa getEmpresaLogada() {
		if (getEmpresaSelecionada() != null)
			return getEmpresaSelecionada();
		return getSessionManaged().getEmpresaLogada();
	}

	public void setEmpresaLogada(Empresa empresa) {
		this.getSessionManaged().setEmpresaLogada(empresa);
		setEmpresaSelecionada(empresa);
	}

	public Empresa getEmpresaSelecionada() {
		return getSessionManaged().getEmpresaSelecionada();
	}

	public void setEmpresaSelecionada(Empresa empresaSelecionada) {
		getSessionManaged().setEmpresaSelecionada(empresaSelecionada);
	}
}
