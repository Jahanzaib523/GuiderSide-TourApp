package Models;

import java.io.Serializable;

public class TripInfo implements Serializable
{
    private String UID;
    private String fromlocation;
    private String tolocation;
    private String startdate;
    private String enddate;
    private String guiderID;
    private String tripstatus;

    public TripInfo(String ID, String fromlocation, String tolocation, String startlocation, String endlocation) {
        this.UID = ID;
        this.fromlocation = fromlocation;
        this.tolocation = tolocation;
        this.startdate = startlocation;
        this.enddate = endlocation;
    }

    public TripInfo(String UID_, String fromlocation, String tolocation, String startdate, String enddate, String guiderID, String tripstatus) {
        this.UID = UID_;
        this.fromlocation = fromlocation;
        this.tolocation = tolocation;
        this.startdate = startdate;
        this.enddate = enddate;
        this.guiderID = guiderID;
        this.tripstatus = tripstatus;
    }

    @Override
    public String toString() {
        return "TripInfo{" +
                "UID='" + UID + '\'' +
                ", fromlocation='" + fromlocation + '\'' +
                ", tolocation='" + tolocation + '\'' +
                ", startdate='" + startdate + '\'' +
                ", enddate='" + enddate + '\'' +
                ", guiderID='" + guiderID + '\'' +
                ", tripstatus='" + tripstatus + '\'' +
                '}';
    }

    public String getGuiderID() {
        return guiderID;
    }

    public void setGuiderID(String guiderID) {
        this.guiderID = guiderID;
    }

    public String getTripstatus() {
        return tripstatus;
    }

    public void setTripstatus(String tripstatus) {
        this.tripstatus = tripstatus;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID_) {
        this.UID = UID_;
    }

    public String getFromlocation() {
        return fromlocation;
    }

    public void setFromlocation(String fromlocation) {
        this.fromlocation = fromlocation;
    }

    public String getTolocation() {
        return tolocation;
    }

    public void setTolocation(String tolocation) {
        this.tolocation = tolocation;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startlocation) {
        this.startdate = startlocation;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String endlocation) {
        this.enddate = endlocation;
    }
}
