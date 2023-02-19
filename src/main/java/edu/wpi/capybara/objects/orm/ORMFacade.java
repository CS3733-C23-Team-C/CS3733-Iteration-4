package edu.wpi.capybara.objects.orm;

import java.util.List;
import jakarta.persistence.PersistenceException;

public interface ORMFacade {
    <E> List<E> from(Class<E> entityClass) throws PersistenceException;
    <E> void merge(E entity) throws PersistenceException;
    <E> void delete(E entity) throws PersistenceException;
}
