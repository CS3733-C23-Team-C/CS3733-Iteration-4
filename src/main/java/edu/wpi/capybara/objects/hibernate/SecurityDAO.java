package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.objects.orm.SecuritysubmissionEntity;
import java.util.HashMap;

public interface SecurityDAO {

  HashMap<Integer, SecuritysubmissionEntity> getSecuritySubs();

  SecuritysubmissionEntity getSecurity(int id);

  void addSecurity(SecuritysubmissionEntity submission);

  void deleteSecurity(int id);
}
