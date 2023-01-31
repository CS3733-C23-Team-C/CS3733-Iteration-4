package edu.wpi.capybara.objects.submissions;

public class pathfindingSubmission {
    private String employeeID;
    private String currRoomNum;
    private String destRoomNum;
    private String moveDate;

    public enum submissionStatus {
        BLANK,
        PROCESSING,
        DONE
    }

    private pathfindingSubmission.submissionStatus currStatus;

    public pathfindingSubmission(String id, String currRoom, String destRoom, String moveDate) {
        this.employeeID = id;
        this.currRoomNum = currRoom;
        this.destRoomNum = destRoom;
        this.moveDate = moveDate;
        this.currStatus = pathfindingSubmission.submissionStatus.PROCESSING;
    }

    public String getID() {
        return this.employeeID;
    }

    public String getCurrRoom() {
        return this.currRoomNum;
    }

    public String getDestRoom() {
        return this.destRoomNum;
    }

    public String getMoveDate(){
        return this.moveDate;
    }

    public String getStatus() { // returns status of submission
        return switch (currStatus) {
            case BLANK -> "Submission Incomplete";
            case PROCESSING -> "Processing";
            case DONE -> "Done";
        };
    }

    public void updateStatus(submissionStatus newStatus) { // when submission is updated in database call this to update state
        this.currStatus = newStatus;
    }
}
