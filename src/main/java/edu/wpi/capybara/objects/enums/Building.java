package edu.wpi.capybara.objects.enums;

public enum Building {
  FRANCIS,
  TOWER,
  SHAPIRO,
  ERROR;

  public static Building strToBuilding(String input) {
    if (input.equals("45 Francis")) return Building.FRANCIS;
    else if (input.equals("Tower")) return Building.TOWER;
    else if (input.equals("Shapiro")) return Building.SHAPIRO;
    else return Building.ERROR;
  }
}
