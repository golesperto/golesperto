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
@Table(name = "SDP_PRODUTO_VOLUME")
public class ProdutoVolume implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PRV_INT_COD")
	private Integer prvIntCod;

	@Column(name = "PRV_STR_VOLUME", nullable = false, length = 150)
	private String prvStrVolume;

	public ProdutoVolume() {
	}

	public Integer getPrvIntCod() {
		return prvIntCod;
	}

	public void setPrvIntCod(Integer prvIntCod) {
		this.prvIntCod = prvIntCod;
	}

	public String getPrvStrVolume() {
		return prvStrVolume;
	}

	public void setPrvStrVolume(String prvStrVolume) {
		this.prvStrVolume = prvStrVolume;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((prvIntCod == null) ? 0 : prvIntCod.hashCode());
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
		ProdutoVolume other = (ProdutoVolume) obj;
		if (prvIntCod == null) {
			if (other.prvIntCod != null)
				return false;
		} else if (!prvIntCod.equals(other.prvIntCod))
			return false;
		return true;
	}

}
