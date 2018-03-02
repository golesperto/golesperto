package com.sdp.converter;

import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.apache.log4j.Logger;
import com.sdp.bo.ProdutoVolumeBO;
import com.sdp.entity.ProdutoVolume;

@FacesConverter(forClass = ProdutoVolume.class, value = "produtovolume")
public class ProdutoVolumeConverter implements Converter, Serializable {
	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(ProdutoVolumeConverter.class);
	ProdutoVolumeBO produtovolumeBO = new ProdutoVolumeBO();

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		try {
			if (value != null && !value.trim().isEmpty() && value.matches(".*\\d.*")) {
				final Integer cod = Integer.parseInt(value);
				return produtovolumeBO.find(cod);
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
			ProdutoVolume produtovolume = (ProdutoVolume) value;
			return String.valueOf(produtovolume.getPrvIntCod());
		} catch (Exception e) {
			log.error("Erro no getAsString", e);
			return null;
		}
	}
}