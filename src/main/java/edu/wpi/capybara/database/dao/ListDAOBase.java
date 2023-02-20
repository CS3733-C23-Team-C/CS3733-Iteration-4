package edu.wpi.capybara.database.dao;

import edu.wpi.capybara.objects.orm.DAOFacade;
import edu.wpi.capybara.objects.orm.Persistent;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.collections.FXCollections;

class ListDAOBase<E extends Persistent> {
  protected final DAOFacade orm;
  protected final ReadOnlyListWrapper<E> entities =
      new ReadOnlyListWrapper<>(FXCollections.observableArrayList());

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
    entity.enablePersistence(orm);
  }

  public void delete(E entity) {
    orm.delete(entity);
    entities.remove(entity);
  }
}
