package edu.wpi.capybara.objects;

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
}
