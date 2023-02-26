package edu.wpi.cs3733.C23.teamC.database.dao;

import edu.wpi.cs3733.C23.teamC.objects.orm.DAOFacade;
import edu.wpi.cs3733.C23.teamC.objects.orm.Persistent;
import java.util.function.Function;
import javafx.beans.property.ReadOnlyMapProperty;
import javafx.beans.property.ReadOnlyMapWrapper;
import javafx.collections.FXCollections;

public class MapDAOBase<K, E extends Persistent> {
  protected final DAOFacade orm;
  protected final ReadOnlyMapWrapper<K, E> entities =
      new ReadOnlyMapWrapper<>(FXCollections.observableHashMap());
  private final Function<E, K> keyGetter;

  protected MapDAOBase(DAOFacade orm, Class<E> entityClass, Function<E, K> keyGetter) {
    this.orm = orm;
    this.keyGetter = keyGetter;
    orm.from(entityClass)
        .forEach(
            entity -> {
              entity.enablePersistence(orm);
              entities.put(keyGetter.apply(entity), entity);
            });
  }

  public ReadOnlyMapProperty<K, E> getAll() {
    return entities.getReadOnlyProperty();
  }

  public E get(K key) {
    return entities.get(key);
  }

  public void add(E entity) {
    orm.insert(entity);
    entities.put(keyGetter.apply(entity), entity);
    entity.enablePersistence(orm);
  }

  public void delete(E entity) {
    entities.remove(keyGetter.apply(entity), entity);
    orm.delete(entity);
    // todo should we somehow disable persistence?
  }

  public void update() {
    getAll().forEach((k, e) -> orm.refresh(e));
  }
}
