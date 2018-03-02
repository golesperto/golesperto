package com.sdp.managed;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import com.sdp.bo.MenuBO;
import com.sdp.entity.Menu;
import com.sdp.util.Mensagens;
import com.sdp.util.Status;

@ManagedBean
@ViewScoped
public class MenuMB extends GenericManagedBean<Menu> {
	private static final long serialVersionUID = 9116181950356658019L;

	private static Logger log = Logger.getLogger(MenuMB.class);

	private List<SelectItem> status;

	public MenuMB() {
		setBo(new MenuBO());
		setClazz(Menu.class);
		carregarEntityList();
		carregarStatus();
	}

	public void carregarStatus() {
		status = new ArrayList<SelectItem>();
		Status.getList().forEach(s -> status.add(new SelectItem(s.getChave(), s.getValor())));
	}

	private void carregarEntityList() {
		try {
			setEntityList(((MenuBO) getBo()).listAll());
		} catch (Exception e) {
			addError(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), "");
			log.error(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), e);
		}
	}

	@Override
	public void actionPersist(ActionEvent event) {
		try {
			((MenuBO) getBo()).persist(getEntity());
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
			((MenuBO) getBo()).remove(getEntity());
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
