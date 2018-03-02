package com.sdp.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("telefone")
public class TelefoneConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uIcomponent, String value) {
		if (value.trim().equals("")) {
			return null;
		} else {
			value = value.replace("(", "");
			value = value.replace(")", "");
			value = value.replace("-", "");
			return value;
		}
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uIcomponent, Object object) {
		return object.toString();
	}
}
