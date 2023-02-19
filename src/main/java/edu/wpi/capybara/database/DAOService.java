package edu.wpi.capybara.database;

import edu.wpi.capybara.objects.orm.DAOFacade;
import jakarta.persistence.PersistenceException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static edu.wpi.capybara.Main.db;

@Slf4j
public class DAOService implements DAOFacade {
    @Override
    public <E> void insert(E entity) throws PersistenceException {
        try (final var session = db.getSession()) {
            final var tx = session.beginTransaction();
            try {
                session.persist(entity);
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
        try (final var session = db.getSession()) {
            final var tx = session.beginTransaction();
            try {
                final var ret = session.createQuery("FROM " + entityClass.getSimpleName(), entityClass).list();
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
        try (final var session = db.getSession()) {
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
        try (final var session = db.getSession()) {
            final var tx = session.beginTransaction();
            try {
                session.remove(entity);
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
}
