package edu.wpi.capybara.objects.orm;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;

@Entity
@Table(name = "staff", schema = "cdb", catalog = "teamcdb")
public class Staff implements Persistent {
  private final SimpleStringProperty staffID = new SimpleStringProperty();
  private final SimpleStringProperty firstName = new SimpleStringProperty();
  private final SimpleStringProperty lastName = new SimpleStringProperty();
  private final SimpleStringProperty password = new SimpleStringProperty();
  private final SimpleStringProperty role = new SimpleStringProperty();
  private final SimpleStringProperty notes = new SimpleStringProperty();

  public Staff() {}

  public Staff(String staffid, String firstname, String lastname, String role, String password) {
    setStaffID(staffid);
    setFirstName(firstname);
    setLastName(lastname);
    setRole(role);
    setPassword(password);
  }

  @Override
  public void enablePersistence(DAOFacade orm) {
    final InvalidationListener listener = evt -> orm.merge(this);
    staffID.addListener(listener);
    firstName.addListener(listener);
    lastName.addListener(listener);
    password.addListener(listener);
    role.addListener(listener);
    notes.addListener(listener);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) return false;
    else if (obj == this) return true;
    else if (obj instanceof Staff that) {
      return Persistent.compareProperties(
          this,
          that,
          Staff::getStaffID,
          Staff::getFirstName,
          Staff::getLastName,
          Staff::getPassword,
          Staff::getRole,
          Staff::getNotes);
    } else return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        getStaffID(), getFirstName(), getLastName(), getPassword(), getRole(), getNotes());
  }

  @Id
  @Column(name = "staffid")
  public String getStaffID() {
    return staffID.get();
  }

  public SimpleStringProperty staffIDProperty() {
    return staffID;
  }

  public void setStaffID(String staffID) {
    this.staffID.set(staffID);
  }

  @Column(name = "firstname")
  public String getFirstName() {
    return firstName.get();
  }

  public SimpleStringProperty firstNameProperty() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName.set(firstName);
  }

  @Column(name = "lastname")
  public String getLastName() {
    return lastName.get();
  }

  public SimpleStringProperty lastNameProperty() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName.set(lastName);
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
}
