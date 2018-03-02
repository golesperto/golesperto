package com.sdp.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.log4j.Logger;

import com.sdp.bo.EmpresaBO;
import com.sdp.bo.UsuarioBO;
import com.sdp.entity.Empresa;
import com.sdp.entity.Usuario;

@FacesConverter(forClass = Usuario.class, value = "usuario")
public class UsuarioConverter implements Converter, Serializable {
	private static final long serialVersionUID = 1L;

	private Logger log = Logger.getLogger(UsuarioConverter.class);

	UsuarioBO usuarioBO = new UsuarioBO();

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		try {
			if (value != null && !value.trim().isEmpty() && value.matches(".*\\d.*")) {
				final Long cod = Long.parseLong(value);
				return usuarioBO.find(cod);
			}
		} catch (Exception e) {
			log.error("Erro no getAsObject", e);
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		try {
			if (value == null)
				return null;
			Usuario usuario = (Usuario) value;
			return String.valueOf(usuario.getUsuIntCod());
		} catch (Exception e) {
			log.error("Erro no getAsString", e);
			return null;
		}
	}
}
