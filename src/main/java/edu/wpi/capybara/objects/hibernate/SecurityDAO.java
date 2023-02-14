package edu.wpi.capybara.objects.hibernate;

import java.util.HashMap;

public interface SecurityDAO {

    HashMap<Integer, SecuritysubmissionEntity> getSecuritySubs();
    SecuritysubmissionEntity getSecurity(int id);
    void addSecurity(SecuritysubmissionEntity submission);
    void deleteSecurity(int id);
}
