/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package duyphuong;
import java.io.Serializable;
/**
 *
 * @author ASUS
 */
public class Ticket implements Serializable {
  private static final long serialVersionUID = 1L;
    
    private final String ticketId;      // Mã vé duy nhất (vd: V1, V2...)
    private final String bookingId;     // Mã lần đặt liên kết
    private final String showtimeId;    // Mã suất chiếu (vd: SC01)
    private final String seatId;        // Mã ghế (vd: A1, D1)
    private final long price;           // Đơn giá tại thời điểm đặt (70000 hoặc 100000)
    private boolean active;       // Trạng thái: true (còn hiệu lực), false (dã hủy)

    public Ticket(String ticketId, String bookingId, String showtimeId, String seatId, long price) {
        this.ticketId = ticketId;
        this.bookingId = bookingId;
        this.showtimeId = showtimeId;
        this.seatId = seatId;
        this.price = price;
        this.active = true; // Vé mới tạo mặc định là còn hiệu lực
    }

    // Các Getter và Setter (tuân thủ nguyên tắc private và this.)
    public String getTicketId() { return this.ticketId; }
    public String getBookingId() { return this.bookingId; }
    public String getShowtimeId() { return this.showtimeId; }
    public String getSeatId() { return this.seatId; }
    public long getPrice() { return this.price; }
    public boolean isActive() { return this.active; }
    public void setActive(boolean active) { this.active = active; }

    @Override
    public String toString() {
        String statusStr = this.active ? "Còn hiệu lực" : "Đã hủy";
        return String.format("Mã vé: %s | Mã đặt: %s | Suất: %s | Ghế: %s | Giá: %,d VNĐ | Trạng thái: %s",
                this.ticketId, this.bookingId, this.showtimeId, this.seatId, this.price, statusStr);
    }
}
    
