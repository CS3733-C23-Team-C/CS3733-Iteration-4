package edu.wpi.cs3733.C23.teamC.objects.hibernate;

import edu.wpi.cs3733.C23.teamC.Main;
import edu.wpi.cs3733.C23.teamC.objects.orm.DAOFacade;
import edu.wpi.cs3733.C23.teamC.objects.orm.Persistent;
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

@Entity
@Table(name = "messages", schema = "cdb", catalog = "teamcdb")
public class MessagesEntity implements Persistent {
  // private final SimpleObjectProperty<UUID> messageID = new SimpleObjectProperty<>();
  private final SimpleIntegerProperty messageid = new SimpleIntegerProperty();
  private final SimpleStringProperty senderid = new SimpleStringProperty();
  private final SimpleStringProperty receivingid = new SimpleStringProperty();
  private final SimpleObjectProperty<Timestamp> date = new SimpleObjectProperty<>();
  private final SimpleStringProperty message = new SimpleStringProperty();
  private final SimpleBooleanProperty read = new SimpleBooleanProperty();

  public MessagesEntity() {}

  public MessagesEntity(
      int messageid,
      String senderid,
      String receivingid,
      Timestamp date,
      String message,
      Boolean read) {
    setMessageid(messageid);
    setSenderid(senderid);
    setReceivingid(receivingid);
    setDate(date);
    setMessage(message);
    setRead(read);
  }

  @Override
  public void enablePersistence(DAOFacade orm) {
    final InvalidationListener listener =
        evt -> {
          if (Thread.currentThread() != Main.getUpdaterThread()) orm.merge(this);
        };
    messageid.addListener(listener);
    senderid.addListener(listener);
    receivingid.addListener(listener);
    date.addListener(listener);
    message.addListener(listener);
    read.addListener(listener);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) return false;
    else if (obj == this) return true;
    else if (obj instanceof MessagesEntity that) {
      return Persistent.compareProperties(
          this,
          that,
          MessagesEntity::getMessageid,
          MessagesEntity::getSenderid,
          MessagesEntity::getReceivingid,
          MessagesEntity::getDate,
          MessagesEntity::getMessage,
          MessagesEntity::isRead);
    } else return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        getMessageid(), getSenderid(), getReceivingid(), getDate(), getMessageid(), isRead());
  }

  @Id
  @Column(name = "messageid")
  public int getMessageid() {
    return messageid.get();
  }

  public SimpleIntegerProperty messageidProperty() {
    return messageid;
  }

  public void setMessageid(int messageid) {
    this.messageid.set(messageid);
  }

  @Column(name = "senderid")
  public String getSenderid() {
    return senderid.get();
  }

  public SimpleStringProperty senderidProperty() {
    return senderid;
  }

  public void setSenderid(String senderid) {
    this.senderid.set(senderid);
  }

  @Column(name = "receivingid")
  public String getReceivingid() {
    return receivingid.get();
  }

  public SimpleStringProperty receivingidProperty() {
    return receivingid;
  }

  public void setReceivingid(String receivingid) {
    this.receivingid.set(receivingid);
  }

  @Column(name = "date")
  public Timestamp getDate() {
    return date.get();
  }

  public SimpleObjectProperty<Timestamp> dateProperty() {
    return date;
  }

  public void setDate(Timestamp date) {
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

  @Column(name = "read")
  public boolean isRead() {
    return read.get();
  }

  public SimpleBooleanProperty readProperty() {
    return read;
  }

  public void setRead(boolean read) {
    this.read.set(read);
  }

  @Transient
  public boolean getRead() {
    return isRead();
  }
}
