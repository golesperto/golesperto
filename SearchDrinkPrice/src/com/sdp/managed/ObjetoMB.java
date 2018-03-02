package com.sdp.managed;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import com.sdp.bo.MenuBO;
import com.sdp.bo.ObjetoBO;
import com.sdp.entity.Menu;
import com.sdp.entity.Objeto;
import com.sdp.util.Mensagens;
import com.sdp.util.Status;

@ManagedBean
@ViewScoped
public class ObjetoMB extends GenericManagedBean<Objeto> {
	private static final long serialVersionUID = 9116181950356658019L;

	private static Logger log = Logger.getLogger(MenuMB.class);

	private List<SelectItem> status;

	private List<Menu> menus;

	private MenuBO menuBO;

	public ObjetoMB() {
		setBo(new ObjetoBO());
		setClazz(Objeto.class);
		menuBO = new MenuBO();
		carregarEntityList();
		carregarStatus();
		carregarMenus();
	}

	public void carregarStatus() {
		status = new ArrayList<SelectItem>();
		Status.getList().forEach(s -> status.add(new SelectItem(s.getChave(), s.getValor())));
	}

	public void carregarMenus() {
		try {
			menus = menuBO.listByStatus(Status.STATUS_NORMAL);
		} catch (Exception e) {
			addError(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), "");
			log.error(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), e);
		}
	}

	private void carregarEntityList() {
		try {
			setEntityList(((ObjetoBO) getBo()).listAll());
		} catch (Exception e) {
			addError(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), "");
			log.error(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), e);
		}
	}

	@Override
	public void actionNew(ActionEvent event) {
		setEntity(new Objeto());
	}

	@Override
	public void actionPersist(ActionEvent event) {
		try {
			((ObjetoBO) getBo()).persist(getEntity());
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
			((ObjetoBO) getBo()).remove(getEntity());
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

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}
}
