package com.sdp.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.sdp.util.Mensagens;

@FacesValidator(value="cpfValidator")
public class CpfValidator implements Validator {
    
    @Override
    public void validate(FacesContext arg0, UIComponent arg1, Object valorTela) throws ValidatorException {
        String valorTelaStr = replaceCaracteres(String.valueOf(valorTela));
        if (!validaCPF(valorTelaStr)) {
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setSummary(Mensagens.getMensagem("erro.validacao.cpf"));
            throw new ValidatorException(message);
        }
    }

    public static boolean validaCPF(String cpf) {
        if (cpf == null || cpf.length() != 11 || GenericValidator.validarSequenciaIgual(cpf,11))
            return false;
        try {
            Long.parseLong(cpf);
        } catch (NumberFormatException e) {
            return false;
        }
        if (!calcDigVerif(cpf.substring(0, 9)).equals(cpf.substring(9, 11)))
            return false;
        return true;
    }

    private static String calcDigVerif(String num) {
        Integer primDig, segDig;
        int soma = 0, peso = 10;
        for (int i = 0; i < num.length(); i++)
            soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;
        if (soma % 11 == 0 | soma % 11 == 1)
            primDig = new Integer(0);
        else
            primDig = new Integer(11 - (soma % 11));
        soma = 0;
        peso = 11;
        for (int i = 0; i < num.length(); i++)
            soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;
        soma += primDig.intValue() * 2;
        if (soma % 11 == 0 | soma % 11 == 1)
            segDig = new Integer(0);
        else
            segDig = new Integer(11 - (soma % 11));
        return primDig.toString() + segDig.toString();
    }
    
    private static String replaceCaracteres(String value){
        return value.replaceAll("\\.", "").replaceAll("-", "");
    }
}
