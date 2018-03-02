package com.sdp.managed;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.sdp.bo.UsuarioBO;
import com.sdp.entity.Usuario;
import com.sdp.util.JSFHelper;

@ManagedBean
@ViewScoped
public class SobreMB extends GenericManagedBean<Usuario> {
	private static final long serialVersionUID = 9116181950356658019L;

	private static Logger log = Logger.getLogger(MenuMB.class);

	private String titulo;

	public SobreMB() {
		setBo(new UsuarioBO());
		setClazz(Usuario.class);
		if (getUsuarioLogado() == null) {
			JSFHelper.redirect("login.jsf");
		}
		titulo = "Sobre";
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}
