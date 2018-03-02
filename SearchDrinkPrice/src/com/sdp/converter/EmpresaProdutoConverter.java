package com.sdp.converter;

import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.apache.log4j.Logger;
import com.sdp.bo.EmpresaProdutoBO;
import com.sdp.entity.EmpresaProduto;

@FacesConverter(forClass = EmpresaProduto.class, value = "empresaproduto")
public class EmpresaProdutoConverter implements Converter, Serializable {
	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(EmpresaProdutoConverter.class);
	EmpresaProdutoBO empresaprodutoBO = new EmpresaProdutoBO();

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		try {
			if (value != null && !value.trim().isEmpty() && value.matches(".*\\d.*")) {
				final Integer cod = Integer.parseInt(value);
				return empresaprodutoBO.find(cod);
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
			EmpresaProduto empresaproduto = (EmpresaProduto) value;
			return String.valueOf(empresaproduto.getEprIntCod());
		} catch (Exception e) {
			log.error("Erro no getAsString", e);
			return null;
		}
	}
}