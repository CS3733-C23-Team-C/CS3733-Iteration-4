package edu.wpi.cs3733.C23.teamC.exceptions;

import java.io.IOException;

public class FloorDoesNotExistException extends IOException {
  public FloorDoesNotExistException(String e) {
    super(e);
  }

  public FloorDoesNotExistException(Exception e) {
    super(e);
  }
}
