package edu.wpi.capybara.controllers;

import edu.wpi.capybara.objects.submissions.SecuritySubmitter;
import io.github.palexdev.materialfx.controls.*;
import java.util.*;
import javax.swing.*;

public class SecurityController extends ServiceRequestAbs {

  public void setRequestSpecific() {
    submission = new SecuritySubmitter();
    requestSpecific.getItems().addAll("Police Department", "Fire Department", "Health Department");

  }
}
