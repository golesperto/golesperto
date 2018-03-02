package com.sdp.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.log4j.Logger;

import com.sdp.bo.MenuBO;
import com.sdp.entity.Menu;

@FacesConverter(forClass = Menu.class, value = "menu")
public class MenuConverter implements Converter, Serializable {
    private static final long serialVersionUID = 1L;

    private Logger log = Logger.getLogger(MenuConverter.class);

    MenuBO menuBO = new MenuBO();

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        try {
            if (value != null && !value.trim().isEmpty() && value.matches(".*\\d.*")) {
                final Integer cod = Integer.parseInt(value);
                return menuBO.find(cod);
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
            Menu menu = (Menu) value;
            return String.valueOf(menu.getMenIntCod());
        } catch (Exception e) {
            log.error("Erro no getAsString", e);
            return null;
        }
    }
}
