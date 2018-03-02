package com.sdp.bo;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.sdp.entity.ProdutoTipo;

public class ProdutoTipoBO extends BO<ProdutoTipo> {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(ProdutoTipoBO.class);

	public ProdutoTipoBO() {
		super();
		setClazz(ProdutoTipo.class);
	}

	public List<ProdutoTipo> listAllOrdenado() throws Exception {
		String jpql = "select p from ProdutoTipo p order by p.prtStrTipo asc";
		Query q = getDao().createQuery(jpql);
		return q.getResultList();
	}
}