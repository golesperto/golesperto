package com.sdp.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sdp.util.Status;

@SuppressWarnings("serial")
@Entity
@Table(name = "sdp_USUARIO")
public class Usuario implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USU_INT_COD")
	private Long usuIntCod;

	@ManyToOne
	@JoinColumn(name = "EMP_INT_COD", nullable = false)
	private Empresa empresa;

	@ManyToOne
	@JoinColumn(name = "PER_INT_COD", nullable = false)
	private Perfil perfil;

	@Column(name = "USU_STR_EMAIL", nullable = false, unique = true, length = 50)
	private String usuStrEmail;

	@Column(name = "USU_STR_LOGIN", nullable = false, unique = true, length = 50)
	private String usuStrLogin;

	@Column(name = "USU_STR_NOME", nullable = false, length = 100)
	private String usuStrNome;

	@Column(name = "USU_STR_SENHA", nullable = false, length = 50)
	private String usuStrSenha;

	@Column(name = "USU_CHA_STATUS", nullable = false, length = 1)
	private Character usuChaStatus;

	public Usuario() {
	}
	
	public Usuario(Empresa empresa, String usuStrNome, String usuStrEmail, Perfil perfil, Character usuChaStatus) {
		this.empresa = empresa;
		this.usuStrNome = usuStrNome;
		this.usuStrEmail = usuStrEmail;
		this.perfil = perfil;
		this.usuChaStatus = usuChaStatus;
	}

	public Long getUsuIntCod() {
		return usuIntCod;
	}

	public void setUsuIntCod(Long usuIntCod) {
		this.usuIntCod = usuIntCod;
	}

	public String getUsuStrEmail() {
		return usuStrEmail;
	}

	public void setUsuStrEmail(String usuStrEmail) {
		this.usuStrEmail = usuStrEmail;
	}

	public String getUsuStrLogin() {
		return usuStrLogin;
	}

	public void setUsuStrLogin(String usuStrLogin) {
		this.usuStrLogin = usuStrLogin;
	}

	public String getUsuStrNome() {
		return usuStrNome;
	}

	public void setUsuStrNome(String usuStrNome) {
		this.usuStrNome = usuStrNome;
	}

	public String getUsuStrSenha() {
		return usuStrSenha;
	}

	public void setUsuStrSenha(String usuStrSenha) {
		this.usuStrSenha = usuStrSenha;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((usuIntCod == null) ? 0 : usuIntCod.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (usuIntCod == null) {
			if (other.usuIntCod != null)
				return false;
		} else if (!usuIntCod.equals(other.usuIntCod))
			return false;
		return true;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public Character getUsuChaStatus() {
		return usuChaStatus;
	}

	public void setUsuChaStatus(Character usuChaStatus) {
		this.usuChaStatus = usuChaStatus;
	}
	
	public String getStatusStr() {
		return usuChaStatus != null && usuChaStatus.equals(Status.STATUS_NORMAL) ? "Normal" : "Bloqueado";
	}

}