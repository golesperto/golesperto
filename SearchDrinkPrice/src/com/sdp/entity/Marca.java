package com.sdp.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sdp.util.Status;
import com.sdp.util.Utils;

@SuppressWarnings("serial")
@Entity
@Table(name = "SDP_MARCA")
public class Marca implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MAR_INT_COD")
	private Integer marIntCod;

	@Column(name = "MAR_STR_NOME", nullable = false, length = 150)
	private String marStrNome;

	public Marca() {
	}

	public Integer getMarIntCod() {
		return marIntCod;
	}

	public void setMarIntCod(Integer marIntCod) {
		this.marIntCod = marIntCod;
	}

	public String getMarStrNome() {
		return marStrNome;
	}

	public void setMarStrNome(String marStrNome) {
		this.marStrNome = marStrNome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((marIntCod == null) ? 0 : marIntCod.hashCode());
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
		Marca other = (Marca) obj;
		if (marIntCod == null) {
			if (other.marIntCod != null)
				return false;
		} else if (!marIntCod.equals(other.marIntCod))
			return false;
		return true;
	}

}
