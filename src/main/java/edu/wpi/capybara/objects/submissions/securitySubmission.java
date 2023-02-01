package edu.wpi.capybara.objects.submissions;

public class securitySubmission {

    private String employeeID;

    private String room;

    private String floor;

    private String notes;

    public securitySubmission(String id, String roomNumber, String floorNumber, String notesUpdates){
        this.employeeID = id;
        this.room = roomNumber;
        this.floor = floorNumber;
        this.notes = notesUpdates;
    }
    public String getID(){return this.employeeID;}
    public String getRoom(){return this.room;}
    public String getFloor(){return this.floor;}
    public String getNotes(){return this.notes;}
}
