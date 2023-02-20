package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.objects.orm.SecuritySubmission;

import java.util.HashMap;

public interface SecurityDAO {

  HashMap<Integer, SecuritySubmission> getSecuritySubs();

  SecuritySubmission getSecurity(int id);

  void addSecurity(SecuritySubmission submission);

  void deleteSecurity(int id);
}
