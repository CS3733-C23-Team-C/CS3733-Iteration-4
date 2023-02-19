package edu.wpi.capybara.database.dao;

import edu.wpi.capybara.objects.orm.DAOFacade;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;

class ListDAOBase<E> {
    protected final DAOFacade orm;
    protected final ReadOnlyListWrapper<E> entities = new ReadOnlyListWrapper<>();

    protected ListDAOBase(DAOFacade orm, Class<E> entityClass) {
        this.orm = orm;
        entities.addAll(orm.from(entityClass));
    }

    public ReadOnlyListProperty<E> getAll() {
        return entities.getReadOnlyProperty();
    }

    public void add(E entity) {
        orm.insert(entity);
        entities.add(entity);
    }

    public void delete(E entity) {
        orm.delete(entity);
        entities.remove(entity);
    }
}
