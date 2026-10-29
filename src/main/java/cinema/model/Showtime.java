package cinema.model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * [Thành viên 1] Một suất chiếu: mã suất, tên phim, giờ chiếu và sơ đồ phòng.
 */
public class Showtime {

    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    private final String showtimeId;
    private final String movieTitle;
    private final LocalTime startTime;
    private final Room room;

    public Showtime(String showtimeId, String movieTitle, LocalTime startTime) {
        if (showtimeId == null || showtimeId.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã suất không được rỗng.");
        }
        if (movieTitle == null || movieTitle.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên phim không được rỗng.");
        }
        if (startTime == null) {
            throw new IllegalArgumentException("Giờ chiếu không được rỗng.");
        }
        this.showtimeId = showtimeId.trim().toUpperCase();
        this.movieTitle = movieTitle.trim();
        this.startTime = startTime;
        this.room = new Room();
    }

    public String getShowtimeId() {
        return this.showtimeId;
    }

    public String getMovieTitle() {
        return this.movieTitle;
    }

    public LocalTime getStartTime() {
        return this.startTime;
    }

    public String getStartTimeText() {
        return this.startTime.format(Showtime.TIME_FORMAT);
    }

    public List<Seat> getSeats() {
        return this.room.getSeats();
    }

    public Seat findSeat(String seatId) {
        return this.room.findSeat(seatId);
    }

    @Override
    public String toString() {
        return this.showtimeId + " - " + this.movieTitle + " (" + this.getStartTimeText() + ")";
    }
}
