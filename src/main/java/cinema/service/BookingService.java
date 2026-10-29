package cinema.service;

import cinema.model.Booking;
import cinema.model.Room;
import cinema.model.Seat;
import cinema.model.Showtime;
import cinema.model.Ticket;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Lớp xử lý toàn bộ NGHIỆP VỤ. Menu chỉ nhập/hiển thị, không chứa quy tắc.
 *
 * Phân công:
 *   [Thành viên 1] getShowtimes, getShowtime, countFreeSeats, isSeatBooked
 *   [Thành viên 2] parseSeatInput, validateSeats, calculateTotal
 *   [Thành viên 3] createBooking, findTicket, findBookingOf, sinh mã duy nhất
 *   [Thành viên 4] cancelTicket, getStatistics
 *   [Thành viên 5] constructor khôi phục từ file, withSampleData
 *
 * Trạng thái ghế KHÔNG được lưu riêng: một ghế "đã đặt" khi tồn tại vé ACTIVE
 * có cùng (mã suất, mã ghế). Nhờ vậy dữ liệu ghế và vé không thể lệch nhau.
 */
public class BookingService {

    private final Map<String, Showtime> showtimes = new LinkedHashMap<String, Showtime>();
    private final List<Booking> bookings = new ArrayList<Booking>();
    private final Map<String, Booking> bookingById = new LinkedHashMap<String, Booking>();
    private final Map<String, Ticket> ticketById = new LinkedHashMap<String, Ticket>();
    private int lastBookingNumber = 0;
    private int lastTicketNumber = 0;

    // =====================================================================
    // [Thành viên 5] Khởi tạo
    // =====================================================================

    /** Khôi phục từ dữ liệu đã nạp (DataStore đã kiểm tra tính hợp lệ). */
    public BookingService(List<Showtime> showtimeList, List<Booking> bookingList) {
        for (Showtime showtime : showtimeList) {
            this.showtimes.put(showtime.getShowtimeId(), showtime);
        }
        for (Booking booking : bookingList) {
            this.bookings.add(booking);
            this.bookingById.put(booking.getBookingId(), booking);
            this.lastBookingNumber = Math.max(this.lastBookingNumber,
                    BookingService.numericSuffix(booking.getBookingId()));
            for (Ticket ticket : booking.getTickets()) {
                this.ticketById.put(ticket.getTicketId(), ticket);
                this.lastTicketNumber = Math.max(this.lastTicketNumber,
                        BookingService.numericSuffix(ticket.getTicketId()));
            }
        }
    }

    /** Bộ dữ liệu mẫu theo đề: SC01 (09:00) và SC02 (14:00), chưa có vé. */
    public static BookingService withSampleData() {
        List<Showtime> list = new ArrayList<Showtime>();
        list.add(new Showtime("SC01", "Mật mã Java", LocalTime.of(9, 0)));
        list.add(new Showtime("SC02", "Mật mã Java", LocalTime.of(14, 0)));
        return new BookingService(list, new ArrayList<Booking>());
    }

