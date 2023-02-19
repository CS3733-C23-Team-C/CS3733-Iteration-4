package edu.wpi.capybara.objects.orm;

import jakarta.persistence.*;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

@Entity
@Table(name = "node", schema = "cdb", catalog = "teamcdb")
public class Node {
    private final SimpleStringProperty id = new SimpleStringProperty();
    private final SimpleIntegerProperty xCoord = new SimpleIntegerProperty();
    private final SimpleIntegerProperty yCoord = new SimpleIntegerProperty();
    private final SimpleStringProperty floor = new SimpleStringProperty();
    private final SimpleStringProperty building = new SimpleStringProperty();

    protected Node() {}

    static Node createPersistent(ORMFacade orm, String id, int xCoord, int yCoord, String floor, String building) {
        final var newNode = new Node();
        newNode.setId(id);
        newNode.setXCoord(xCoord);
        newNode.setYCoord(yCoord);
        newNode.setFloor(floor);
        newNode.setBuilding(building);
        newNode.enableAutomaticPersistence(orm);
        return newNode;
    }

    void enableAutomaticPersistence(ORMFacade orm) {
        final InvalidationListener listener = evt -> orm.merge(this);
        id.addListener(listener);
        xCoord.addListener(listener);
        yCoord.addListener(listener);
        floor.addListener(listener);
        building.addListener(listener);
    }

    @Id
    @Column(name = "nodeid")
    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    @Basic
    @Column(name = "xcoord")
    public int getXCoord() {
        return xCoord.get();
    }

    public void setXCoord(int xCoord) {
        this.xCoord.set(xCoord);
    }

    public SimpleIntegerProperty xCoordProperty() {
        return xCoord;
    }

    @Basic
    @Column(name = "ycoord")
    public int getYCoord() {
        return yCoord.get();
    }

    public void setYCoord(int yCoord) {
        this.yCoord.set(yCoord);
    }

    public SimpleIntegerProperty yCoordProperty() {
        return yCoord;
    }

    @Basic
    @Column(name = "floor")
    public String getFloor() {
        return floor.get();
    }

    public void setFloor(String floor) {
        this.floor.set(floor);
    }

    public SimpleStringProperty floorProperty() {
        return floor;
    }

    @Basic
    @Column(name = "building")
    public String getBuilding() {
        return building.get();
    }

    public void setBuilding(String building) {
        this.building.set(building);
    }

    public SimpleStringProperty buildingProperty() {
        return building;
    }
}
