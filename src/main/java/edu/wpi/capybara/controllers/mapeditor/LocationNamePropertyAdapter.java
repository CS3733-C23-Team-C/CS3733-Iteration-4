package edu.wpi.capybara.controllers.mapeditor;

import edu.wpi.capybara.objects.hibernate.LocationnameEntity;
import javafx.beans.property.adapter.JavaBeanStringProperty;
import javafx.beans.property.adapter.JavaBeanStringPropertyBuilder;

public class LocationNamePropertyAdapter {
  private static final JavaBeanStringPropertyBuilder longNameBuilder =
      JavaBeanStringPropertyBuilder.create().name("longname");
  private static final JavaBeanStringPropertyBuilder shortNameBuilder =
      JavaBeanStringPropertyBuilder.create().name("shortname");
  private static final JavaBeanStringPropertyBuilder locationTypeBuilder =
      JavaBeanStringPropertyBuilder.create().name("locationtype");

  private final JavaBeanStringProperty longName;
  private final JavaBeanStringProperty shortName;
  private final JavaBeanStringProperty locationType;

  public LocationNamePropertyAdapter(LocationnameEntity locationname) {
    try {
      longName = longNameBuilder.bean(locationname).build();
      shortName = shortNameBuilder.bean(locationname).build();
      locationType = locationTypeBuilder.bean(locationname).build();
    } catch (NoSuchMethodException e) {
      throw new IllegalStateException(
          "Failed to find getter/setter methods of locationname. This should not be possible.", e);
    }
  }

  public JavaBeanStringProperty longNameProperty() {
    return longName;
  }

  public JavaBeanStringProperty shortNameProperty() {
    return shortName;
  }

  public JavaBeanStringProperty locationTypeProperty() {
    return locationType;
  }
}
