package edu.wpi.cs3733.C23.teamC.objects;

import lombok.Getter;
import lombok.Setter;

public class SelectedMessage {
  @Getter @Setter private String type;
  @Setter @Getter private int selectedID;

  public SelectedMessage(String type, int ID) {
    this.type = type;
    this.selectedID = ID;
  }
}
