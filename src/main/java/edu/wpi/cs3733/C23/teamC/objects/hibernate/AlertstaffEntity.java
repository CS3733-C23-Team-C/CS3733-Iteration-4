package edu.wpi.cs3733.C23.teamC.objects.hibernate;

import edu.wpi.cs3733.C23.teamC.Main;
import edu.wpi.cs3733.C23.teamC.objects.orm.DAOFacade;
import edu.wpi.cs3733.C23.teamC.objects.orm.Persistent;
import jakarta.persistence.*;
import java.util.Objects;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "alertstaff", schema = "cdb", catalog = "teamcdb")
public class AlertstaffEntity implements Persistent {

  private final SimpleIntegerProperty id = new SimpleIntegerProperty();

  private final SimpleObjectProperty<StaffEntity> staff = new SimpleObjectProperty<StaffEntity>();

  private final SimpleObjectProperty<AlertEntity> alert = new SimpleObjectProperty<AlertEntity>();

  private final SimpleBooleanProperty seen = new SimpleBooleanProperty();

  public AlertstaffEntity() {}

  public AlertstaffEntity(int id, StaffEntity staff, AlertEntity alert, boolean seen) {
    setId(id);
    setStaff(staff);
    setAlert(alert);
    setSeen(seen);
  }

  @Override
  public void enablePersistence(DAOFacade orm) {
    final InvalidationListener listener =
        evt -> {
          if (Thread.currentThread() != Main.getUpdaterThread()) orm.merge(this);
        };
    id.addListener(listener);
    staff.addListener(listener);
    alert.addListener(listener);
    seen.addListener(listener);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) return false;
    else if (obj == this) return true;
    else if (obj instanceof AlertstaffEntity that) {
      return Persistent.compareProperties(
          this,
          that,
          AlertstaffEntity::getId,
          AlertstaffEntity::getStaff,
          AlertstaffEntity::getAlert,
          AlertstaffEntity::getSeen);
    } else return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getStaff(), getAlert(), getSeen());
  }

  @Id
  @Column(name = "id")
  public int getId() {
    return id.get();
  }

  public SimpleIntegerProperty idProperty() {
    return id;
  }

  public void setId(int id) {
    this.id.set(id);
  }

  @ManyToOne
  @JoinColumn(name = "staff", insertable = false)
  @Cascade(org.hibernate.annotations.CascadeType.REFRESH)
  public StaffEntity getStaff() {
    return staff.get();
  }

  public SimpleObjectProperty<StaffEntity> staffProperty() {
    return staff;
  }

  public void setStaff(StaffEntity staff) {
    this.staff.set(staff);
  }

  @ManyToOne
  @JoinColumn(name = "alert", insertable = false)
  @Cascade(org.hibernate.annotations.CascadeType.REFRESH)
  public AlertEntity getAlert() {
    return alert.get();
  }

  public SimpleObjectProperty<AlertEntity> alertProperty() {
    return alert;
  }

  public void setAlert(AlertEntity alert) {
    this.alert.set(alert);
  }

  @Column(name = "seen")
  public boolean getSeen() {
    return seen.get();
  }

  public SimpleBooleanProperty seenProperty() {
    return seen;
  }

  public void setSeen(boolean seen) {
    this.seen.set(seen);
  }
}
