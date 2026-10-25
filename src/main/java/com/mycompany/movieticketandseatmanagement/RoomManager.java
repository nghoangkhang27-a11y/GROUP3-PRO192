package com.mycompany.movieticketandseatmanagement;

import java.util.ArrayList;
import java.util.List;

public class RoomManager {
    private List<Showtime> showtimes = new ArrayList<>();

    public RoomManager() {
        showtimes.add(new Showtime("SC01", "Mat ma Java", "09:00"));
        showtimes.add(new Showtime("SC02", "Mat ma Java", "14:00"));
    }

    public Showtime findShowtime(String showtimeId) {
        for (Showtime st : showtimes) {
            if (st.getShowtimeId().equalsIgnoreCase(showtimeId.trim())) {
                return st;
            }
        }
        return null;
    }

    // Tra cứu danh sách suất chiếu (Mã suất, tên phim, giờ chiếu, số ghế trống)
    public void displayShowtimes() {
        System.out.println("\n========== DANH SACH SUAT CHIEU ==========");
        for (Showtime st : showtimes) {
            int availableSeats = 20 - st.getBookedSeatIds().size();
            System.out.println("Ma suat: " + st.getShowtimeId() + " | Phim: " + st.getMovieTitle() + " | Gio: " + st.getStartTime() + " | Ghe trong: " + availableSeats + "/20");
        }
        System.out.println("------------------------------------------");
    }

    // Hiển thị sơ đồ ghế của suất (Trống [A1] / Đã đặt [X])
    public void displaySeatMap(String showtimeId) {
        Showtime st = findShowtime(showtimeId);
        if (st == null) {
            System.out.println("Suat chieu khong ton tai!");
            return;
        }

        System.out.println("\n============== SO DO PHONG CHIEU (" + st.getShowtimeId() + ") ==============");
        System.out.println("                [ MAN HINH CHIEU ]                  ");
        System.out.println("---------------------------------------------------------");
        System.out.println("         1      2      3      4      5");

        char[] rowNames = {'A', 'B', 'C', 'D'};
        for (char r : rowNames) {
            System.out.print("    " + r + "   ");
            for (int i = 1; i <= 5; i++) {
                String sId = r + "" + i;
                if (st.isSeatBooked(sId)) {
                    System.out.print("[ X ]   ");
                } else {
                    System.out.print("[" + sId + "]   ");
                }
            }
            if (r == 'D') {
                System.out.println("  <- Hang VIP (100.000d)");
            } else {
                System.out.println("  <- Hang Standard (70.000d)");
            }
        }
        System.out.println("---------------------------------------------------------");
        System.out.println("Chu thich: [ MaGhe ] = Trong  |  [ X ] = Da duoc dat\n");
    }
}