package com.sdp.bo;

import java.util.List;

import javax.persistence.Query;

import com.sdp.entity.Perfil;

public class PerfilBO extends BO<Perfil> {
	private static final long serialVersionUID = 1L;

	public PerfilBO() {
		super();
		setClazz(Perfil.class);
	}

	public List<Perfil> listAll() {
		try {
			String jpql = "select p from Perfil p order by p.perStrNome asc";
			Query q = getDao().createQuery(jpql);
			return q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Perfil> listByStatus(Character perChaStatus) {
		try {
			String jpql = "select p from Perfil p where p.perChaStatus =:perChaStatus order by p.perStrNome asc";
			Query q = getDao().createQuery(jpql);
			q.setParameter("perChaStatus", perChaStatus);
			return q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Perfil findPerfil(Character perChaTipo) {
		try {
			PerfilBO perfilBO = new PerfilBO();
			String jpql = "select p from Perfil p where p.perChaTipo =:perChaTipo";
			Query q = perfilBO.getDao().createQuery(jpql);
			q.setParameter("perChaTipo", perChaTipo);
			List<Perfil> list = q.getResultList();
			return list != null && !list.isEmpty() ? list.get(0) : null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
