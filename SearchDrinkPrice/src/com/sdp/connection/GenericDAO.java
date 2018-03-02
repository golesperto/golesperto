package com.sdp.connection;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

import org.apache.log4j.Logger;
import org.eclipse.persistence.exceptions.DatabaseException;

import com.sdp.exception.BancoDadosException;
import com.sdp.exception.IntegridadeReferencialException;
import com.sdp.exception.RegistroExistenteException;

public class GenericDAO<E> extends GenericListDAO<E> implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String PU_SDP = "PU_SDP";

	private static Logger log = Logger.getLogger(GenericDAO.class);

	public GenericDAO(String persistenceUnitName) {
		super(persistenceUnitName);
	}

	public GenericDAO() {
		super(PU_SDP);
	}

	public EntityTransaction getTransaction() {
		return entityManager.getTransaction();
	}

	public boolean persist(E entity) throws BancoDadosException, RegistroExistenteException {
		boolean persist = false;
		EntityTransaction tx = getTransaction();
		if (entity != null) {
			try {
				if (!tx.isActive())
					tx.begin();
				entityManager.persist(entity);
				entityManager.flush();
				tx.commit();
				// refresh(entity);
				persist = true;
			} catch (Exception e) {
				if (tx.isActive()) {
					tx.rollback();
				}
				// log.error("Erro no persist", e);
				throw new BancoDadosException(e);
			}
		}
		return persist;
	}

	public boolean merge(E entity)
			throws BancoDadosException, IntegridadeReferencialException, RegistroExistenteException {
		boolean merge = false;
		EntityTransaction tx = getTransaction();
		if (entity != null) {
			try {
				if (!tx.isActive())
					tx.begin();
				entityManager.merge(entity);
				entityManager.flush();
				tx.commit();
				// refresh(entity);
				merge = true;
			} catch (Exception e) {
				if (tx.isActive()) {
					tx.rollback();
				}
				log.error("Erro no merge", e);
				throw new BancoDadosException(e);
			}
		}
		return merge;
	}

	public Long persistNative(String insert) throws BancoDadosException {
		Long idGerado = null;
		EntityTransaction tx = getTransaction();
		try {
			if (!tx.isActive())
				tx.begin();
			java.sql.Connection connection = entityManager.unwrap(java.sql.Connection.class);
			Statement pstmt = connection.createStatement();
			System.out.println(insert);
			pstmt.execute(insert, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next())
				idGerado = rs.getLong(1);
			pstmt.close();
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			log.error("Erro no persistNative", e);
			throw new BancoDadosException(e);
		} finally {
			return idGerado;
		}
	}

	public void persistBatchNative(List<String> inserts) throws BancoDadosException {
		EntityTransaction tx = getTransaction();
		try {
			// entityManager.setFlushMode(FlushModeType.COMMIT);
			if (!tx.isActive())
				tx.begin();
			int j = 0;
			for (String insert : inserts) {
				Query q = entityManager.createNativeQuery(insert);
				q.executeUpdate();
				if (++j % 500 == 0) {
					entityManager.flush();
				}
			}
			entityManager.flush();
			tx.commit();
			// entityManager.setFlushMode(FlushModeType.AUTO);
		} catch (Exception e) {
			tx.rollback();
			log.error("Erro no merge", e);
			throw new BancoDadosException(e);
		}
	}

	public synchronized void persistBatchSync(List<E> entities) throws BancoDadosException, RegistroExistenteException {
		persistBatch(entities);
	}

	public void persistBatch(List<E> entities) throws BancoDadosException, RegistroExistenteException {
		// entityManager.setFlushMode(FlushModeType.COMMIT);
		// System.out.println("Comecou 'persistBatch' " + Utils.dateToString(new
		// Date(), "HH:mm:ss"));
		EntityTransaction tx = getTransaction();
		if (!tx.isActive())
			tx.begin();
		int i = 0;
		for (E entity : entities) {
			try {
				entityManager.persist(entity);
				if (++i % 2000 == 0) {
					entityManager.flush();
					if (i % 100000 == 0) {
						entityManager.clear();
					}
				}
			} catch (Exception e) {
				if (tx.isActive())
					tx.rollback();
				log.error("Erro no persistBatch", e);
				throw new BancoDadosException(e);
			}
		}
		entityManager.flush();
		// entityManager.clear();
		tx.commit();
		// System.out.println("Terminou 'persistBatch' " +
		// Utils.dateToString(new Date(), "HH:mm:ss"));
	}

	public void mergeBatch(List<E> entities)
			throws BancoDadosException, RegistroExistenteException, IntegridadeReferencialException {
		EntityTransaction tx = getTransaction();
		if (!tx.isActive())
			tx.begin();
		int i = 0;
		for (E entity : entities) {
			try {
				entityManager.merge(entity);
				if (i++ % 2000 == 0) {
					entityManager.flush();
					// entityManager.clear();
				}
			} catch (Exception e) {
				if (tx.isActive()) {
					tx.rollback();
				}
				log.error("Erro no merge", e);
				throw new BancoDadosException(e);
			}
		}
		entityManager.flush();
		// entityManager.clear();
		tx.commit();
	}

	public void clearEntityManager() {
		entityManager.clear();
	}

	public boolean remove(E entity) throws BancoDadosException, IntegridadeReferencialException {
		boolean remove = false;
		EntityTransaction tx = getTransaction();
		if (!tx.isActive())
			tx.begin();
		try {
			if (entity != null) {
				entity = entityManager.merge(entity);
				entityManager.remove(entity);
				entityManager.flush();
				tx.commit();
				remove = true;
			}
		} catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			throw new BancoDadosException(e);
		}
		return remove;
	}

	public void removeBatch(List<E> entities) throws BancoDadosException, IntegridadeReferencialException {
		EntityTransaction tx = getTransaction();
		if (!tx.isActive())
			tx.begin();
		int i = 0;
		for (E entity : entities) {
			try {
				// entity = entityManager.merge(entity);
				entityManager.remove(entityManager.merge(entity));
				if (i++ % 2000 == 0) {
					entityManager.flush();
					entityManager.clear();
				}
			} catch (Exception e) {
				if (tx.isActive()) {
					tx.rollback();
				}
				throw new BancoDadosException(e);
			}
		}
		// entityManager.flush();
		entityManager.clear();
		tx.commit();
	}

	public boolean refreshBatch(List<E> entities) throws BancoDadosException {
		boolean refresh = false;
		for (E entity : entities) {
			refresh(entity);
			refresh = true;
		}
		return refresh;
	}

	public boolean refresh(E entity) throws BancoDadosException {
		boolean refresh = false;
		if (entity != null) {
			entityManager.refresh(entityManager.merge(entity));
			refresh = true;
		}
		return refresh;
	}

	public Query createQuery(String jpql) throws BancoDadosException {
		Query query = null;
		if (jpql != null && !jpql.isEmpty()) {
			query = entityManager.createQuery(jpql);
		}
		return query;
	}

	public Query createNamedQuery(String nameQuery) throws BancoDadosException {
		Query query = null;
		if (nameQuery != null && !nameQuery.isEmpty()) {
			query = entityManager.createNamedQuery(nameQuery);
		}
		return query;
	}

	public int executeQuery(Query query) throws BancoDadosException {
		EntityTransaction tx = getTransaction();
		int qtdRegistros = 0;
		if (!tx.isActive())
			tx.begin();
		if (query != null) {
			try {
				qtdRegistros = query.executeUpdate();
				entityManager.flush();
				tx.commit();
			} catch (Exception e) {
				if (tx.isActive()) {
					tx.rollback();
				}
				// log.error("Erro no persist", e);
				throw new BancoDadosException(e);
			}
		}
		return qtdRegistros;
	}

	public Query createNativeQuery(String sql, Class clazz) throws BancoDadosException {
		Query query = null;
		if (sql != null && !sql.isEmpty()) {
			query = entityManager.createNativeQuery(sql, clazz);
		}
		return query;
	}

	public Query createNativeQuery(String sql, String resultSetMapping) throws BancoDadosException {
		Query query = null;
		if (sql != null && !sql.isEmpty()) {
			query = entityManager.createNativeQuery(sql, resultSetMapping);
		}
		return query;
	}

	public Query createNativeQuery(String sql) throws BancoDadosException {
		Query query = null;
		if (sql != null && !sql.isEmpty()) {
			query = entityManager.createNativeQuery(sql);
		}
		return query;
	}

	public StoredProcedureQuery createStoredProcedure(String nome) {
		return entityManager.createStoredProcedureQuery(nome);
	}

}
