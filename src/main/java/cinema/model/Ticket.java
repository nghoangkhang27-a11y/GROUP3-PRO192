package cinema.model;

public class Ticket {
    private final String ticketId;
    private final String bookingId;
    private final String showtimeId;
    private final String seatId;
    private final long priceAtBooking;
    private TicketStatus status;
    
public Ticket(String ticketId, String bookingId, String showtimeId, String seatId, long priceAtBooking) {
    this(ticketId, bookingId , showtimeId, seatId, priceAtBooking, TicketStatus.ACTIVE);
}
public Ticket(String ticketId, String bookingId, String showtimeId, String seatId, long priceAtBooking, TicketStatus status) {
    this.ticketId = ticketId;
    this.bookingId = bookingId;
    this.showtimeId = showtimeId;
    this.seatId = seatId;
    this.priceAtBooking = priceAtBooking;
    this.status = status;
}
public long cancel() {
    if (this.status !=TicketStatus.ACTIVE) {
        throw new IllegalStateException("ticket " + this.ticketId + " cancel before");
    }
    this.status= TicketStatus.CANCELLED;
    return this.priceAtBooking;
}
public boolean isActive() {
    return this.status == TicketStatus.ACTIVE;
}
public long getRefundedAmount() {
    return this.status == TicketStatus.CANCELLED ? this.priceAtBooking : 0L;
}

    public String getTicketId() {
        return ticketId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getShowtimeId() {
        return showtimeId;
    }

    public String getSeatId() {
        return seatId;
    }

    public long getPriceAtBooking() {
        return priceAtBooking;
    }

    public TicketStatus getStatus() {
        return status;
    }

}
   