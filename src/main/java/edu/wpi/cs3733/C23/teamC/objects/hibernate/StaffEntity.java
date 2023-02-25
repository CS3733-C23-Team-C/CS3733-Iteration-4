package edu.wpi.cs3733.C23.teamC.objects.hibernate;

import edu.wpi.cs3733.C23.teamC.database.CSVExportable;
import edu.wpi.cs3733.C23.teamC.database.CSVImporter;
import edu.wpi.cs3733.C23.teamC.objects.orm.DAOFacade;
import edu.wpi.cs3733.C23.teamC.objects.orm.Persistent;
import jakarta.persistence.*;
import java.util.Objects;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import org.hibernate.collection.spi.PersistentSet;

@Entity
@Table(name = "staff", schema = "cdb", catalog = "teamcdb")
public class StaffEntity implements Persistent, CSVExportable {

  private PersistentSet<AlertstaffEntity> alertstaff = new PersistentSet<AlertstaffEntity>();
  private final SimpleStringProperty staffid = new SimpleStringProperty();
  private final SimpleStringProperty firstname = new SimpleStringProperty();
  private final SimpleStringProperty lastname = new SimpleStringProperty();
  private final SimpleStringProperty password = new SimpleStringProperty();
  private final SimpleStringProperty role = new SimpleStringProperty();
  private final SimpleStringProperty notes = new SimpleStringProperty();

  public StaffEntity() {}

  public StaffEntity(
      String staffid, String firstname, String lastname, String role, String password) {
    setStaffid(staffid);
    setFirstname(firstname);
    setLastname(lastname);
    setRole(role);
    setPassword(password);
  }

  @Override
  public void enablePersistence(DAOFacade orm) {
    final InvalidationListener listener = evt -> orm.merge(this);
    staffid.addListener(listener);
    firstname.addListener(listener);
    lastname.addListener(listener);
    password.addListener(listener);
    role.addListener(listener);
    notes.addListener(listener);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) return false;
    else if (obj == this) return true;
    else if (obj instanceof StaffEntity that) {
      return Persistent.compareProperties(
          this,
          that,
          StaffEntity::getStaffid,
          StaffEntity::getFirstname,
          StaffEntity::getLastname,
          StaffEntity::getPassword,
          StaffEntity::getRole,
          StaffEntity::getNotes);
    } else return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        getStaffid(), getFirstname(), getLastname(), getPassword(), getRole(), getNotes());
  }

  @Id
  @Column(name = "staffid")
  public String getStaffid() {
    return staffid.get();
  }

  public SimpleStringProperty staffidProperty() {
    return staffid;
  }

  public void setStaffid(String staffid) {
    this.staffid.set(staffid);
  }

  @Column(name = "firstname")
  public String getFirstname() {
    return firstname.get();
  }

  public SimpleStringProperty firstnameProperty() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname.set(firstname);
  }

  @Column(name = "lastname")
  public String getLastname() {
    return lastname.get();
  }

  public SimpleStringProperty lastnameProperty() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname.set(lastname);
  }

  @Column(name = "password")
  public String getPassword() {
    return password.get();
  }

  public SimpleStringProperty passwordProperty() {
    return password;
  }

  public void setPassword(String password) {
    this.password.set(password);
  }

  @Column(name = "role")
  public String getRole() {
    return role.get();
  }

  public SimpleStringProperty roleProperty() {
    return role;
  }

  public void setRole(String role) {
    this.role.set(role);
  }

  @Column(name = "notes")
  public String getNotes() {
    return notes.get();
  }

  public SimpleStringProperty notesProperty() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes.set(notes);
  }

  @OneToMany(cascade = CascadeType.ALL)
  public PersistentSet<AlertstaffEntity> getAlertstaff() {
    return this.alertstaff;
  }

  //  SimpleSetProperty<AlertstaffEntity> alertsProperty() {
  //    return alertstaff;
  //  }

  public void setAlertstaff(PersistentSet<AlertstaffEntity> alerts) {
    this.alertstaff = alerts;
  }

  @Override
  public String[] toCSV() {
    return new String[] {
      getStaffid(), getFirstname(), getLastname(), getRole(), getPassword(), getNotes()
    };
  }

  public static class Importer implements CSVImporter<StaffEntity> {
    @Override
    public StaffEntity fromCSV(String[] csv) {

      String staffid = csv[0];
      String firstname = csv[1];
      String lastname = csv[2];
      String role = csv[3];
      String password = csv[4];
      // String notes = csv[5];

      return new StaffEntity(staffid, firstname, lastname, role, password);
    }
  }
}
