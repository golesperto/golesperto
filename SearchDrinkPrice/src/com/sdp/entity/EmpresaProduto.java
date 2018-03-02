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
import com.sdp.util.Utils;

@SuppressWarnings("serial")
@Entity
@Table(name = "SDP_EMPRESA_PRODUTO")
public class EmpresaProduto implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EPR_INT_COD")
	private Integer eprIntCod;

	@Column(name = "EPR_FLT_VALOR", nullable = false)
	private Double eprFltValor;

	@Column(name = "EPR_CHA_STATUS", nullable = false, length = 1)
	private String eprChaStatus;

	@ManyToOne
	@JoinColumn(name = "EMP_INT_COD")
	private Empresa empresa;

	@ManyToOne
	@JoinColumn(name = "PRO_INT_COD")
	private Produto produto;

	public EmpresaProduto() {
	}

	public Integer getEprIntCod() {
		return eprIntCod;
	}

	public void setEprIntCod(Integer eprIntCod) {
		this.eprIntCod = eprIntCod;
	}

	public Double getEprFltValor() {
		return eprFltValor;
	}

	public void setEprFltValor(Double eprFltValor) {
		this.eprFltValor = eprFltValor;
	}

	public String getEprChaStatus() {
		return eprChaStatus;
	}

	public String getStatus() {
		return eprChaStatus != null && eprChaStatus.equals(Status.NORMAL.getChave().toString()) ? Utils.HTML_DISPONIVEL
				: Utils.HTML_NAO_DISPONIVEL;
	}

	public void setEprChaStatus(String eprChaStatus) {
		this.eprChaStatus = eprChaStatus;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((eprIntCod == null) ? 0 : eprIntCod.hashCode());
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
		EmpresaProduto other = (EmpresaProduto) obj;
		if (eprIntCod == null) {
			if (other.eprIntCod != null)
				return false;
		} else if (!eprIntCod.equals(other.eprIntCod))
			return false;
		return true;
	}

}
