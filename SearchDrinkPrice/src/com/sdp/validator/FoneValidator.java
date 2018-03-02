package com.sdp.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.sdp.util.Mensagens;

@FacesValidator(value = "foneValidator")
public class FoneValidator implements Validator {
    @Override
    public void validate(FacesContext arg0, UIComponent arg1, Object valorTela) throws ValidatorException {
        String valorTelaStr = replaceCaracteres(String.valueOf(valorTela));
        int totalDigitos = calculaTotalDigitos(valorTelaStr);
        if (!validaFone(valorTelaStr, totalDigitos)) {
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setSummary(Mensagens.getMensagem("erro.validacao.numero.telefone.fax"));
            throw new ValidatorException(message);
            // //System.out.println("ERRO > " + valorTela);
        }
        // else{
        // //System.out.println("OK > " + valorTela);
        // }
    }

    private int calculaTotalDigitos(String tel) {
        if (tel.startsWith("11")) {
            // var new_sp_phone = phone.match(/^(\(11\) 9(5[0-9]|6[0-9]|7[01234569]|8[0-9]|9[0-9])[0-9]{1})/g);
            if (tel.length() == 11) {
                int primeiroDigito = Integer.parseInt(tel.substring(2, 3));
                int segundoDigito = Integer.parseInt(tel.substring(3, 4));
                int terceiroDigito = Integer.parseInt(tel.substring(4, 5));
                if (primeiroDigito == 9) {
                    if (segundoDigito == 9 || segundoDigito == 8 || segundoDigito == 6 || segundoDigito == 5) {
                        return 11;
                    } else if (segundoDigito == 7) {
                        if ((terceiroDigito >= 0 && terceiroDigito <= 6) || terceiroDigito == 9) {
                            return 11;
                        }
                    }
                } else {
                    return 10;
                }
            }
        }
        return 10;
    }

    private static boolean validaFone(String fone, int totalDigitos) {
        if (fone == null || fone.length() != totalDigitos || GenericValidator.validarSequenciaIgual(fone, totalDigitos))
            return false;
        try {
            Long.parseLong(fone);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private static String replaceCaracteres(String value) {
        return value.replaceAll("\\(", "").replaceAll("-", "").replaceAll("\\)", "").replaceAll(" ", "");
    }

    public static void main(String[] args) {
        // var new_sp_phone = phone.match(/^(\(11\) 9(5[0-9]|6[0-9]|7[01234569]|8[0-9]|9[0-9])[0-9]{1})/g);
        new FoneValidator().validate(null, null, "(11) 95892-2939"); // ok
        new FoneValidator().validate(null, null, "(11) 96892-2939"); // ok
        new FoneValidator().validate(null, null, "(11) 97092-2939"); // ok
        new FoneValidator().validate(null, null, "(11) 99892-2939"); // ok
        new FoneValidator().validate(null, null, "(11) 99892-2939"); // ok
        new FoneValidator().validate(null, null, "(11) 97792-2939"); // erro
        new FoneValidator().validate(null, null, "(11) 37792-2939"); // erro
        new FoneValidator().validate(null, null, "(41) 95892-2939"); // erro
        new FoneValidator().validate(null, null, "(41) 9892-2939"); // ok
        new FoneValidator().validate(null, null, "(41) 3892-2939"); // ok
    }
}
