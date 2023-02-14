package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.database.DatabaseConnect;

import java.util.HashMap;

public class TransportationDAOImpl implements TransportationDAO{
    HashMap<Integer, TransportationsubmissionEntity> transportationSubs = new HashMap();

    @Override
    public HashMap<Integer, TransportationsubmissionEntity> getTransportationSubs() {
        return transportationSubs;
    }

    @Override
    public TransportationsubmissionEntity getTransportation(int id) {
        return transportationSubs.get(id);
    }

    @Override
    public void addTransportation(TransportationsubmissionEntity submission) {
        DatabaseConnect.insertNew(submission);
        this.transportationSubs.put(submission.getSubmissionid(), submission);
    }

    public TransportationDAOImpl(HashMap<Integer, TransportationsubmissionEntity> transportationSubs) {
        this.transportationSubs = transportationSubs;
    }

    @Override
    public void deleteTransportation(int id) {
        transportationSubs.remove(id);
        newDBConnect.delete(getTransportation(id));
    }
}
