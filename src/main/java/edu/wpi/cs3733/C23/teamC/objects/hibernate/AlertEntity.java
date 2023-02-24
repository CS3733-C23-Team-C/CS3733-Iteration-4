package edu.wpi.cs3733.C23.teamC.objects.hibernate;

import edu.wpi.cs3733.C23.teamC.Main;
import edu.wpi.cs3733.C23.teamC.objects.orm.DAOFacade;
import edu.wpi.cs3733.C23.teamC.objects.orm.Persistent;
import jakarta.persistence.*;
import java.util.*;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import org.hibernate.collection.spi.PersistentSet;

@Entity
@Table(name = "alerts", schema = "cdb", catalog = "teamcdb")
public class AlertEntity implements Persistent {
  // private final SimpleObjectProperty<UUID> messageID = new SimpleObjectProperty<>();

  private Set<StaffEntity> staff = new PersistentSet<>();
  private final SimpleIntegerProperty alertid = new SimpleIntegerProperty();
  private final SimpleObjectProperty<Date> date = new SimpleObjectProperty<>();
  private final SimpleStringProperty message = new SimpleStringProperty();

  public AlertEntity() {}

  public AlertEntity(int alertid, Date date, String message) {
    setAlertid(alertid);
    setDate(date);
    setMessage(message);
  }

  @Override
  public void enablePersistence(DAOFacade orm) {
    final InvalidationListener listener =
        evt -> {
          if (Thread.currentThread() != Main.getUpdaterThread()) orm.merge(this);
        };
    alertid.addListener(listener);
    date.addListener(listener);
    message.addListener(listener);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) return false;
    else if (obj == this) return true;
    else if (obj instanceof AlertEntity that) {
      return Persistent.compareProperties(
          this, that, AlertEntity::getAlertid, AlertEntity::getDate, AlertEntity::getMessage);
    } else return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getAlertid(), getDate(), getAlertid());
  }

  @Id
  @Column(name = "alertid")
  public int getAlertid() {
    return alertid.get();
  }

  public SimpleIntegerProperty alertidProperty() {
    return alertid;
  }

  public void setAlertid(int alertid) {
    this.alertid.set(alertid);
  }

  @Column(name = "alertdate")
  public Date getDate() {
    return date.get();
  }

  public SimpleObjectProperty<Date> dateProperty() {
    return date;
  }

  public void setDate(Date date) {
    this.date.set(date);
  }

  @Column(name = "message")
  public String getMessage() {
    return message.get();
  }

  public SimpleStringProperty messageProperty() {
    return message;
  }

  public void setMessage(String message) {
    this.message.set(message);
  }

  @ManyToMany(mappedBy = "alerts")
  public Set<StaffEntity> getStaff() {
    return this.staff;
  }

  public void setStaff(Set<StaffEntity> staff) {
    this.staff = staff;
  }
}
