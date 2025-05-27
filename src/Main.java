import model.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ReservationSystem system = new ReservationSystem();

        while (true) {
            System.out.println("\n=== Login Aplikasi Reservasi Ruangan ===");
            System.out.print("Masukkan nama: ");
            String nama = scanner.nextLine();
            System.out.print("Pilih role (admin/mahasiswa/dosen), atau ketik 'exit' untuk keluar: ");
            String role = scanner.nextLine();

            if (role.equalsIgnoreCase("exit")) {
                System.out.println("Terima kasih telah menggunakan aplikasi.");
                break;
            }

            AbstractUser user;
            switch (role.toLowerCase()) {
                case "admin" -> user = new Admin(nama);
                case "mahasiswa" -> user = new Mahasiswa(nama);
                case "dosen" -> user = new Dosen(nama);
                default -> {
                    System.out.println("Role tidak valid. Coba lagi.");
                    continue;
                }
            }

            system.start(user);
        }
    }
}