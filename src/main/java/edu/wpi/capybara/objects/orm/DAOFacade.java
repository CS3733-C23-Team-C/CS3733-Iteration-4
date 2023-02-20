package edu.wpi.capybara.objects.orm;

import java.util.List;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;

public interface DAOFacade {
    Session getSession();
            <E>

    void insert(E entity) throws PersistenceException;
    <E> List<E> from(Class<E> entityClass) throws PersistenceException;

    @FunctionalInterface
    interface WhereBuilder<E> {
        Predicate[] apply(CriteriaBuilder criteriaBuilder, Root<E> selectionRoot);
    }

    <E> List<E> select(Class<E> entityClass, WhereBuilder<E> whereBuilder) throws PersistenceException;
    <E> void merge(E entity) throws PersistenceException;
    <E> void delete(E entity) throws PersistenceException;
}
