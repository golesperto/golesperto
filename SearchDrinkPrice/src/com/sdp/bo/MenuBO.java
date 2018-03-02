package com.sdp.bo;

import java.util.List;

import javax.persistence.Query;

import com.sdp.entity.Menu;

public class MenuBO extends BO<Menu> {

	private static final long serialVersionUID = 1L;

	public MenuBO() {
		super();
		setClazz(Menu.class);
	}

	public List<Menu> listAll() {
		try {
			String jpql = "select m from Menu m order by m.menStrNome asc";
			Query q = getDao().createQuery(jpql);
			return q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Menu> listByStatus(Character menChaStatus) {
		try {
			String jpql = "select m from Menu m where m.menChaStatus =:menChaStatus order by m.menStrNome asc";
			Query q = getDao().createQuery(jpql);
			q.setParameter("menChaStatus", menChaStatus);
			return q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
