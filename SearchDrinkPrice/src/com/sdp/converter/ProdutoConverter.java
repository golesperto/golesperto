package com.sdp.converter;

import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.apache.log4j.Logger;

import com.sdp.bo.ProdutoBO;
import com.sdp.bo.ProdutoTipoBO;
import com.sdp.entity.Produto;
import com.sdp.entity.ProdutoTipo;

@FacesConverter(forClass = Produto.class, value = "produto")
public class ProdutoConverter implements Converter, Serializable {
	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(ProdutoConverter.class);
	ProdutoBO produtoBO = new ProdutoBO();

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		try {
			if (value != null && !value.trim().isEmpty() && value.matches(".*\\d.*")) {
				final Integer cod = Integer.parseInt(value);
				return produtoBO.find(cod);
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
			Produto produto = (Produto) value;
			return String.valueOf(produto.getProIntCod());
		} catch (Exception e) {
			log.error("Erro no getAsString", e);
			return null;
		}
	}
}