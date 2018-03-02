package com.sdp.bo;

import java.util.List;

import javax.persistence.Query;

import com.sdp.entity.Produto;

public class ProdutoBO extends BO<Produto> {

	private static final long serialVersionUID = 1L;

	public ProdutoBO() {
		super();
		setClazz(Produto.class);
	}

	public List<Produto> listAllOrdenado() throws Exception {
		String jpql = "select p from Produto p order by p.proStrNome asc";
		Query q = getDao().createQuery(jpql);
		return q.getResultList();
	}

}