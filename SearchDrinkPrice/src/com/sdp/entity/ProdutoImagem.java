package com.sdp.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "SDP_PRODUTO_IMAGEM")
public class ProdutoImagem implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PRI_INT_COD")
	private Integer priIntCod;

	@Lob
	@Column(name = "PRI_BIT_IMAGEM", nullable = false)
	private byte[] priBitImagem;

	@Column(name = "PRI_STR_TIPOIMAGEM", nullable = false, length = 10)
	private String priStrTipoImagem;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRO_INT_COD", nullable = false)
	private Produto produto;

	public ProdutoImagem() {
	}

	public Integer getPriIntCod() {
		return priIntCod;
	}

	public void setPriIntCod(Integer priIntCod) {
		this.priIntCod = priIntCod;
	}

	public byte[] getPriBitImagem() {
		return priBitImagem;
	}

	public void setPriBitImagem(byte[] priBitImagem) {
		this.priBitImagem = priBitImagem;
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
		result = prime * result + ((priIntCod == null) ? 0 : priIntCod.hashCode());
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
		ProdutoImagem other = (ProdutoImagem) obj;
		if (priIntCod == null) {
			if (other.priIntCod != null)
				return false;
		} else if (!priIntCod.equals(other.priIntCod))
			return false;
		return true;
	}

	public String getPriStrTipoImagem() {
		return priStrTipoImagem;
	}

	public void setPriStrTipoImagem(String priStrTipoImagem) {
		this.priStrTipoImagem = priStrTipoImagem;
	}
}
