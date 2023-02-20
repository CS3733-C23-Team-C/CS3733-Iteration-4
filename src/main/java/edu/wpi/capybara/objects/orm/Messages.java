package edu.wpi.capybara.objects.orm;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "messages", schema = "cdb", catalog = "teamcdb")
public class Messages implements Persistent {
    private final SimpleObjectProperty<UUID> messageID = new SimpleObjectProperty<>();
    private final SimpleStringProperty senderID = new SimpleStringProperty();
    private final SimpleStringProperty receivingID = new SimpleStringProperty();
    private final SimpleObjectProperty<Timestamp> date = new SimpleObjectProperty<>();
    private final SimpleStringProperty message = new SimpleStringProperty();
    private final SimpleBooleanProperty read = new SimpleBooleanProperty();

    protected Messages() {}

    @Override
    public void enablePersistence(DAOFacade orm) {
        final InvalidationListener listener = evt -> orm.merge(this);
        messageID.addListener(listener);
        senderID.addListener(listener);
        receivingID.addListener(listener);
        date.addListener(listener);
        message.addListener(listener);
        read.addListener(listener);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        else if (obj == this) return true;
        else if (obj instanceof Messages that) {
            return Persistent.compareProperties(this, that,
                    Messages::getMessageID,
                    Messages::getSenderID,
                    Messages::getReceivingID,
                    Messages::getDate,
                    Messages::getMessage,
                    Messages::isRead);
        } else return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getMessageID(),
                getSenderID(),
                getReceivingID(),
                getDate(),
                getMessageID(),
                isRead()
        );
    }

    @Id
    @Column(name = "messageid")
    public UUID getMessageID() {
        return messageID.get();
    }

    public SimpleObjectProperty<UUID> messageIDProperty() {
        return messageID;
    }

    public void setMessageID(UUID messageID) {
        this.messageID.set(messageID);
    }

    @Column(name = "senderid")
    public String getSenderID() {
        return senderID.get();
    }

    public SimpleStringProperty senderIDProperty() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID.set(senderID);
    }

    @Column(name = "receivingid")
    public String getReceivingID() {
        return receivingID.get();
    }

    public SimpleStringProperty receivingIDProperty() {
        return receivingID;
    }

    public void setReceivingID(String receivingID) {
        this.receivingID.set(receivingID);
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
}
