package com.sdp.connection;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;

public class Connection implements Serializable {

	private static final long serialVersionUID = 1L;

	private static Connection instance;

	private static Logger log = Logger.getLogger(Connection.class);

	private static Map<String, EntityManagerFactory> emfMap = new HashMap<String, EntityManagerFactory>();

	private Connection() {
	}

	public static Connection getInstance() {
		if (instance == null) {
			instance = new Connection();
		}
		return instance;
	}

	private EntityManager createEntityManager(String persistenceUnitName) {
		try {
			EntityManagerFactory emf = getEntityManagerFactory(persistenceUnitName);
			return emf.createEntityManager();
		} catch (Exception e) {
			log.error("Erro no createEntityManager", e);
		}
		return null;
	}

	private static synchronized EntityManagerFactory getEntityManagerFactory(String persistenceUnitName) {
		EntityManagerFactory emf = emfMap.get(persistenceUnitName);
		if (emf == null) {
			emf = Persistence.createEntityManagerFactory(persistenceUnitName);
			emfMap.put(persistenceUnitName, emf);
		}
		return emf;
	}

	public EntityManager getEntityManager(String persistenceUnitName) {
		return createEntityManager(persistenceUnitName);
	}
}