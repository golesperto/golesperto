package com.sdp.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sdp.util.Status;
import com.sdp.util.Utils;

@SuppressWarnings("serial")
@Entity
@Table(name = "sdp_MENU")
public class Menu implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MEN_INT_COD")
	private Integer menIntCod;

	@Column(name = "MEN_STR_NOME", nullable = false, length = 50)
	private String menStrNome;

	@Column(name = "MEN_STR_ICONE", nullable = false, length = 50)
	private String menStrIcone;

	@Column(name = "MEN_CHA_STATUS", nullable = false, length = 1)
	private Character menChaStatus;

	public Menu() {

	}

	public String getStatusStr() {
		return menChaStatus != null && menChaStatus.equals(Status.STATUS_NORMAL) ? "Normal" : "Bloqueado";
	}

	public Integer getMenIntCod() {
		return menIntCod;
	}

	public void setMenIntCod(Integer menIntCod) {
		this.menIntCod = menIntCod;
	}

	public String getMenStrNome() {
		return menStrNome;
	}

	public void setMenStrNome(String menStrNome) {
		this.menStrNome = menStrNome;
	}

	public String getMenStrIcone() {
		return menStrIcone;
	}

	public String getIconeImagem() {
		return Utils.getIconeImagem(menStrIcone);
	}

	public void setMenStrIcone(String menStrIcone) {
		this.menStrIcone = menStrIcone;
	}

	public Character getMenChaStatus() {
		return menChaStatus;
	}

	public void setMenChaStatus(Character menChaStatus) {
		this.menChaStatus = menChaStatus;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((menIntCod == null) ? 0 : menIntCod.hashCode());
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
		Menu other = (Menu) obj;
		if (menIntCod == null) {
			if (other.menIntCod != null)
				return false;
		} else if (!menIntCod.equals(other.menIntCod))
			return false;
		return true;
	}

}
