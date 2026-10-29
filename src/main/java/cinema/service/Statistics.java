package cinema.service;

/**
 * [Thành viên 4] Kết quả thống kê của một suất chiếu.
 */
public class Statistics {

    private final String showtimeId;
    private final int activeTickets;
    private final int freeSeats;
    private final long totalAmount;
    private final long refundedAmount;

    public Statistics(String showtimeId, int activeTickets, int freeSeats,
                      long totalAmount, long refundedAmount) {
        this.showtimeId = showtimeId;
        this.activeTickets = activeTickets;
        this.freeSeats = freeSeats;
        this.totalAmount = totalAmount;
        this.refundedAmount = refundedAmount;
    }

    public String getShowtimeId() {
        return this.showtimeId;
    }

    /** Số vé còn hiệu lực. */
    public int getActiveTickets() {
        return this.activeTickets;
    }

    /** Số ghế trống. */
    public int getFreeSeats() {
        return this.freeSeats;
    }

    /** Tổng tiền gốc của mọi vé đã tạo (kể cả vé đã hủy). */
    public long getTotalAmount() {
        return this.totalAmount;
    }

    /** Tổng tiền đã hoàn. */
    public long getRefundedAmount() {
        return this.refundedAmount;
    }

    /** Số tiền còn lại sau hoàn. */
    public long getRemainingAmount() {
        return this.totalAmount - this.refundedAmount;
    }
}
