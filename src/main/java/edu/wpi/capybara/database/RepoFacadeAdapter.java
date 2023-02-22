package edu.wpi.capybara.database;

import edu.wpi.capybara.objects.hibernate.*;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;

public class RepoFacadeAdapter implements RepoFacade {
  private final RepoFacade2 repo;

  public RepoFacadeAdapter(RepoFacade2 repo) {
    this.repo = repo;
  }

  @Override
  public Map<Integer, AudiosubmissionEntity> getAudioSubs() {
    return repo.getAudioSubs();
  }

  @Override
  public Map<Integer, CleaningsubmissionEntity> getCleaningSubs() {
    return repo.getCleaningSubs();
  }

  @Override
  public Map<Integer, ComputersubmissionEntity> getComputerSubs() {
    return repo.getComputerSubs();
  }

  @Override
  public Map<Integer, SecuritysubmissionEntity> getSecuritySubs() {
    return repo.getSecuritySubs();
  }

  @Override
  public Map<Integer, TransportationsubmissionEntity> getTransportationSubs() {
    return repo.getTransportationSubs();
  }

  @Override
  public Map<String, LocationnameEntity> getLocationnames() {
    return repo.getLocationNames();
  }

  @Override
  public Map<String, NodeEntity> getNodes() {
    return repo.getNodes();
  }

  @Override
  public Map<String, StaffEntity> getStaff() {
    return repo.getStaff();
  }

  @Override
  public List<EdgeEntity> getEdges() {
    return repo.getEdges();
  }

  @Override
  public List<MoveEntity> getMoves() {
    return repo.getMoves();
  }

  @Override
  public Map<Integer, MessagesEntity> getMessages() {
    return repo.getMessages();
  }

  @Override
  public Map<Integer, MessagesEntity> getMessages(String staff) {
    return repo.getMessages(staff);
  }

  @Override
  public Map<Integer, MessagesEntity> getMessages(String staff, int lastid) {
    return repo.getMessages(staff, lastid);
  }

  @Override
  public void addAudio(AudiosubmissionEntity submission) {
    repo.addAudio(submission);
  }

  @Override
  public void addCleaning(CleaningsubmissionEntity submission) {
    repo.addCleaning(submission);
  }

  @Override
  public void addComputer(ComputersubmissionEntity submission) {
    repo.addComputer(submission);
  }

  @Override
  public void addSecurity(SecuritysubmissionEntity submission) {
    repo.addSecurity(submission);
  }

  @Override
  public void addTransportation(TransportationsubmissionEntity submission) {
    repo.addTransportation(submission);
  }

  @Override
  public void addLocationname(LocationnameEntity submission) {
    repo.addLocationName(submission);
  }

  @Override
  public void addNode(NodeEntity submission) {
    repo.addNode(submission);
  }

  @Override
  public void addStaff(StaffEntity submission) {
    repo.addStaff(submission);
  }

  @Override
  public void addEdge(EdgeEntity submission) {
    repo.addEdge(submission);
  }

  @Override
  public boolean addMove(MoveEntity submission) {
    return repo.addMove(submission);
  }

  @Override
  public void addMessage(MessagesEntity message) {
    repo.addMessage(message);
  }

  @Override
  public AudiosubmissionEntity getAudio(int id) {
    return repo.getAudio(id);
  }

  @Override
  public CleaningsubmissionEntity getCleaning(int id) {
    return repo.getCleaning(id);
  }

  @Override
  public ComputersubmissionEntity getComputer(int id) {
    return repo.getComputer(id);
  }

  @Override
  public SecuritysubmissionEntity getSecurity(int id) {
    return repo.getSecurity(id);
  }

  @Override
  public TransportationsubmissionEntity getTransportation(int id) {
    return repo.getTransportation(id);
  }

  @Override
  public LocationnameEntity getLocationname(String longname) {
    return repo.getLocationname(longname);
  }

  @Override
  public NodeEntity getNode(String nodeid) {
    return repo.getNode(nodeid);
  }

  @Override
  public StaffEntity getStaff(String staffid) {
    return repo.getStaff(staffid);
  }

  @Override
  public MessagesEntity getMessage(int id) {
    return repo.getMessage(id);
  }

  @Override
  public void deleteAudio(int id) {
    repo.deleteAudio(id);
  }

  @Override
  public void deleteCleaning(int id) {
    repo.deleteCleaning(id);
  }

  @Override
  public void deleteComputer(int id) {
    repo.deleteComputer(id);
  }

  @Override
  public void deleteSecurity(int id) {
    repo.deleteSecurity(id);
  }

  @Override
  public void deleteTransportation(int id) {
    repo.deleteTransportation(id);
  }

  @Override
  public void deleteEdge(EdgeEntity edge) {
    repo.deleteEdge(edge);
  }

  @Override
  public void deleteLocationname(String longname) {
    repo.deleteLocationName(longname);
  }

  @Override
  public void deleteMove(MoveEntity move) {
    repo.deleteMove(move);
  }

  @Override
  public void deleteNode(String nodeid) {
    repo.deleteNode(nodeid);
  }

  @Override
  public void deleteStaff(String staffid) {
    repo.deleteStaff(staffid);
  }

  @Override
  public void deleteMessage(int messageid) {
    repo.deleteMessage(messageid);
  }

  @Override
  public StaffEntity getStaff(String Staffid, String password) {
    return repo.getStaff(Staffid, password);
  }

  public StaffEntity getStaff2(String firstName, String lastName) {
    return repo.getStaff2(firstName, lastName);
  }

  @Override
  public Session getSession() {
    return repo.getSession();
  }

  @Override
  public int newID() {
    return repo.newID();
  }

  @Override
  public void importAll() {
    repo.importAll();
  }

  @Override
  public int generateMessageID() {
    return repo.generateMessageID();
  }

  @Override
  public void threadRefresh(int delay) {
    repo.threadRefresh(delay);
  }

  @Override
  public void refreshMessages() {
    repo.refreshMessages();
  }
}
