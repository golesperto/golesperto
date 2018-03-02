package com.sdp.bo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.sdp.connection.GenericDAO;
import com.sdp.entity.Empresa;
import com.sdp.entity.PerfilObjeto;
import com.sdp.exception.BancoDadosException;
import com.sdp.exception.IntegridadeReferencialException;
import com.sdp.exception.RegistroExistenteException;

public class BO<E extends Serializable> implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String FILTRO_GENERICO_QUERY = "FILTRO_GENERICO_QUERY";

	private transient GenericDAO<E> dao;

	private Class<E> clazz;

	private static Logger log = Logger.getLogger(BO.class);

	private String persistenceUnitName;

	public BO(String persistenceUnitName) {
		this.persistenceUnitName = persistenceUnitName;
	}

	public BO() {
		this.persistenceUnitName = GenericDAO.PU_SDP;
	}

	public void clearEntityManager() {
		dao.clearEntityManager();
	}

	public GenericDAO<E> getDao() {
		if (this.dao == null) {
			this.dao = new GenericDAO<E>(persistenceUnitName);
		}
		return this.dao;
	}

	public E detach(E entity) throws BancoDadosException {
		return getDao().detach(entity);
	}

	public boolean merge(E entity)
			throws BancoDadosException, IntegridadeReferencialException, RegistroExistenteException {
		return getDao().merge(entity);
	}

	public List<E> detachBatch(List<E> entities) throws BancoDadosException {
		return getDao().detachBatch(entities);
	}

	public E find(E entity) throws BancoDadosException {
		return getDao().find(entity);
	}

	public List<E> findBatch(List<E> entities) throws BancoDadosException {
		return getDao().findBatch(entities);
	}

	public E find(Object primaryKey) throws BancoDadosException {
		return getDao().find(getClazz(), primaryKey);
	}

	public boolean refresh(E entity) throws BancoDadosException {
		return getDao().refresh(entity);
	}

	public boolean refreshBatch(List<E> entities) throws BancoDadosException {
		return getDao().refreshBatch(entities);
	}

	public boolean persist(E entity)
			throws BancoDadosException, RegistroExistenteException, IntegridadeReferencialException {
		if (getDao().find(entity) != null) {
			return getDao().merge(entity);
		} else {
			return getDao().persist(entity);
		}
	}

	public boolean persistDetach(E entity)
			throws BancoDadosException, RegistroExistenteException, IntegridadeReferencialException {
		entity = getDao().find(entity);
		if (entity != null) {
			getDao().clearEntityManager();
			return getDao().merge(entity);
		} else {
			return getDao().persist(entity);
		}
	}

	public boolean persistForce(E entity)
			throws BancoDadosException, RegistroExistenteException, IntegridadeReferencialException {
		return getDao().persist(entity);
	}

	public void persistBatchSync(List<E> entities) throws BancoDadosException, RegistroExistenteException {
		getDao().persistBatchSync(entities);
	}

	public void persistBatch(List<E> entities) throws BancoDadosException, RegistroExistenteException {
		getDao().persistBatch(entities);
	}

	public void mergeBatch(List<E> entities)
			throws BancoDadosException, RegistroExistenteException, IntegridadeReferencialException {
		getDao().mergeBatch(entities);
	}

	public boolean remove(E entity) throws BancoDadosException, IntegridadeReferencialException {
		return getDao().remove(entity);
	}

	public void removeBatch(List<E> entities) throws BancoDadosException, IntegridadeReferencialException {
		getDao().removeBatch(entities);
	}

	public List<E> list(final String jpql) throws BancoDadosException {
		return getDao().list(jpql);
	}

	public List<E> list(final Query jpql) throws BancoDadosException {
		return getDao().list(jpql);
	}

	public List<E> listAll() throws BancoDadosException {
		return getDao().listAll(getClazz());
	}

	public Class<E> getClazz() {
		return clazz;
	}

	public void setClazz(Class<E> clazz) {
		this.clazz = clazz;
	}

	public String getPersistenceUnitName() {
		return persistenceUnitName;
	}

	public void setPersistenceUnitName(String persistenceUnitName) {
		this.persistenceUnitName = persistenceUnitName;
	}

	protected E findByFields(Map<String, Object> filtros) throws BancoDadosException {
		return getDao().findByFields(clazz, filtros);
	}

	protected List<E> listByFields(Map<String, Object> filtros, String[] ordenacao) throws BancoDadosException {
		return getDao().listByFields(clazz, filtros, ordenacao, null);
	}

	protected List<E> listByFields(Map<String, Object> filtros, String[] ordenacao, String[] hints)
			throws BancoDadosException {
		return getDao().listByFields(clazz, filtros, ordenacao, hints);
	}

	protected List<E> listByFields(Map<String, Object> filtros) throws BancoDadosException {
		return listByFields(filtros, null, null);
	}

	protected List<E> listByFieldsHint(Map<String, Object> filtros, String... hints) throws BancoDadosException {
		return listByFields(filtros, null, hints);
	}

	public String tratarCamposIn(List<String> lista) {
		if (lista != null && !lista.isEmpty()) {
			String out = "";
			for (String s : lista) {
				out += ",'" + s + "'";
			}
			out = out.replaceFirst(",", "");
			return out;
		}
		return "";
	}

	public String tratarCamposInEmpresa(List<Empresa> lista) {
		if (lista != null && !lista.isEmpty()) {
			String out = "";
			for (Empresa e : lista) {
				if (e != null) {
					out += "," + e.getEmpIntCod();
				}
			}
			out = out.replaceFirst(",", "");
			return out;
		}
		return "";
	}

	public String tratarCamposInSemAspa(List<String> lista) {
		if (lista != null && !lista.isEmpty()) {
			String out = "";
			for (String s : lista) {
				out += "," + s;
			}
			out = out.replaceFirst(",", "");
			return out;
		}
		return "";
	}

	public String tratarCamposInMenu(List<PerfilObjeto> perfilObjetos) {
		if (perfilObjetos != null && !perfilObjetos.isEmpty()) {
			String out = "";
			for (PerfilObjeto po : perfilObjetos) {
				out += "," + po.getObjeto().getMenu().getMenIntCod();
			}
			out = out.replaceFirst(",", "");
			return out;
		}
		return "";
	}

}
