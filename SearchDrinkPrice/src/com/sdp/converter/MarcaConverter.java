package com.sdp.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.log4j.Logger;

import com.sdp.bo.MarcaBO;
import com.sdp.entity.Marca;
import com.sdp.entity.Menu;

@FacesConverter(forClass = Marca.class, value = "marca")
public class MarcaConverter implements Converter, Serializable {
	private static final long serialVersionUID = 1L;

	private Logger log = Logger.getLogger(MarcaConverter.class);

	MarcaBO marcaBO = new MarcaBO();

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		try {
			if (value != null && !value.trim().isEmpty() && value.matches(".*\\d.*")) {
				final Integer cod = Integer.parseInt(value);
				return marcaBO.find(cod);
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
			Marca marca = (Marca) value;
			return String.valueOf(marca.getMarIntCod());
		} catch (Exception e) {
			log.error("Erro no getAsString", e);
			return null;
		}
	}
}
