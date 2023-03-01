package edu.wpi.cs3733.C23.teamC.database.orm;

import jakarta.persistence.PersistenceException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.List;
import org.hibernate.Session;

public interface DAOFacade {
  Session getSession();

  <E> void insert(E entity) throws PersistenceException;

  <E> List<E> from(Class<E> entityClass) throws PersistenceException;

  <E> void merge(E entity) throws PersistenceException;

  <E> void delete(E entity) throws PersistenceException;

  @FunctionalInterface
  interface WhereBuilder<E> {
    Predicate[] apply(CriteriaBuilder criteriaBuilder, Root<E> selectionRoot);
  }

  <E> List<E> select(Class<E> entityClass, WhereBuilder<E> whereBuilder)
      throws PersistenceException;

  <E> void refresh(E entity) throws PersistenceException;

  // merges only if the current thread is not the auto updater thread. this prevents potentially
  // stale data from being
  // written back to the database during a refresh operation.
  <E> void mergeOnlyWhenManual(E entity) throws PersistenceException;
}
