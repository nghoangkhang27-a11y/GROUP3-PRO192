package cinema.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class Booking {
    private final String bookingId;
    private final String customerName;
    private final String showtimeId;
    private final List<Ticket> tickets = new ArrayList<>();
    
    public Booking(String bookingId, String customerName, String showtimeId) {
        this.bookingId = bookingId;
        this.customerName = customerName;
        this.showtimeId = showtimeId;
    }
    
    public void addTicket(Ticket ticket) {
        if (!this.bookingId.equals(ticket.getBookingId())) {
            throw new IllegalArgumentException(" Ticket " + ticket.getTicketId() + " NO booking " + this.bookingId);
        }
        this.tickets.add(ticket);
    }
    public long getTotalAmount() {
        long total = 0L;
        for (Ticket ticket : this.tickets) {
            total += ticket.getPriceAtBooking();
        }
        return total;
    }
    public long getRefundedAmoount() {
        long total = 0L;
        for (Ticket ticket : this.tickets) {
            total += ticket.getRefundedAmount();
        }
        return total;
    }
    public long getActiveAmount() {
        return this.getTotalAmount() - this.getRefundedAmoount();
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