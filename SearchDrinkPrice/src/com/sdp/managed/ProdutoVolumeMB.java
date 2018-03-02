package com.sdp.managed;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;

import com.sdp.bo.ProdutoVolumeBO;
import com.sdp.entity.ProdutoVolume;
import com.sdp.util.Mensagens;

@ManagedBean
@ViewScoped
public class ProdutoVolumeMB extends GenericManagedBean<ProdutoVolume> {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(ProdutoVolumeMB.class);

	public ProdutoVolumeMB() {
		setClazz(ProdutoVolume.class);
		setBo(new ProdutoVolumeBO());
		carregarEntityList();
	}

	public void carregarEntityList() {
		try {
			setEntityList(((ProdutoVolumeBO) getBo()).listAll());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), e);
			addError(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), "");
		}
	}

	@Override
	public void actionPersist(ActionEvent event) {
		super.actionPersist(event);
		carregarEntityList();
	}

	public void actionRemove() {
		super.actionRemove(null);
		carregarEntityList();
	}

	@Override
	public void actionNew(ActionEvent event) {
		setEntity(new ProdutoVolume());
	}
}