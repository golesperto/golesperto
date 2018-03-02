package com.sdp.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import com.sdp.entity.Objeto;
import com.sdp.entity.Perfil;
import com.sdp.entity.PerfilObjeto;
import com.sdp.exception.BancoDadosException;

public class PerfilObjetoBO extends BO<PerfilObjeto> {
	private static final long serialVersionUID = 1L;

	public PerfilObjetoBO() {
		super();
		setClazz(PerfilObjeto.class);
	}

	public List<PerfilObjeto> listByIdPerfil(Integer perIntCod) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("perfil.perIntCod", perIntCod);
			return listByFields(params);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<PerfilObjeto> listAll() {
		try {
			String jpql = "select po from PerfilObjeto po order by po.perfil.perStrNome asc";
			Query q = getDao().createQuery(jpql);
			return q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void deleteByIdPerfil(Integer perIntCod) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(" DELETE FROM sdp_PERFIL_OBJETO WHERE PER_INT_COD = ?1 ");
			Query q = getDao().createNativeQuery(sb.toString());
			q.setParameter(1, perIntCod);
			getDao().executeQuery(q);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<PerfilObjeto> listPerfilObjetos() {
		try {
			PerfilBO perfilBO = new PerfilBO();
			StringBuilder sb = new StringBuilder();
			sb.append(" SELECT DISTINCT PER.PER_INT_COD FROM sdp_PERFIL_OBJETO POB ");
			sb.append(" INNER JOIN sdp_PERFIL PER ON PER.PER_INT_COD = POB.PER_INT_COD ");
			Query q = getDao().createNativeQuery(sb.toString(), Perfil.class);
			List<Perfil> perfis = q.getResultList();
			List<PerfilObjeto> perfilObjetos = new ArrayList<PerfilObjeto>();
			sb = new StringBuilder();
			sb.append(" SELECT OBJ.* FROM sdp_PERFIL_OBJETO POB ");
			sb.append(" INNER JOIN sdp_OBJETO OBJ ON OBJ.OBJ_INT_COD = POB.OBJ_INT_COD ");
			sb.append(" WHERE POB.PER_INT_COD = ?1 ");
			sb.append(" ORDER BY OBJ.OBJ_STR_OBJETO ASC ");
			for (Perfil p : perfis) {
				try {
					Perfil perfil = perfilBO.find(p.getPerIntCod());
					q = getDao().createNativeQuery(sb.toString(), Objeto.class);
					q.setParameter(1, p.getPerIntCod());
					List<Objeto> objetos = q.getResultList();
					PerfilObjeto po = new PerfilObjeto(perfil, objetos);
					perfilObjetos.add(po);
				} catch (BancoDadosException e) {
					e.printStackTrace();
				}
			}
			return perfilObjetos;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
