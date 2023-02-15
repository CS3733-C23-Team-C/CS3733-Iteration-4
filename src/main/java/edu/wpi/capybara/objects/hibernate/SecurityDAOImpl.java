package edu.wpi.capybara.objects.hibernate;

import java.util.HashMap;

public class SecurityDAOImpl implements SecurityDAO {
  HashMap<Integer, SecuritysubmissionEntity> securitySubs = new HashMap();

  @Override
  public HashMap<Integer, SecuritysubmissionEntity> getSecuritySubs() {
    return securitySubs;
  }

  @Override
  public SecuritysubmissionEntity getSecurity(int id) {
    return securitySubs.get(id);
  }

  @Override
  public void addSecurity(SecuritysubmissionEntity submission) {
    newDBConnect.insertNew(submission);
    this.securitySubs.put(submission.getSubmissionid(), submission);
  }

  public SecurityDAOImpl(HashMap<Integer, SecuritysubmissionEntity> securitySubs) {
    this.securitySubs = securitySubs;
  }

  @Override
  public void deleteSecurity(int id) {
    newDBConnect.delete(getSecurity(id));
    securitySubs.remove(id);
  }
}
