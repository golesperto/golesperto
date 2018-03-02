package com.sdp.util;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Gmail implements Runnable {
	private String para, titulo, conteudo;

	private static Gmail instance;

	private Gmail(String para, String titulo, String conteudo) {
		super();
		this.para = para;
		this.titulo = titulo;
		this.conteudo = conteudo;
	}

	public static void main(String[] args) {
		Gmail.getInstance("matheus_felipe25@live.com", "Teste", "Teste").enviarEmail();
	}

	public static synchronized Gmail getInstance(String para, String titulo, String conteudo) {
		instance = new Gmail(para, titulo, conteudo);
		return instance;
	}

	public static void enviarEmailNovoUsuario(String login, String senha, String email) {
		String conteudo = Mensagens.getMensagem("novo.usuario.senha.conteudo");
		conteudo = conteudo.replace("[login]", login);
		conteudo = conteudo.replace("[senha]", senha);
		Gmail.getInstance(email, Mensagens.getMensagem("novo.usuario.senha.titulo"), conteudo).enviarEmail();
	}

	public static boolean enviarEmail() {
		if (instance != null && instance.getPara() != null && !instance.getPara().isEmpty()
				&& instance.getConteudo() != null && !instance.getConteudo().isEmpty() && instance.getTitulo() != null
				&& !instance.getTitulo().isEmpty()) {
			new Thread(instance).start();
			return true;
		} else {
			return false;
		}
	}

	public void run() {
		System.out.println("Enviando email para: " + para);
		Properties props = new Properties();
		props.put("mail.smtp.host", Mensagens.getMensagem("mail.smtp.host"));
		props.put("mail.smtp.socketFactory.port", Mensagens.getMensagem("mail.smtp.socketFactory.port"));
		props.put("mail.smtp.socketFactory.class", Mensagens.getMensagem("mail.smtp.socketFactory.class"));
		props.put("mail.smtp.auth", Mensagens.getMensagem("mail.smtp.auth"));
		props.put("mail.smtp.port", Mensagens.getMensagem("mail.smtp.port"));
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(Mensagens.getMensagem("mail.username"),
						Mensagens.getMensagem("mail.password"));
			}
		});
		try {
			Message message = new MimeMessage(session);
			try {
				message.setFrom(new InternetAddress(Mensagens.getMensagem("mail.username"), "sdp"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(para));
			message.setSubject(titulo);
			message.setText(conteudo);
			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	public String getPara() {
		return para;
	}

	public void setPara(String para) {
		this.para = para;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
}
