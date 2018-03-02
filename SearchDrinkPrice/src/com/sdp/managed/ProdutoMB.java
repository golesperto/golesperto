package com.sdp.managed;

import java.io.IOException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;

import com.sdp.bo.MarcaBO;
import com.sdp.bo.ProdutoBO;
import com.sdp.bo.ProdutoImagemBO;
import com.sdp.bo.ProdutoTipoBO;
import com.sdp.bo.ProdutoVolumeBO;
import com.sdp.entity.Marca;
import com.sdp.entity.Produto;
import com.sdp.entity.ProdutoImagem;
import com.sdp.entity.ProdutoTipo;
import com.sdp.entity.ProdutoVolume;
import com.sdp.util.Mensagens;

@ManagedBean
@ViewScoped
public class ProdutoMB extends GenericManagedBean<Produto> {
	private static final long serialVersionUID = 9116181950356658019L;

	private static Logger log = Logger.getLogger(ProdutoMB.class);

	private List<ProdutoTipo> produtosTipo;

	private ProdutoTipoBO produtoTipoBO;

	private List<ProdutoVolume> produtosVolume;

	private ProdutoVolumeBO produtoVolumeBO;

	private List<Marca> marcas;

	private MarcaBO marcaBO;

	private ProdutoImagemBO produtoImagemBO;

	public ProdutoMB() {
		setBo(new ProdutoBO());
		setClazz(Produto.class);
		produtoTipoBO = new ProdutoTipoBO();
		produtoVolumeBO = new ProdutoVolumeBO();
		marcaBO = new MarcaBO();
		produtoImagemBO = new ProdutoImagemBO();
		carregarEntityList();
		carregarProdutosTipo();
		carregarProdutosVolume();
		carregarMarcas();
	}

	public void carregarEntityList() {
		try {
			setEntityList(((ProdutoBO) getBo()).listAll());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), e);
			addError(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), "");
		}
	}

	public void upload(FileUploadEvent event) {
		try {
			String tipoImagem = event.getFile().getFileName();
			tipoImagem = tipoImagem.substring(tipoImagem.indexOf(".") + 1, tipoImagem.length());
			ProdutoImagem produtoImagem = new ProdutoImagem();
			produtoImagem.setPriStrTipoImagem(tipoImagem);
			produtoImagem.setPriBitImagem(event.getFile().getContents());
			produtoImagem.setProduto(getEntity());
			getEntity().setImagem(produtoImagem);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), e);
			addError(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), "");
		}
	}

	@Override
	public void actionPersist(ActionEvent event) {
		super.actionPersist(event);
		carregarEntityList();
	}

	public void actionRemove() {
		super.actionRemove(null);
		carregarEntityList();
	}

	@Override
	public void actionNew(ActionEvent event) {
		setEntity(new Produto());
	}

	public void carregarProdutosTipo() {
		try {
			produtosTipo = produtoTipoBO.listAll();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), e);
			addError(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), "");
		}
	}

	public void carregarProdutosVolume() {
		try {
			produtosVolume = produtoVolumeBO.listAll();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), e);
			addError(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), "");
		}
	}

	public void carregarMarcas() {
		try {
			marcas = marcaBO.listAll();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), e);
			addError(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), "");
		}
	}

	public List<ProdutoTipo> getProdutosTipo() {
		return produtosTipo;
	}

	public void setProdutosTipo(List<ProdutoTipo> produtosTipo) {
		this.produtosTipo = produtosTipo;
	}

	public List<ProdutoVolume> getProdutosVolume() {
		return produtosVolume;
	}

	public void setProdutosVolume(List<ProdutoVolume> produtosVolume) {
		this.produtosVolume = produtosVolume;
	}

	public List<Marca> getMarcas() {
		return marcas;
	}

	public void setMarcas(List<Marca> marcas) {
		this.marcas = marcas;
	}
}
