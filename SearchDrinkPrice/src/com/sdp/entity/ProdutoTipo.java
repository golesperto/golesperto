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
@Table(name = "SDP_PRODUTO_TIPO")
public class ProdutoTipo implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PRT_INT_COD")
	private Integer prtIntCod;

	@Column(name = "PRT_STR_TIPO", nullable = false, length = 150)
	private String prtStrTipo;

	public ProdutoTipo() {
	}

	public Integer getPrtIntCod() {
		return prtIntCod;
	}

	public void setPrtIntCod(Integer prtIntCod) {
		this.prtIntCod = prtIntCod;
	}

	public String getPrtStrTipo() {
		return prtStrTipo;
	}

	public void setPrtStrTipo(String prtStrTipo) {
		this.prtStrTipo = prtStrTipo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((prtIntCod == null) ? 0 : prtIntCod.hashCode());
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
		ProdutoTipo other = (ProdutoTipo) obj;
		if (prtIntCod == null) {
			if (other.prtIntCod != null)
				return false;
		} else if (!prtIntCod.equals(other.prtIntCod))
			return false;
		return true;
	}

}
