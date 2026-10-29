package cinema.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * [Thành viên 1] Phòng chiếu 4 hàng x 5 ghế (A1-D5).
 * Phòng chỉ ĐỊNH NGHĨA ghế, không lưu cờ "đã đặt" dùng chung cho mọi suất.
 */
public class Room {

    public static final char FIRST_ROW = 'A';
    public static final char LAST_ROW = 'D';
    public static final int SEATS_PER_ROW = 5;
    public static final int TOTAL_SEATS = 20;

    private final List<Seat> seats = new ArrayList<Seat>();

    public Room() {
        for (char row = Room.FIRST_ROW; row <= Room.LAST_ROW; row++) {
            for (int number = 1; number <= Room.SEATS_PER_ROW; number++) {
                if (row == 'D') {
                    this.seats.add(new VipSeat(row, number));
                } else {
                    this.seats.add(new StandardSeat(row, number));
                }
            }
        }
    }

    /** Tìm ghế theo mã đã chuẩn hóa (chữ hoa). Không có thì trả về null. */
    public Seat findSeat(String seatId) {
        if (seatId == null) {
            return null;
        }
        for (Seat seat : this.seats) {
            if (seat.getSeatId().equals(seatId)) {
                return seat;
            }
        }
        return null;
    }

    public List<Seat> getSeats() {
        return Collections.unmodifiableList(this.seats);
    }
}
