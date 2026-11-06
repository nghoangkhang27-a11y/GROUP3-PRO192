/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package cinema.service;

/**
 *
 * @author Nguyen Chi
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
    public int getActiveTickets() {
        return this.activeTickets;
    }
    public int getFreeSeats() {
        return this.freeSeats;
    }
    public long getTotalAmount() {
        return this.totalAmount;
    }
    
    public long getRefundedAmount() {
        return this.refundedAmount;
    }

    public long getRemainingAmount() {
        return this.totalAmount - this.refundedAmount;
    }
}
