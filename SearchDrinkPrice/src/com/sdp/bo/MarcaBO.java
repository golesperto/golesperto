package com.sdp.bo;

import java.util.List;

import javax.persistence.Query;

import com.sdp.entity.Marca;

public class MarcaBO extends BO<Marca> {

	private static final long serialVersionUID = 1L;

	public MarcaBO() {
		super();
		setClazz(Marca.class);
	}

	public List<Marca> listAllOrdenado() throws Exception {
		String jpql = "select m from Marca m order by m.marStrNome asc";
		Query q = getDao().createQuery(jpql);
		return q.getResultList();
	}
}