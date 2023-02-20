package edu.wpi.capybara.objects.orm;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.Property;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

interface Persistent {
    void enablePersistence(DAOFacade orm);

    static <T> boolean compareProperties(T thisOne, T thatOne, Function<T, Object>... getters) {
        for (var getter : getters) {
            if (!Objects.equals(getter.apply(thisOne), getter.apply(thatOne))) return false;
        }
        return true;
    }
}
