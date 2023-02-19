package edu.wpi.capybara.database.dao;

import edu.wpi.capybara.objects.orm.DAOFacade;
import javafx.beans.property.ReadOnlyMapProperty;
import javafx.beans.property.ReadOnlyMapWrapper;

import java.util.function.Function;

public class MapDAOBase<K, E> {
    protected final DAOFacade orm;
    protected final ReadOnlyMapWrapper<K, E> entities = new ReadOnlyMapWrapper<>();
    private final Function<E, K> keyGetter;

    protected MapDAOBase(DAOFacade orm, Class<E> entityClass, Function<E, K> keyGetter) {
        this.orm = orm;
        this.keyGetter = keyGetter;
        orm.from(entityClass).forEach(entity -> entities.put(keyGetter.apply(entity), entity));
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
    }

    public void delete(E entity) {
        orm.delete(entity);
        entities.remove(keyGetter.apply(entity), entity);
    }
}
