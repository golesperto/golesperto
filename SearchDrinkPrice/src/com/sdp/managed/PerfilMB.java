package com.sdp.managed;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import com.sdp.bo.PerfilBO;
import com.sdp.entity.Perfil;
import com.sdp.util.Mensagens;
import com.sdp.util.Status;

@ManagedBean
@ViewScoped
public class PerfilMB extends GenericManagedBean<Perfil> {
	private static final long serialVersionUID = 9116181950356658019L;

	private static Logger log = Logger.getLogger(MenuMB.class);

	private List<SelectItem> status;

	public PerfilMB() {
		setBo(new PerfilBO());
		setClazz(Perfil.class);
		carregarEntityList();
		carregarStatus();
	}

	public void carregarStatus() {
		status = new ArrayList<SelectItem>();
		Status.getList().forEach(s -> status.add(new SelectItem(s.getChave(), s.getValor())));
	}

	private void carregarEntityList() {
		try {
			setEntityList(((PerfilBO) getBo()).listAll());
		} catch (Exception e) {
			addError(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), "");
			log.error(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), e);
		}
	}

	@Override
	public void actionNew(ActionEvent event) {
		setEntity(new Perfil());
	}

	@Override
	public void actionPersist(ActionEvent event) {
		try {
			getEntity().setPerStrNome(getEntity().getPerStrNome().toUpperCase());
			getEntity().setPerChaTipo(getEntity().getPerChaTipo().toUpperCase());
			((PerfilBO) getBo()).persist(getEntity());
			carregarEntityList();
			actionNew(event);
			addInfo(Mensagens.getMensagem(Mensagens.REGISTRO_SALVO_COM_SUCESSO), "");
		} catch (Exception e) {
			log.error(Mensagens.getMensagem(Mensagens.ERRO_AO_SALVAR_REGISTRO), e);
			addError(Mensagens.getMensagem(Mensagens.ERRO_AO_SALVAR_REGISTRO), "");
		}
	}

	public void actionRemove() {
		try {
			((PerfilBO) getBo()).remove(getEntity());
			carregarEntityList();
			actionNew(null);
			addInfo(Mensagens.getMensagem(Mensagens.REGISTRO_REMOVIDO_COM_SUCESSO), "");
		} catch (Exception e) {
			log.error(Mensagens.getMensagem(Mensagens.ERRO_AO_REMOVER_REGISTRO), e);
			addError(Mensagens.getMensagem(Mensagens.ERRO_AO_REMOVER_REGISTRO), "");
		}
	}

	public List<SelectItem> getStatus() {
		return status;
	}

	public void setStatus(List<SelectItem> status) {
		this.status = status;
	}
}
