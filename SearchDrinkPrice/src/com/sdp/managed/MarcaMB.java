package com.sdp.managed;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;

import com.sdp.bo.MarcaBO;
import com.sdp.entity.Marca;
import com.sdp.util.Mensagens;

@ManagedBean
@ViewScoped
public class MarcaMB extends GenericManagedBean<Marca> {
	private static final long serialVersionUID = 9116181950356658019L;

	private static Logger log = Logger.getLogger(MarcaMB.class);

	public MarcaMB() {
		setBo(new MarcaBO());
		setClazz(Marca.class);
		carregarEntityList();
	}
	
	public void carregarEntityList(){
		try {
			setEntityList(((MarcaBO) getBo()).listAll());
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
		setEntity(new Marca());
	}
}
