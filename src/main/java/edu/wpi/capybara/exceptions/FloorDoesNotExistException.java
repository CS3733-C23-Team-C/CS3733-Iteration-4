package edu.wpi.capybara.exceptions;

import java.io.IOException;

public class FloorDoesNotExistException extends IOException {
  public FloorDoesNotExistException(String e) {
    super(e);
  }
}
