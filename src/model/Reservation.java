package model;

public class Reservation {
    private AbstractUser user;
    private Room room;
    private String date;
    private String startTime;
    private String endTime;

    public Reservation(AbstractUser user, Room room, String date, String startTime, String endTime) {
        this.user = user;
        this.room = room;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public AbstractUser getUser() {
        return user;
    }

    public Room getRoom() {
        return room;
    }

    public String getDate() {
        return date;
    }

//    public String getTimeSlot() {
//        return timeSlot;
//    }

    public String getStartTime(){
        return startTime;
    }

    public String getEndTime(){
        return endTime;
    }

    @Override
    public String toString() {
        return user.getName() + " memesan " + room.getName() + " pada " + date;
    }

}
