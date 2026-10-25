/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package duyphuong;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author ASUS
 */
public class Booking implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String bookingId;       // Mã lần đặt duy nhất
    private final String customerName;    // Tên người đặt
    private final String showtimeId;      // Mã suất chiếu
    private final List<Ticket> tickets;   // Danh sách các vé được tạo cùng lần đặt

    public Booking(String bookingId, String customerName, String showtimeId) {
        this.bookingId = bookingId;
        this.customerName = customerName;
        this.showtimeId = showtimeId;
        this.tickets = new ArrayList<>();
    }

    public void addTicket(Ticket ticket) {
        this.tickets.add(ticket);
    }

    // Các Getter và Setter
    public String getBookingId() { return this.bookingId; }
    public String getCustomerName() { return this.customerName; }
    public String getShowtimeId() { return this.showtimeId; }
    public List<Ticket> getTickets() { return this.tickets; }

    // Tính tổng tiền gốc của lần đặt
    public long getTotalOriginalPrice() {
        long total = 0;
        for (Ticket t : this.tickets) {
            total += t.getPrice();
        }
        return total;
    }
}
