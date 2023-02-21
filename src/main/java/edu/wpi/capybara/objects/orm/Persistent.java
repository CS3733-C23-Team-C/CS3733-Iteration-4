package edu.wpi.capybara.objects.orm;

import java.util.Objects;
import java.util.function.Function;

public interface Persistent {
  void enablePersistence(DAOFacade orm);

  static <T> boolean compareProperties(
      T thisOne, T thatOne, Function<T, ? super Object>... getters) {
    for (var getter : getters) {
      if (!Objects.equals(getter.apply(thisOne), getter.apply(thatOne))) return false;
    }
    return true;
  }
}
