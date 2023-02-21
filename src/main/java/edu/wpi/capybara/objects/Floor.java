package edu.wpi.capybara.objects;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/** Utility enum for representing the floors of the hospital. */
public enum Floor {
  F1,
  F2,
  F3,
  L1,
  L2;

  @Override
  public String toString() {
    return switch (this) {
      case F1 -> "1";
      case F2 -> "2";
      case F3 -> "3";
      case L1 -> "L1";
      case L2 -> "L2";
    };
  }

  public static Floor fromString(String string) {
    return switch (string) {
      case "1" -> F1;
      case "2" -> F2;
      case "3" -> F3;
      case "L1" -> L1;
      case "L2" -> L2;
      default -> null;
    };
  }

  @Converter
  public static class SQLConverter implements AttributeConverter<Floor, String> {
    @Override
    public String convertToDatabaseColumn(Floor attribute) {
      return attribute.toString();
    }

    @Override
    public Floor convertToEntityAttribute(String dbData) {
      return Floor.fromString(dbData);
    }
  }

  public static class StringConverter extends javafx.util.StringConverter<Floor> {
    @Override
    public String toString(Floor object) {
      return object.toString();
    }

    @Override
    public Floor fromString(String string) {
      return Floor.fromString(string);
    }
  }
}
