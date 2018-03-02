package com.sdp.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.sdp.util.Mensagens;

@FacesValidator(value = "cepValidator")
public class CepValidator implements Validator {

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object valorTela)
			throws ValidatorException {
		String valorTelaStr = replaceCaracteres(String.valueOf(valorTela));
		if (!validaCep(valorTelaStr)) {
			FacesMessage message = new FacesMessage();
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			message.setSummary(Mensagens.getMensagem("erro.validacao.cep"));
			throw new ValidatorException(message);
		}
	}

	private static boolean validaCep(String cep) {
		if (cep == null || cep.length() != 8
				|| GenericValidator.validarSequenciaIgual(cep, 8))
			return false;
		try {
			Long.parseLong(cep);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	private static String replaceCaracteres(String value) {
		return value.replaceAll("-", "");
	}
}
