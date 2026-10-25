package com.mycompany.movieticketandseatmanagement;

import java.util.ArrayList;
import java.util.List;

public class Showtime {
    private String showtimeId;
    private String movieTitle;
    private String startTime;
    private List<Seat> seats;
    private List<String> bookedSeatIds; // Lưu vết ghế đã đặt để hiện sơ đồ [X]

    public Showtime(String showtimeId, String movieTitle, String startTime) {
        this.showtimeId = showtimeId;
        this.movieTitle = movieTitle;
        this.startTime = startTime;
        this.seats = new ArrayList<>();
        this.bookedSeatIds = new ArrayList<>();
        initializeSeats();
    }

    private void initializeSeats() {
        String[] rows = {"A", "B", "C", "D"};
        for (String r : rows) {
            for (int i = 1; i <= 5; i++) {
                String sId = r + i;
                if (r.equals("D")) {
                    seats.add(new VipSeat(sId));
                } else {
                    seats.add(new StandardSeat(sId));
                }
            }
        }
    }

    public boolean isSeatBooked(String seatId) {
        for (String id : bookedSeatIds) {
            if (id.equalsIgnoreCase(seatId)) return true;
        }
        return false;
    }

    public void addBookedSeat(String seatId) {
        if (!isSeatBooked(seatId)) {
            bookedSeatIds.add(seatId.toUpperCase());
        }
    }

    public String getShowtimeId() { return showtimeId; }
    public String getMovieTitle() { return movieTitle; }
    public String getStartTime() { return startTime; }
    public List<Seat> getSeats() { return seats; }
    public List<String> getBookedSeatIds() { return bookedSeatIds; }

    Object findSeat(String seatId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}