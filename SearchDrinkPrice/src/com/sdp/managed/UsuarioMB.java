package com.sdp.managed;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import com.sdp.bo.EmpresaBO;
import com.sdp.bo.PerfilBO;
import com.sdp.bo.UsuarioBO;
import com.sdp.entity.Perfil;
import com.sdp.entity.Usuario;
import com.sdp.util.CryptMD5;
import com.sdp.util.GeradorSenha;
import com.sdp.util.Gmail;
import com.sdp.util.Mensagens;
import com.sdp.util.Status;

@ManagedBean
@SessionScoped
public class UsuarioMB extends GenericManagedBean<Usuario> {
	private static final long serialVersionUID = 9116181950356658019L;

	private static Logger log = Logger.getLogger(MenuMB.class);

	private List<SelectItem> status;

	private List<Perfil> perfis;

	private PerfilBO perfilBO;

	private boolean editar;

	private EmpresaBO empresaBO;

	public UsuarioMB() {
		setBo(new UsuarioBO());
		setClazz(Usuario.class);
		perfilBO = new PerfilBO();
		empresaBO = new EmpresaBO();
		carregarStatus();
		carregarPerfis();
		carregarEntityList();
	}

	public void carregarStatus() {
		status = new ArrayList<SelectItem>();
		Status.getList().forEach(s -> status.add(new SelectItem(s.getChave(), s.getValor())));
	}

	public void clickButtonEditar() {
		editar = true;
	}

	public void carregarPerfis() {
		try {
			perfis = perfilBO.listByStatus(Status.STATUS_NORMAL);
		} catch (Exception e) {
			log.error(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), e);
			addError(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), "");
		}
	}

	public void carregarEntityList() {
		try {
			setEntityList(((UsuarioBO) getBo()).listByIdEmpresaAndStatus(getEmpresaSelecionada().getEmpIntCod(),
					Status.STATUS_NORMAL));
			carregarPerfis();
		} catch (Exception e) {
			log.error(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), e);
			addError(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), "");
		}
	}

	@Override
	public void actionPersist(ActionEvent event) {
		try {
			if (((UsuarioBO) getBo()).findUsuarioByLoginOrEmail(getEntity().getUsuStrLogin(),
					getEntity().getUsuStrEmail()) != null) {
				((UsuarioBO) getBo()).merge(getEntity());
				carregarEntityList();
				actionNew(event);
				addInfo(Mensagens.getMensagem(Mensagens.REGISTRO_SALVO_COM_SUCESSO), "");
				return;
			}
			String senha = "SENHA01";
			getEntity()
					.setUsuStrSenha(CryptMD5.encrypt(getEntity().getUsuStrLogin().toUpperCase(), senha.toUpperCase()));
			getEntity().setUsuStrLogin(getEntity().getUsuStrLogin().toUpperCase());
			getEntity().setEmpresa(getEmpresaSelecionada());
			((UsuarioBO) getBo()).persist(getEntity());
			Gmail.enviarEmailNovoUsuario(getEntity().getUsuStrLogin(), senha, getEntity().getUsuStrEmail());
			if (getUsuarioSelecionado().getUsuIntCod() == getEntity().getUsuIntCod()) {
				setUsuarioSelecionado(getEntity());
			}
			if (getUsuarioLogado().getUsuIntCod() == getEntity().getUsuIntCod()) {
				setUsuarioLogado(getEntity());
			}
			carregarEntityList();
			actionNew(event);
			addInfo(Mensagens.getMensagem(Mensagens.REGISTRO_SALVO_COM_SUCESSO), "");
		} catch (Exception e) {
			addError(Mensagens.getMensagem(Mensagens.ERRO_AO_SALVAR_REGISTRO), "");
			log.error(Mensagens.getMensagem(Mensagens.ERRO_AO_SALVAR_REGISTRO), e);
		}
	}

	public void actionRemove() {
		try {
			((UsuarioBO) getBo()).remove(getEntity());
			carregarEntityList();
			actionNew(null);
			addInfo(Mensagens.getMensagem(Mensagens.REGISTRO_REMOVIDO_COM_SUCESSO), "");
		} catch (Exception e) {
			log.error(Mensagens.getMensagem(Mensagens.ERRO_AO_REMOVER_REGISTRO), e);
			addError(Mensagens.getMensagem(Mensagens.ERRO_AO_REMOVER_REGISTRO), "");
		}
	}

	@Override
	public void actionNew(ActionEvent event) {
		setEntity(new Usuario());
	}

	public List<SelectItem> getStatus() {
		return status;
	}

	public void setStatus(List<SelectItem> status) {
		this.status = status;
	}

	public boolean isEditar() {
		return editar;
	}

	public void setEditar(boolean editar) {
		this.editar = editar;
	}

	public List<Perfil> getPerfis() {
		return perfis;
	}

	public void setPerfis(List<Perfil> perfis) {
		this.perfis = perfis;
	}

}
