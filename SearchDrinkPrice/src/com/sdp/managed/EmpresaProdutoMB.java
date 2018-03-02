package com.sdp.managed;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import com.sdp.bo.EmpresaProdutoBO;
import com.sdp.bo.ProdutoBO;
import com.sdp.entity.EmpresaProduto;
import com.sdp.entity.Produto;
import com.sdp.util.Mensagens;
import com.sdp.util.Status;

@ManagedBean
@ViewScoped
public class EmpresaProdutoMB extends GenericManagedBean<EmpresaProduto> {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(EmpresaProdutoMB.class);

	private List<SelectItem> status;

	private List<Produto> produtos;

	private ProdutoBO produtoBO;

	public EmpresaProdutoMB() {
		setClazz(EmpresaProduto.class);
		setBo(new EmpresaProdutoBO());
		produtoBO = new ProdutoBO();
		carregarStatus();
		carregarProdutos();
		carregarEntityList();
	}
	
	public void carregarEntityList(){
		try {
			setEntityList(((EmpresaProdutoBO) getBo()).listByEmpresa(getEmpresaSelecionada().getEmpIntCod()));
		} catch (Exception e) {
			e.printStackTrace();
			addError(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), "");
			log.error(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), e);
		}
	}
	
	@Override
	public void actionPersist(ActionEvent event) {
		getEntity().setEmpresa(getEmpresaSelecionada());
		super.actionPersist(event);
		carregarEntityList();
	}
	
	public void actionRemove() {
		super.actionRemove(null);
		carregarEntityList();
	}
	
	@Override
	public void actionNew(ActionEvent event) {
		setEntity(new EmpresaProduto());
	}

	public void carregarProdutos() {
		try {
			produtos = produtoBO.listAll();
		} catch (Exception e) {
			e.printStackTrace();
			addError(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), "");
			log.error(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), e);
		}
	}

	public void carregarStatus() {
		this.status = new ArrayList<>();
		Status.getList().forEach(s -> this.status.add(new SelectItem(s.getChave(), s.getValor())));
	}
	
	public List<SelectItem> getStatus() {
		return status;
	}

	public void setStatus(List<SelectItem> status) {
		this.status = status;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

}