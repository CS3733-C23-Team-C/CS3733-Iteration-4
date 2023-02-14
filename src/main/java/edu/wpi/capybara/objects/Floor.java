package edu.wpi.capybara.objects;

public enum Floor {
  F1,
  F2,
  F3,
  L1,
  L2;

  @Override
  public String toString() {
    return switch (this) {
      case F1 -> "F1";
      case F2 -> "F2";
      case F3 -> "F3";
      case L1 -> "L1";
      case L2 -> "L2";
    };
  }

  public static Floor fromString(String string) {
    return switch (string) {
      case "F1" -> F1;
      case "F2" -> F2;
      case "F3" -> F3;
      case "L1" -> L1;
      case "L2" -> L2;
      default -> null;
    };
  }
}
