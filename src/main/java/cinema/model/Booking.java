package cinema.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Một lần đặt: chứa các vé được tạo cùng lần.
 */
public class Booking {

    private final String bookingId;
    private final String customerName;
    private final String showtimeId;
    private final List<Ticket> tickets = new ArrayList<Ticket>();

    public Booking(String bookingId, String customerName, String showtimeId) {
        this.bookingId = bookingId;
        this.customerName = customerName;
        this.showtimeId = showtimeId;
    }

    public void addTicket(Ticket ticket) {
        if (!this.bookingId.equals(ticket.getBookingId())) {
            throw new IllegalArgumentException("Vé " + ticket.getTicketId() + " không thuộc lần đặt " + this.bookingId);
        }
        this.tickets.add(ticket);
    }

    /** Tổng tiền gốc = tổng đơn giá các vé lúc tạo (kể cả vé đã hủy). */
    public long getTotalAmount() {
        long total = 0L;
        for (Ticket ticket : this.tickets) {
            total += ticket.getPriceAtBooking();
        }
        return total;
    }

    /** Tổng tiền đã hoàn của các vé đã hủy. */
    public long getRefundedAmount() {
        long total = 0L;
        for (Ticket ticket : this.tickets) {
            total += ticket.getRefundedAmount();
        }
        return total;
    }

    /** Tổng giá trị các vé còn hiệu lực. */
    public long getActiveAmount() {
        return this.getTotalAmount() - this.getRefundedAmount();
    }

    public String getBookingId() {
        return this.bookingId;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public String getShowtimeId() {
        return this.showtimeId;
    }

    public List<Ticket> getTickets() {
        return Collections.unmodifiableList(this.tickets);
    }
}
