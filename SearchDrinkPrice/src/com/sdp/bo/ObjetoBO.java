package com.sdp.bo;

import java.util.List;

import javax.persistence.Query;

import com.sdp.entity.Objeto;

public class ObjetoBO extends BO<Objeto> {
	private static final long serialVersionUID = 1L;

	public ObjetoBO() {
		super();
		setClazz(Objeto.class);
	}

	public List<Objeto> listAll() {
		try {
			String jpql = "select o from Objeto o order by o.objStrObjeto asc";
			Query q = getDao().createQuery(jpql);
			return q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Objeto> listByStatus(Character objChaStatus) {
		try {
			String jpql = "select o from Objeto o where o.objChaStatus =:objChaStatus order by o.objStrObjeto asc";
			Query q = getDao().createQuery(jpql);
			q.setParameter("objChaStatus", objChaStatus);
			return q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
