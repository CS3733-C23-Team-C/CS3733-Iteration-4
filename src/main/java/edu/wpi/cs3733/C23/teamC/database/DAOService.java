package edu.wpi.cs3733.C23.teamC.database;

import edu.wpi.cs3733.C23.teamC.Main;
import edu.wpi.cs3733.C23.teamC.database.orm.DAOFacade;
import jakarta.persistence.PersistenceException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@Slf4j
public class DAOService implements DAOFacade {
  private static SessionFactory factory;

  public DAOService() {
    log.info("Initializing DAO service.");
    try {
      factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
      log.info("Successfully initialized.");
    } catch (Throwable ex) {
      System.err.println("Failed to create sessionFactory object." + ex);
      throw new ExceptionInInitializerError(ex);
    }
  }

  @Override
  public Session getSession() {
    return factory.openSession();
  }

  @Override
  public <E> void insert(E entity) throws PersistenceException {
    log.info("Executing INSERT operation for entity of type " + entity.getClass().getName());
    try (final var session = getSession()) {
      final var tx = session.beginTransaction();
      try {
        session.merge(entity);
        tx.commit();
      } catch (PersistenceException e) {
        // rollback the database, log the exception, and throw it back to the caller.
        // this is so the caller can decide how it wants to handle the failure.
        tx.rollback();
        log.error("Unable to insert entity of type " + entity.getClass().getName(), e);
        throw e;
      }
    }
  }

  @Override
  public <E> List<E> from(Class<E> entityClass) throws PersistenceException {
    log.info("Executing FROM operation for entity type " + entityClass.getName());
    try (final var session = getSession()) {
      final var tx = session.beginTransaction();
      try {
        final var ret =
            session.createQuery("FROM " + entityClass.getSimpleName(), entityClass).list();
        tx.commit();
        return ret;
      } catch (PersistenceException e) {
        tx.rollback();
        log.error("Unable to get entities of type " + entityClass.getName(), e);
        throw e;
      }
    }
  }

  @Override
  public <E> void merge(E entity) throws PersistenceException {
    log.info("Executing MERGE operation for entity of type " + entity.getClass().getName());
    try (final var session = getSession()) {
      final var tx = session.beginTransaction();
      try {
        session.merge(entity);
        tx.commit();
      } catch (PersistenceException e) {
        // rollback the database, log the exception, and throw it back to the caller.
        // this is so the caller can decide how it wants to handle the failure.
        tx.rollback();
        log.error("Unable to merge entity of type " + entity.getClass().getName(), e);
        throw e;
      }
    }
  }

  @Override
  public <E> void delete(E entity) throws PersistenceException {
    log.info("Executing DELETE operation for entity of type " + entity.getClass().getName());
    try (final var session = getSession()) {
      final var tx = session.beginTransaction();
      try {
        // session.merge(entity);
        session.remove(entity);
        tx.commit();
      } catch (ObjectNotFoundException e) {
        // well, the object's gone regardless. log a warning but otherwise ignore it.
        log.debug("Attempted to delete an object, but it was already deleted.");
      } catch (PersistenceException e) {
        // rollback the database, log the exception, and throw it back to the caller.
        // this is so the caller can decide how it wants to handle the failure.
        tx.rollback();
        log.error("Unable to delete entity of type " + entity.getClass().getName(), e);
        throw e;
      }
    }
  }

  @Override
  public <E> List<E> select(Class<E> entityClass, WhereBuilder<E> whereBuilder)
      throws PersistenceException {
    log.info("Executing SELECT operation for entity type " + entityClass.getName());
    try (final var session = getSession()) {
      final var tx = session.beginTransaction();
      try {
        final var builder = session.getCriteriaBuilder();
        final var queryTemplate = builder.createQuery(entityClass);
        final var from = queryTemplate.from(entityClass);

        queryTemplate.select(from).where(whereBuilder.apply(builder, from));

        final var typedQuery = session.createQuery(queryTemplate);
        final var result = typedQuery.getResultList();
        tx.commit();
        return result;
      } catch (PersistenceException e) {
        tx.rollback();
        log.error("Unable to select entities of type " + entityClass.getName(), e);
        throw e;
      }
    }
  }

  @Override
  public <E> void refresh(E entity) throws PersistenceException {
    log.debug("Executing REFRESH operation for entity of type " + entity.getClass().getName());
    try (final var session = getSession()) {
      final var tx = session.beginTransaction();
      try {
        session.refresh(entity);
        tx.commit();
      } catch (PersistenceException e) {
        // rollback the database, log the exception, and throw it back to the caller.
        // this is so the caller can decide how it wants to handle the failure.
        tx.rollback();
        log.error("Unable to insert entity of type " + entity.getClass().getName(), e);
        throw e;
      }
    }
  }

  @Override
  public <E> void mergeOnlyWhenManual(E entity) throws PersistenceException {
    if (Thread.currentThread().equals(Main.getUpdaterThread())) return;
    merge(entity);
  }
}
