package edu.wpi.capybara.controllers;

import edu.wpi.capybara.objects.submissions.SecuritySubmitter;
import io.github.palexdev.materialfx.controls.*;
import java.util.*;
import javax.swing.*;

public class EmployeeEdit extends ServiceRequestAbs {

    public void setRequestSpecific() {
        submission = new EmployeeEditSubmitter();
        requestSpecific.getItems().addAll("Employee ID", "Name", "Ocupation", "Date of change", "Additional Notes");
    }
}
