package edu.wpi.capybara.objects.enums;

public enum Floor {
  L1,
  L2,
  ERROR;

  public static Floor strToFloor(String input) {
    if (input.equals("L1")) return Floor.L1;
    else if (input.equals("L2")) return Floor.L2;
    else return Floor.ERROR;
  }
}
