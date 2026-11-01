package cinema.model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Showtime {
    private String showtimeId;
    private String movieTitle;
    private LocalTime startTime; 
    private List<Seat> seats;

    public Showtime(String showtimeId, String movieTitle, LocalTime startTime) {
        this.showtimeId = showtimeId;
        this.movieTitle = movieTitle;
        this.startTime = startTime;
        this.seats = new ArrayList<>();
        
        
        char rows[] = {'A', 'B', 'C', 'D'};
        for (char r : rows) {
            for (int i = 1; i <= 5; i++) {
                if (r == 'D') {
                    seats.add(new VipSeat(r, i));
                } else {
                    seats.add(new StandardSeat(r, i));
                }
            }
        }
    }


    public Seat findSeat(String seatId) {
        if (seatId == null) return null;
        String normalizedId = seatId.trim().toUpperCase();
        for (Seat s : seats) {
            if (s.getSeatId().equals(normalizedId)) {
                return s;
            }
        }
        return null;
    }

    
    public String getShowtimeId() {
        return showtimeId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    
    public String getStartTimeText() {
        return startTime != null ? startTime.toString() : "";
    }

    public List<Seat> getSeats() {
        return seats;
    }
}