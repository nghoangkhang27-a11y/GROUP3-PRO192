package cinema.model;

/**
 * [Thành viên 3, 4] Một vé = một ghế của một suất.
 * Lưu ĐƠN GIÁ TẠI LÚC ĐẶT. Trạng thái chỉ đổi được qua cancel(),
 * nên không thể bỏ qua bước kiểm tra hủy.
 */
public class Ticket {

    private final String ticketId;
    private final String bookingId;
    private final String showtimeId;
    private final String seatId;
    private final long priceAtBooking;
    private TicketStatus status;

    /** Tạo vé mới (còn hiệu lực). */
    public Ticket(String ticketId, String bookingId, String showtimeId, String seatId, long priceAtBooking) {
        this(ticketId, bookingId, showtimeId, seatId, priceAtBooking, TicketStatus.ACTIVE);
    }

    /** Khôi phục vé từ file (có trạng thái). */
    public Ticket(String ticketId, String bookingId, String showtimeId, String seatId,
            long priceAtBooking, TicketStatus status) {
        this.ticketId = ticketId;
        this.bookingId = bookingId;
        this.showtimeId = showtimeId;
        this.seatId = seatId;
        this.priceAtBooking = priceAtBooking;
        this.status = status;
    }

    /**
     * Hủy vé. Chỉ hủy được vé đang ACTIVE.
     * 
     * @return số tiền hoàn mô phỏng = 100% đơn giá đã lưu trên vé
     * @throws IllegalStateException nếu vé đã hủy trước đó
     */
    public long cancel() {
        if (this.status != TicketStatus.ACTIVE) {
            throw new IllegalStateException("Vé " + this.ticketId + " đã được hủy trước đó.");
        }
        this.status = TicketStatus.CANCELLED;
        return this.priceAtBooking;
    }

    public boolean isActive() {
        return this.status == TicketStatus.ACTIVE;
    }

    /** Số tiền đã hoàn của vé này (0 nếu vé còn hiệu lực). */
    public long getRefundedAmount() {
        return this.status == TicketStatus.CANCELLED ? this.priceAtBooking : 0L;
    }

    public String getTicketId() {
        return this.ticketId;
    }

    public String getBookingId() {
        return this.bookingId;
    }

    public String getShowtimeId() {
        return this.showtimeId;
    }

    public String getSeatId() {
        return this.seatId;
    }

    public long getPriceAtBooking() {
        return this.priceAtBooking;
    }

    public TicketStatus getStatus() {
        return this.status;
    }
}
