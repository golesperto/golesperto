package com.sdp.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "sdp_LOG_ACESSO")
public class LogAcesso implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LOG_INT_COD")
	private Long logIntCod;

	@ManyToOne
	@JoinColumn(name = "USU_INT_COD")
	private Usuario usuario;

	@Column(name = "LOG_DTM_ACESSO")
	private Date logDtmAcesso = Calendar.getInstance().getTime();

	@Column(name = "LOG_STR_DESCRICAO", length = 100)
	private String logStrDescricao;

	@Column(name = "LOG_STR_OBJETO", length = 30)
	private String logStrObjeto;

	@Column(name = "LOG_STR_IP", length = 30)
	private String logStrIp;

	public LogAcesso() {
	}

	public LogAcesso(Usuario usuario, Date logDtmAcesso, String logStrDescricao) {
		this.usuario = usuario;
		this.logDtmAcesso = logDtmAcesso;
		this.logStrDescricao = logStrDescricao;
	}

	public LogAcesso(Usuario usuario, String logStrObjeto, String logStrIp, String logStrDescricao) {
		this.usuario = usuario;
		this.logStrObjeto = logStrObjeto;
		this.logStrIp = logStrIp;
		this.logStrDescricao = logStrDescricao;
	}

	public LogAcesso(Usuario usuario, String logStrObjeto, String logStrIp) {
		this.usuario = usuario;
		this.logStrObjeto = logStrObjeto;
		this.logStrIp = logStrIp;
	}

	public Long getLogIntCod() {
		return logIntCod;
	}

	public void setLogIntCod(Long logIntCod) {
		this.logIntCod = logIntCod;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getLogDtmAcesso() {
		return logDtmAcesso;
	}

	public void setLogDtmAcesso(Date logDtmAcesso) {
		this.logDtmAcesso = logDtmAcesso;
	}

	public String getLogStrDescricao() {
		return logStrDescricao;
	}

	public void setLogStrDescricao(String logStrDescricao) {
		this.logStrDescricao = logStrDescricao;
	}

	public String getLogStrObjeto() {
		return logStrObjeto;
	}

	public void setLogStrObjeto(String logStrObjeto) {
		this.logStrObjeto = logStrObjeto;
	}

	public String getLogStrIp() {
		return logStrIp;
	}

	public void setLogStrIp(String logStrIp) {
		this.logStrIp = logStrIp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((logIntCod == null) ? 0 : logIntCod.hashCode());
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
		LogAcesso other = (LogAcesso) obj;
		if (logIntCod == null) {
			if (other.logIntCod != null)
				return false;
		} else if (!logIntCod.equals(other.logIntCod))
			return false;
		return true;
	}

}
