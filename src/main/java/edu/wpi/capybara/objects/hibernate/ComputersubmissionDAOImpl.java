package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.database.DatabaseConnect;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.HashMap;

public class ComputersubmissionDAOImpl implements ComputersubmissionDAO{
    HashMap<Integer, ComputersubmissionEntity> computerSubs = new HashMap();

    @Override
    public HashMap<Integer, ComputersubmissionEntity> getComputerSubs() {
        return computerSubs;
    }

    @Override
    public ComputersubmissionEntity getComputer(int id) {
        return computerSubs.get(id);
    }

    @Override
    public void addComputer(ComputersubmissionEntity submission) {
        newDBConnect.insertNew(submission);
        this.computerSubs.put(submission.getSubmissionid(), submission);
    }

    @Override
    public void deleteComputer(int id) {
        computerSubs.remove(id);
        newDBConnect.delete(getComputer(id));
    }
    public ComputersubmissionDAOImpl(HashMap<Integer, ComputersubmissionEntity> computerSubs) {
        this.computerSubs = computerSubs;
    }
}
