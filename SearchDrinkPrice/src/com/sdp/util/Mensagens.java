package com.sdp.util;

import java.util.ResourceBundle;

public class Mensagens {
	public static final String REGISTRO_SALVO_COM_SUCESSO = "salvar.sucesso";
	public static final String REGISTRO_REMOVIDO_COM_SUCESSO = "remover.sucesso";
	public static final String ERRO_AO_SALVAR_REGISTRO = "salvar.erro";
	public static final String ERRO_AO_REMOVER_REGISTRO = "remover.erro";
	public static final String ERRO_AO_BUSCAR_REGISTROS = "buscar.erro";
	public static final String USUARIO_SENHA_INVALIDO = "login.user.senha.invalido";
	public static final String USUARIO_SEM_PERFIL = "login.user.sem.perfil";
	public static final String SENHA_EXPIRADA = "login.senha.expirada";
	public static final String LOGIN_ERRO_GENERICO = "login.erro.generico";
	public static final String SENHA_ATUAL_INVALIDA = "trocar.senha.senha.atual.invalida";
	public static final String SENHA_CONFIRMACAO_INVALIDA = "trocar.senha.senha.confirmacao.invalida";
	public static final String USUARIO_NAOENCONTRADO = "login.user.naoencontrado";
	public static final String USUARIO_BLOQUEADO = "login.user.bloqueado";
	public static final String USUARIO_DUPLICADO = "usuario.user.duplicado";
	public static final String EMAIL_INVALIDO = "usuario.email.invalido";
	public static final String SENHA_ALTERADA_COM_SUCESSO = "senha.alterada.com.sucesso";
	public static final String RANGE_DATA_INVALIDO = "range.data.invalido";
	public static final String API_LANGUAGE_TRANSLATION_USERNAME = "api.language.translation.username";
	public static final String API_LANGUAGE_TRANSLATION_PASSWORD = "api.language.translation.password";
	public static final String API_TONE_ANALYZER_USERNAME = "api.tone.analyzer.username";
	public static final String API_TONE_ANALYZER_PASSWORD = "api.tone.analyzer.password";
	public static final String API_CONVERSATION_USERNAME = "api.conversation.username";
	public static final String API_CONVERSATION_PASSWORD = "api.conversation.password";
	public static final String API_CONVERSATION_WORKSPACE_ID = "api.conversation.workspace.id";
	public static final String API_PERSONALITY_INSIGHTS_USERNAME = "api.personality.insights.username";
	public static final String API_PERSONALITY_INSIGHTS_PASSWORD = "api.personality.insights.password";
	public static final String CNPJ_INVALIDO = "cnpj.invalido";
	public static final String SENHA_PADRAO = "senha.padrao";

	public static String getMensagem(String key) {
		ResourceBundle rb = ResourceBundle.getBundle("com.sdp.resource.sdp");
		return rb.getString(key);
	}
}
