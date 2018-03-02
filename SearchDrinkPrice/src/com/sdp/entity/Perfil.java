package com.sdp.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sdp.util.Status;

@SuppressWarnings("serial")
@Entity
@Table(name = "sdp_PERFIL")
public class Perfil implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PER_INT_COD")
	private Integer perIntCod;

	@Column(name = "PER_STR_NOME", nullable = false, length = 100)
	private String perStrNome;

	@Column(name = "PER_CHA_STATUS", nullable = false, length = 1)
	private Character perChaStatus;

	@Column(name = "PER_CHA_TIPO", nullable = false, unique = true, length = 1)
	private String perChaTipo;

	public Perfil() {
	}

	public String getStatusStr() {
		return perChaStatus != null && perChaStatus.equals(Status.STATUS_NORMAL) ? "Normal" : "Bloqueado";
	}

	public Integer getPerIntCod() {
		return perIntCod;
	}

	public void setPerIntCod(Integer perIntCod) {
		this.perIntCod = perIntCod;
	}

	public String getPerStrNome() {
		return perStrNome;
	}

	public void setPerStrNome(String perStrNome) {
		this.perStrNome = perStrNome;
	}

	public String getPerChaTipo() {
		return perChaTipo;
	}

	public void setPerChaTipo(String perChaTipo) {
		this.perChaTipo = perChaTipo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((perIntCod == null) ? 0 : perIntCod.hashCode());
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
		Perfil other = (Perfil) obj;
		if (perIntCod == null) {
			if (other.perIntCod != null)
				return false;
		} else if (!perIntCod.equals(other.perIntCod))
			return false;
		return true;
	}

	public Character getPerChaStatus() {
		return perChaStatus;
	}

	public void setPerChaStatus(Character perChaStatus) {
		this.perChaStatus = perChaStatus;
	}
}
