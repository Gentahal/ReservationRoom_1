package model;

import java.util.*;

public class ReservationSystem {
    private List<Room> rooms = new ArrayList<>();
    private List<Reservation> reservations = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public void start(AbstractUser user) {
        while (true) {
            System.out.println("\n--- Menu " + user.getRole().toUpperCase() + " ---");
            if (user.getRole().equals("admin")) {
                System.out.println("1. Tambah Ruangan");
                System.out.println("2. Lihat Ruangan");
                System.out.println("3. Hapus Ruangan");
                System.out.println("4. Lihat Reservasi");
                System.out.println("5. Logout");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> tambahRuangan();
                    case 2 -> showRooms();
                    case 3 -> hapusRuangan();
                    case 4 -> showAllReservations();
                    case 5 -> {
                        System.out.println("Logout...");
                        return; // keluar dari menu user & kembali ke login
                    }
                    default -> System.out.println("Pilihan salah");
                }

            } else {
                System.out.println("1. Lihat Ruangan");
                System.out.println("2. Lihat Ruangan Yang Tersedia");
                System.out.println("3. Pesan Ruangan");
                System.out.println("4. Lihat Reservasi Saya");
                System.out.println("5. Logout");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> showRooms();
                    case 2 -> showRoomAvailability();
                    case 3 -> makeReservation(user);
                    case 4 -> showUserReservations(user);
                    case 5 -> {
                        System.out.println("Logout...");
                        return;
                    }
                    default -> System.out.println("Pilihan salah");
                }
            }
        }
    }

    private void tambahRuangan() {
        System.out.print("Masukkan ID Ruangan: ");
        int id = scanner.nextInt(); scanner.nextLine();
        System.out.print("Nama ruangan: ");
        String name = scanner.nextLine();
        System.out.print("Kapasitas: ");
        int cap = scanner.nextInt();
        scanner.nextLine();
        rooms.add(new Room(id,name, cap));
        System.out.println("Berhasil menambahkan ruangan.");
    }

    private void hapusRuangan() {
        showRooms();
        System.out.print("Pilih nomor ruangan yang ingin dihapus: ");
        int index = scanner.nextInt();
        scanner.nextLine();
        if (index > 0 && index <= rooms.size()) {
            rooms.remove(index - 1);
            System.out.println("Ruangan berhasil dihapus.");
        } else {
            System.out.println("Indeks tidak valid.");
        }
    }

    private void showRooms() {
        if (rooms.isEmpty()) {
            System.out.println("Belum ada ruangan.");
            return;
        }
        for (int i = 0; i < rooms.size(); i++) {
            System.out.println((i + 1) + ". " + rooms.get(i));
        }
    }

    private void showAllReservations() {
        if (reservations.isEmpty()) {
            System.out.println("Belum ada reservasi.");
            return;
        }
        for (Reservation r : reservations) {
            System.out.println("- " + r);
        }
    }

    public void makeReservation(AbstractUser user) {
        if (rooms.isEmpty()) {
            System.out.println("Belum ada ruangan tersedia.");
            return;
        }

        showRooms();
        System.out.print("Pilih nomor ruangan: ");
        int roomIndex = scanner.nextInt();
        scanner.nextLine();

        if (roomIndex < 1 || roomIndex > rooms.size()) {
            System.out.println("Pilihan tidak valid.");
            return;
        }

        Room selectedRoom = rooms.get(roomIndex - 1);

        System.out.print("Masukkan tanggal (yyyy-mm-dd): ");
        String date = scanner.nextLine();
        System.out.print("Jam mulai (HH:mm): ");
        String startTime = scanner.nextLine();
        System.out.print("Jam selesai (HH:mm): ");
        String endTime = scanner.nextLine();

        for (Reservation r : reservations) {
            if (r.getRoom().getId() == selectedRoom.getId() && r.getDate().equals(date)) {
                if (isOverlap(r.getStartTime(), r.getEndTime(), startTime, endTime)) {
                    System.out.println("❌ Gagal! Ruangan sudah dipesan pada jam tersebut.");
                    return;
                }
            }
        }

        Reservation newReservation = new Reservation(user, selectedRoom, date, startTime, endTime);
        reservations.add(newReservation);
        System.out.println("✅ Reservasi berhasil dilakukan!");
    }

    private boolean isOverlap(String start1, String end1, String start2, String end2) {
        return start1.compareTo(end2) < 0 && start2.compareTo(end1) < 0;
    }
s
    private void showUserReservations(AbstractUser user) {
        boolean found = false;
        for (Reservation r : reservations) {
            if (r.getUser().getName().equalsIgnoreCase(user.getName())) {
                System.out.println("- " + r);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Belum ada reservasi.");
        }
    }

    public void showRoomAvailability() {
        if (rooms.isEmpty()) {
            System.out.println("Belum ada ruangan yang tersedia.");
            return;
        }

        System.out.println("\n📋 Daftar Ketersediaan Ruangan:");
        for (Room room : rooms) {
            System.out.println("🔹 Ruangan: " + room.getName() + " (Kapasitas: " + room.getCapacity() + ")");
            boolean hasReservation = false;

            for (Reservation reservation : reservations) {
                if (reservation.getRoom().getId() == room.getId()) {
                    hasReservation = true;
                    System.out.println("   ❌ Telah Dipesan:");
                    System.out.println("      📅 " + reservation.getDate() + " ⏰ " + reservation.getStartTime() + " - " + reservation.getEndTime());
                    System.out.println("      👤 Oleh: " + reservation.getUser().getName() + " (" + reservation.getUser().getRole() + ")");
                }
            }

            if (!hasReservation) {
                System.out.println("   ✅ Tersedia Sepenuhnya");
            }
        }
    }

}
