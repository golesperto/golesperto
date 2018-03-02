package com.sdp.managed;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import com.sdp.bo.ObjetoBO;
import com.sdp.bo.PerfilBO;
import com.sdp.bo.PerfilObjetoBO;
import com.sdp.entity.Objeto;
import com.sdp.entity.Perfil;
import com.sdp.entity.PerfilObjeto;
import com.sdp.util.Mensagens;
import com.sdp.util.Status;

@ManagedBean
@ViewScoped
public class PerfilObjetoMB extends GenericManagedBean<PerfilObjeto> {
	private static final long serialVersionUID = 9116181950356658019L;

	private static Logger log = Logger.getLogger(PerfilObjetoMB.class);

	private List<Perfil> perfis;

	private List<Objeto> objetos, objetosSelecionados;

	private Perfil perfilSelecionado;

	private String statusSelecionado;

	private PerfilBO perfilBO;

	private ObjetoBO objetoBO;

	public PerfilObjetoMB() {
		setBo(new PerfilObjetoBO());
		setClazz(PerfilObjeto.class);
		objetoBO = new ObjetoBO();
		perfilBO = new PerfilBO();
		carregarEntityList();
		carregarObjetos();
		carregarPerfis();
	}

	private void carregarEntityList() {
		try {
			setEntityList(((PerfilObjetoBO) getBo()).listPerfilObjetos());
		} catch (Exception e) {
			addError(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), "");
			log.error(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), e);
		}
	}

	public void carregarPerfis() {
		try {
			perfis = perfilBO.listByStatus(Status.STATUS_NORMAL);
		} catch (Exception e) {
			log.error(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), e);
			addError(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), "");
		}
	}

	@Override
	public void setEntity(PerfilObjeto entity) {
		objetosSelecionados = entity.getObjetos();
		perfilSelecionado = entity.getPerfil();
		super.setEntity(entity);
	}

	public void carregarObjetos() {
		try {
			objetos = objetoBO.listByStatus(Status.STATUS_NORMAL);
		} catch (Exception e) {
			log.error(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), e);
			addError(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), "");
		}
	}

	@Override
	public void actionPersist(ActionEvent event) {
		try {
			((PerfilObjetoBO) getBo()).deleteByIdPerfil(perfilSelecionado.getPerIntCod());
			List<PerfilObjeto> pos = new ArrayList<>();
			objetosSelecionados.forEach(o -> {
				pos.add(new PerfilObjeto(perfilSelecionado, o));
			});
			((PerfilObjetoBO) getBo()).persistBatch(pos);
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
			((PerfilObjetoBO) getBo()).deleteByIdPerfil(perfilSelecionado.getPerIntCod());
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
		setEntity(new PerfilObjeto());
	}

	public List<Perfil> getPerfis() {
		return perfis;
	}

	public void setPerfis(List<Perfil> perfis) {
		this.perfis = perfis;
	}

	public List<Objeto> getObjetos() {
		return objetos;
	}

	public void setObjetos(List<Objeto> objetos) {
		this.objetos = objetos;
	}

	public List<Objeto> getObjetosSelecionados() {
		return objetosSelecionados;
	}

	public void setObjetosSelecionados(List<Objeto> objetosSelecionados) {
		this.objetosSelecionados = objetosSelecionados;
	}

	public Perfil getPerfilSelecionado() {
		return perfilSelecionado;
	}

	public void setPerfilSelecionado(Perfil perfilSelecionado) {
		this.perfilSelecionado = perfilSelecionado;
	}

	public String getStatusSelecionado() {
		return statusSelecionado;
	}

	public void setStatusSelecionado(String statusSelecionado) {
		this.statusSelecionado = statusSelecionado;
	}
}
