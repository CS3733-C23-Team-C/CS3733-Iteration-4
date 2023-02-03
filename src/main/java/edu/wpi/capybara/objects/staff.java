package edu.wpi.capybara.objects;

import lombok.Getter;
import lombok.Setter;

public class staff {
  @Setter @Getter private String firstName;
  @Setter @Getter private String lastName;
  @Setter @Getter private String staffID;

  public staff(String firstName, String lastName, String staffID) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.staffID = staffID;
  }
}
