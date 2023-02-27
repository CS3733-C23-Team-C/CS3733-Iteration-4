package edu.wpi.cs3733.C23.teamC.objects.hibernate;

import edu.wpi.cs3733.C23.teamC.database.CSVExportable;
import edu.wpi.cs3733.C23.teamC.database.CSVImporter;
import edu.wpi.cs3733.C23.teamC.objects.orm.DAOFacade;
import edu.wpi.cs3733.C23.teamC.objects.orm.Persistent;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

@Entity
@Table(name = "staff2", schema = "cdb", catalog = "teamcdb")
public class Staff2Entity implements Persistent, CSVExportable {
  private final SimpleStringProperty staffid = new SimpleStringProperty();
  private final SimpleStringProperty firstname = new SimpleStringProperty();
  private final SimpleStringProperty lastname = new SimpleStringProperty();
  private final SimpleStringProperty password = new SimpleStringProperty();
  private final SimpleStringProperty role = new SimpleStringProperty();
  private final SimpleStringProperty notes = new SimpleStringProperty();
  private final SimpleIntegerProperty picnum = new SimpleIntegerProperty();

  public Staff2Entity() {}

  public Staff2Entity(
      String staffid, String firstname, String lastname, String role, String password) {
    setStaffid(staffid);
    setFirstname(firstname);
    setLastname(lastname);
    setRole(role);
    setPassword(password);
  }

  private InvalidationListener listener;

  @Override
  public void enablePersistence(DAOFacade orm) {
    listener = evt -> orm.mergeOnlyWhenManual(this);
    staffid.addListener(listener);
    firstname.addListener(listener);
    lastname.addListener(listener);
    password.addListener(listener);
    role.addListener(listener);
    notes.addListener(listener);
    picnum.addListener(listener);
  }

  @Override
  public void disablePersistence() {
    if (listener != null) {
      staffid.removeListener(listener);
      firstname.removeListener(listener);
      lastname.removeListener(listener);
      password.removeListener(listener);
      role.removeListener(listener);
      notes.removeListener(listener);
      picnum.removeListener(listener);
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) return false;
    else if (obj == this) return true;
    else if (obj instanceof Staff2Entity that) {
      return Persistent.compareProperties(
          this,
          that,
          Staff2Entity::getStaffid,
          Staff2Entity::getFirstname,
          Staff2Entity::getLastname,
          Staff2Entity::getPassword,
          Staff2Entity::getRole,
          Staff2Entity::getNotes,
          Staff2Entity::getPicnum);
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

  @Column(name = "picnum")
  public int getPicnum() {
    return picnum.get();
  }

  public SimpleIntegerProperty picnumProperty() {
    return picnum;
  }

  public void setPicnum(int picnum) {
    this.picnum.set(picnum);
  }

  @Override
  public String[] toCSV() {
    return new String[] {
      getStaffid(), getFirstname(), getLastname(), getRole(), getPassword(), getNotes()
    };
  }

  public static class Importer implements CSVImporter<Staff2Entity> {
    @Override
    public Staff2Entity fromCSV(String[] csv) {

      String staffid = csv[0];
      String firstname = csv[1];
      String lastname = csv[2];
      String role = csv[3];
      String password = csv[4];
      // String notes = csv[5];

      return new Staff2Entity(staffid, firstname, lastname, role, password);
    }
  }
}