    private static int numericSuffix(String id) {
        int i = id.length();
        while (i > 0 && Character.isDigit(id.charAt(i - 1))) {
            i--;
        }
        if (i == id.length()) {
            return 0;
        }
        try {
            return Integer.parseInt(id.substring(i));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    // =====================================================================
    // [Thành viên 1] Suất chiếu và trạng thái ghế
    // =====================================================================

    public Collection<Showtime> getShowtimes() {
        return Collections.unmodifiableCollection(this.showtimes.values());
    }

    /** Tìm suất theo mã (không phân biệt hoa/thường, bỏ khoảng trắng đầu/cuối). */
    public Showtime getShowtime(String showtimeId) {
        String id = BookingService.normalize(showtimeId);
        if (id.isEmpty()) {
            throw new BookingException("Mã suất không được rỗng.");
        }
        Showtime showtime = this.showtimes.get(id);
        if (showtime == null) {
            throw new BookingException("Không tìm thấy suất chiếu '" + id + "'.");
        }
        return showtime;
    }

    /** Ghế đã đặt khi có vé ACTIVE cùng (mã suất, mã ghế). */
    public boolean isSeatBooked(String showtimeId, String seatId) {
        for (Ticket ticket : this.ticketById.values()) {
            if (ticket.isActive()
                    && ticket.getShowtimeId().equals(showtimeId)
                    && ticket.getSeatId().equals(seatId)) {
                return true;
            }
        }
        return false;
    }

    public int countFreeSeats(String showtimeId) {
        Showtime showtime = this.getShowtime(showtimeId);
        int free = 0;
        for (Seat seat : showtime.getSeats()) {
            if (!this.isSeatBooked(showtime.getShowtimeId(), seat.getSeatId())) {
                free++;
            }
        }
        return free;
    }

    // =====================================================================
    // [Thành viên 2] Kiểm tra danh sách ghế và báo giá
    // =====================================================================

    /** Tách chuỗi nhập (phân cách bằng dấu phẩy, chấm phẩy hoặc khoảng trắng). */
    public static List<String> parseSeatInput(String raw) {
        List<String> result = new ArrayList<String>();
        if (raw == null) {
            return result;
        }
        for (String part : raw.split("[,;\\s]+")) {
            String id = BookingService.normalize(part);
            if (!id.isEmpty()) {
                result.add(id);
            }
        }
        return result;
    }

    /**
     * Kiểm tra TOÀN BỘ danh sách ghế. Không thay đổi dữ liệu.
     * Ném BookingException nếu: rỗng, ghế lặp, ghế ngoài sơ đồ, ghế đang bị giữ.
     * @return danh sách đối tượng Seat tương ứng (theo đúng thứ tự nhập)
     */
    public List<Seat> validateSeats(String showtimeId, List<String> seatIds) {
        Showtime showtime = this.getShowtime(showtimeId);
        if (seatIds == null || seatIds.isEmpty()) {
            throw new BookingException("Danh sách ghế không được rỗng.");
        }
        Set<String> seen = new HashSet<String>();
        List<Seat> seats = new ArrayList<Seat>();
        for (String rawId : seatIds) {
            String id = BookingService.normalize(rawId);
            if (id.isEmpty()) {
                throw new BookingException("Mã ghế không được rỗng.");
            }
            if (!seen.add(id)) {
                throw new BookingException("Mã ghế bị lặp trong danh sách đặt: " + id + ".");
            }
            Seat seat = showtime.findSeat(id);
            if (seat == null) {
                throw new BookingException("Ghế '" + id + "' không tồn tại (mã hợp lệ từ A1 đến D5).");
            }
            if (this.isSeatBooked(showtime.getShowtimeId(), id)) {
                throw new BookingException("Ghế " + id + " đang được giữ bởi vé còn hiệu lực ở suất "
                        + showtime.getShowtimeId() + ".");
            }
            seats.add(seat);
        }
        return seats;
    }

    /**
     * Tính tổng tiền. ĐA HÌNH: duyệt qua kiểu Seat, mỗi đối tượng tự trả giá
     * đúng loại (StandardSeat 70.000, VipSeat 100.000) - không dùng if/switch.
     */
    public long calculateTotal(List<Seat> seats) {
        long total = 0L;
        for (Seat seat : seats) {
            total += seat.calculatePrice(); // dynamic dispatch
        }
        return total;
    }

    // =====================================================================
    // [Thành viên 3] Tạo lần đặt, mã vé, tra cứu vé
    // =====================================================================

    /**
     * Tạo lần đặt. Kiểm tra mọi điều kiện TRƯỚC, chỉ khi hợp lệ mới tạo và
     * ghi vào bộ nhớ, nên thất bại không để lại dữ liệu một phần.
     */
    public Booking createBooking(String customerName, String showtimeId, List<String> seatIds) {
        String name = customerName == null ? "" : customerName.trim();
        if (name.isEmpty()) {
            throw new BookingException("Tên người đặt không được rỗng.");
        }
        Showtime showtime = this.getShowtime(showtimeId);
        List<Seat> seats = this.validateSeats(showtime.getShowtimeId(), seatIds);

        // Bước 1: dựng đối tượng tạm, CHƯA đụng đến dữ liệu chính
        String bookingId = this.nextBookingId();
        Booking booking = new Booking(bookingId, name, showtime.getShowtimeId());
        int ticketCounter = this.lastTicketNumber;
        List<Ticket> newTickets = new ArrayList<Ticket>();
        for (Seat seat : seats) {
            String ticketId = this.nextTicketId(ticketCounter, newTickets);
            ticketCounter = BookingService.numericSuffix(ticketId);
            Ticket ticket = new Ticket(ticketId, bookingId, showtime.getShowtimeId(),
                    seat.getSeatId(), seat.calculatePrice()); // giá tại lúc đặt (đa hình)
            booking.addTicket(ticket);
            newTickets.add(ticket);
        }

        // Bước 2: mọi thứ đã hợp lệ, ghi vào dữ liệu chính
        this.lastBookingNumber = BookingService.numericSuffix(bookingId);
        this.lastTicketNumber = ticketCounter;
        this.bookings.add(booking);
        this.bookingById.put(bookingId, booking);
        for (Ticket ticket : newTickets) {
            this.ticketById.put(ticket.getTicketId(), ticket);
        }
        return booking;
    }

    /** Sinh mã lần đặt mới, bảo đảm không trùng mã đã có. */
    private String nextBookingId() {
        int number = this.lastBookingNumber;
        String id;
        do {
            number++;
            id = "B" + number;
        } while (this.bookingById.containsKey(id));
        return id;
    }

    /** Sinh mã vé mới, bảo đảm không trùng mã đã có hoặc vé vừa tạo trong lần đặt này. */
    private String nextTicketId(int fromNumber, List<Ticket> pending) {
        int number = Math.max(fromNumber, this.lastTicketNumber);
        String id;
        do {
            number++;
            id = "V" + number;
        } while (this.ticketById.containsKey(id) || BookingService.containsId(pending, id));
        return id;
    }

    private static boolean containsId(List<Ticket> tickets, String id) {
        for (Ticket ticket : tickets) {
            if (ticket.getTicketId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public Ticket findTicket(String ticketId) {
        String id = BookingService.normalize(ticketId);
        if (id.isEmpty()) {
            throw new BookingException("Mã vé không được rỗng.");
        }
        Ticket ticket = this.ticketById.get(id);
        if (ticket == null) {
            throw new BookingException("Không tìm thấy vé '" + id + "'.");
        }
        return ticket;
    }

    public Booking findBookingOf(Ticket ticket) {
        return this.bookingById.get(ticket.getBookingId());
    }

    public List<Booking> getBookings() {
        return Collections.unmodifiableList(this.bookings);
    }

    // =====================================================================
    // [Thành viên 4] Hủy vé và thống kê
    // =====================================================================

    /**
     * Hủy vé theo mã. Chỉ hủy được vé ACTIVE; hủy lần hai bị từ chối.
     * Ghế được giải phóng đúng suất (do trạng thái ghế suy ra từ vé ACTIVE),
     * và không ảnh hưởng vé mới đặt lại cùng ghế.
     * @return số tiền hoàn mô phỏng (100% đơn giá đã lưu trên vé)
     */
    public long cancelTicket(String ticketId) {
        Ticket ticket = this.findTicket(ticketId);
        if (!ticket.isActive()) {
            throw new BookingException("Vé " + ticket.getTicketId()
                    + " đã được hủy trước đó, không thể hủy lại.");
        }
        return ticket.cancel();
    }

    /** Thống kê của một suất: dùng đơn giá đã lưu trên vé, không tính lại từ bảng giá. */
    public Statistics getStatistics(String showtimeId) {
        Showtime showtime = this.getShowtime(showtimeId);
        int active = 0;
        long total = 0L;
        long refunded = 0L;
        for (Ticket ticket : this.ticketById.values()) {
            if (!ticket.getShowtimeId().equals(showtime.getShowtimeId())) {
                continue;
            }
            total += ticket.getPriceAtBooking();
            refunded += ticket.getRefundedAmount();
            if (ticket.isActive()) {
                active++;
            }
        }
        return new Statistics(showtime.getShowtimeId(), active,
                this.countFreeSeats(showtime.getShowtimeId()), total, refunded);
    }

    // =====================================================================
    // Tiện ích
    // =====================================================================

    private static String normalize(String raw) {
        return raw == null ? "" : raw.trim().toUpperCase();
    }

    public int getTotalSeatsPerShowtime() {
        return Room.TOTAL_SEATS;
    }
}
