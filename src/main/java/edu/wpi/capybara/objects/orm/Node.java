package edu.wpi.capybara.objects.orm;

import edu.wpi.capybara.objects.Floor;
import jakarta.persistence.*;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

@Entity
@Table(name = "node", schema = "cdb", catalog = "teamcdb")
public class Node {
    private final SimpleStringProperty id = new SimpleStringProperty();
    private final SimpleIntegerProperty xCoord = new SimpleIntegerProperty();
    private final SimpleIntegerProperty yCoord = new SimpleIntegerProperty();
    private final SimpleObjectProperty<Floor> floor = new SimpleObjectProperty<>();
    private final SimpleStringProperty building = new SimpleStringProperty();

    public Node() {}

    static Node createPersistent(DAOFacade orm, String id, int xCoord, int yCoord, Floor floor, String building) {
        final var newNode = new Node();
        newNode.setId(id);
        newNode.setXCoord(xCoord);
        newNode.setYCoord(yCoord);
        newNode.setFloor(floor);
        newNode.setBuilding(building);
        newNode.enableAutomaticPersistence(orm);
        return newNode;
    }

    void enableAutomaticPersistence(DAOFacade orm) {
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
    @Convert(converter = Floor.SQLConverter.class)
    @Column(name = "floor")
    public Floor getFloor() {
        return floor.get();
    }

    public void setFloor(Floor floor) {
        this.floor.set(floor);
    }

    public SimpleObjectProperty<Floor> floorProperty() {
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
