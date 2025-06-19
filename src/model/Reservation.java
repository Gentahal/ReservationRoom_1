package model;

import java.time.LocalDateTime;

public class Reservation {

    // Atribut
    private AbstractUser user;
    private Room room;
    private String date;
    private String startTime;
    private String endTime;
    private String status = "PENDING";
    private LocalDateTime reservationTime; // Menyimpan waktu saat pembuatan reservasi


    public Reservation(AbstractUser user, Room room, String date, String startTime, String endTime) {
        this.user = user;
        this.room = room;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = "PENDING";
        this.reservationTime = LocalDateTime.now(); // kode untuk berfungsi untuk mengambil waktu berdasarkan laptop berdasarkan laptop kita
    }

    // FUnction abstract user
    public AbstractUser getUser() {
        return user;
    }

    //
    public Room getRoom() {
        return room;
    }

    public String getDate() {
        return date;
    }

    public LocalDateTime getReservationTime() {
        return reservationTime;
    }

    // Function return boolean berdasarkan waktu , akan mengecheck apakah waktu nya sudah kadaluwarsa atau tidak?
    public boolean isPaymentExpired() {
        return LocalDateTime.now().isAfter(reservationTime.plusMinutes(5)); // ini jika plus 5 menit dari
        // waktu yang telah diberikan atau waktu memesan
    }

    public String getStartTime(){
        return startTime;
    }

    public String getEndTime(){
        return endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Function yang berfungsi mengeluarkan String output
    @Override
    public String toString() {
        return user.getName() + " memesan " + room.getName() + " pada " + date + " | Status : " + status;
    }

}
