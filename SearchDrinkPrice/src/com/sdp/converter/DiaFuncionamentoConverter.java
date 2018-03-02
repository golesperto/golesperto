package com.sdp.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.log4j.Logger;

import com.sdp.bo.DiaFuncionamentoBO;
import com.sdp.entity.DiaFuncionamento;

@FacesConverter(forClass = DiaFuncionamento.class, value = "diaFuncionamento")
public class DiaFuncionamentoConverter implements Converter, Serializable {

    private static final long serialVersionUID = 1L;

    private Logger log = Logger.getLogger(DiaFuncionamento.class);

    DiaFuncionamentoBO bo = new DiaFuncionamentoBO();

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        try {
            if (value != null && !value.trim().isEmpty() && value.matches(".*\\d.*")) {
                final Integer cod = Integer.parseInt(value);
                return this.bo.find(cod);
            }
        } catch (Exception e) {
            this.log.error("Erro no getAsObject", e);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        try {
            if (value == null) {
                return null;
            }
            DiaFuncionamento df = (DiaFuncionamento) value;
            return String.valueOf(df.getDfuIntCod());
        } catch (Exception e) {
            this.log.error("Erro no getAsString", e);
            return null;
        }
    }
}
