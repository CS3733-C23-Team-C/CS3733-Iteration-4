package edu.wpi.cs3733.C23.teamC.objects.hibernate;

import edu.wpi.cs3733.C23.teamC.Main;
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Entity
@Table(name = "messages", schema = "cdb", catalog = "teamcdb")
public class MessagesEntity2 {
  @Id
  @Column(name = "messageid")
  private int messageid;

  @Basic
  @Column(name = "senderid")
  private String senderid;

  @Basic
  @Column(name = "receivingid")
  private String receivingid;

  @Basic
  @Column(name = "date")
  private Timestamp date;

  @Basic
  @Column(name = "message")
  private String message;

  @Basic
  @Column(name = "read")
  private Boolean read;

  public MessagesEntity2() {}

  public MessagesEntity2(
      int messageid,
      String senderid,
      String receivingid,
      Timestamp date,
      String message,
      Boolean read) {
    this.messageid = messageid;
    this.senderid = senderid;
    this.receivingid = receivingid;
    this.date = date;
    this.message = message;
    this.read = read;
  }

  public int getMessageid() {
    return messageid;
  }

  public void setMessageid(int messageid) {
    this.messageid = messageid;
    Session session = Main.db.getSession();
    Transaction tx = session.beginTransaction();
    session.merge(this);
    tx.commit();
    session.close();
  }

  public String getSenderid() {
    return senderid;
  }

  public void setSenderid(String senderid) {
    this.senderid = senderid;
    Session session = Main.db.getSession();
    Transaction tx = session.beginTransaction();
    session.merge(this);
    tx.commit();
    session.close();
  }

  public String getReceivingid() {
    return receivingid;
  }

  public void setReceivingid(String receivingid) {
    this.receivingid = receivingid;
    Session session = Main.db.getSession();
    Transaction tx = session.beginTransaction();
    session.merge(this);
    tx.commit();
    session.close();
  }

  public Timestamp getDate() {
    return date;
  }

  public void setDate(Timestamp date) {
    this.date = date;
    Session session = Main.db.getSession();
    Transaction tx = session.beginTransaction();
    session.merge(this);
    tx.commit();
    session.close();
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
    Session session = Main.db.getSession();
    Transaction tx = session.beginTransaction();
    session.merge(this);
    tx.commit();
    session.close();
  }

  public Boolean getRead() {
    return read;
  }

  public void setRead(Boolean read) {
    this.read = read;
    Session session = Main.db.getSession();
    Transaction tx = session.beginTransaction();
    session.merge(this);
    tx.commit();
    session.close();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MessagesEntity2 that = (MessagesEntity2) o;
    return messageid == that.messageid
        && Objects.equals(senderid, that.senderid)
        && Objects.equals(receivingid, that.receivingid)
        && Objects.equals(date, that.date)
        && Objects.equals(message, that.message)
        && Objects.equals(read, that.read);
  }

  @Override
  public String toString() {
    return "MessagesEntity2{"
        + "messageid="
        + messageid
        + ", senderid='"
        + senderid
        + '\''
        + ", receivingid='"
        + receivingid
        + '\''
        + ", date="
        + date
        + ", message='"
        + message
        + '\''
        + ", read="
        + read
        + '}';
  }

  @Override
  public int hashCode() {
    return Objects.hash(messageid, senderid, receivingid, date, message, read);
  }
}
