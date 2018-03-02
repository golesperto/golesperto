package com.sdp.entity;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.List;

import javax.imageio.ImageIO;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.primefaces.model.StreamedContent;

import com.sdp.util.Utils;

@SuppressWarnings("serial")
@Entity
@Table(name = "SDP_PRODUTO")
public class Produto implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PRO_INT_COD")
	private Integer proIntCod;

	@Column(name = "PRO_STR_NOME", nullable = false, length = 150)
	private String proStrNome;

	@ManyToOne
	@JoinColumn(name = "MAR_INT_COD")
	private Marca marca;

	@ManyToOne
	@JoinColumn(name = "PRT_INT_COD")
	private ProdutoTipo tipo;

	@ManyToOne
	@JoinColumn(name = "PRV_INT_COD")
	private ProdutoVolume volume;

	@OneToOne(mappedBy = "produto", cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY)
	private ProdutoImagem imagem;

	@OneToMany(mappedBy = "produto")
	private List<EmpresaProduto> empresaProdutos;

	public Produto() {
	}

	public Integer getProIntCod() {
		return proIntCod;
	}

	public void setProIntCod(Integer proIntCod) {
		this.proIntCod = proIntCod;
	}

	public String getProStrNome() {
		return proStrNome;
	}

	public void setProStrNome(String proStrNome) {
		this.proStrNome = proStrNome;
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public ProdutoTipo getTipo() {
		return tipo;
	}

	public void setTipo(ProdutoTipo tipo) {
		this.tipo = tipo;
	}

	public ProdutoVolume getVolume() {
		return volume;
	}

	public void setVolume(ProdutoVolume volume) {
		this.volume = volume;
	}

	public StreamedContent getImagemProduto() {
		try {
			if (imagem != null) {
				return Utils.getStreamedContent(imagem.getPriBitImagem(), imagem.getPriStrTipoImagem());
			} else {
				return Utils.getImageDefault();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((proIntCod == null) ? 0 : proIntCod.hashCode());
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
		Produto other = (Produto) obj;
		if (proIntCod == null) {
			if (other.proIntCod != null)
				return false;
		} else if (!proIntCod.equals(other.proIntCod))
			return false;
		return true;
	}

	public ProdutoImagem getImagem() {
		return imagem;
	}

	public void setImagem(ProdutoImagem imagem) {
		this.imagem = imagem;
	}

	public List<EmpresaProduto> getEmpresaProdutos() {
		return empresaProdutos;
	}

	public void setEmpresaProdutos(List<EmpresaProduto> empresaProdutos) {
		this.empresaProdutos = empresaProdutos;
	}

}
