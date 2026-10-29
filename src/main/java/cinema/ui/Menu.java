package cinema.ui;

import cinema.data.DataStore;
import cinema.data.DataStoreException;
import cinema.model.Booking;
import cinema.model.Room;
import cinema.model.Seat;
import cinema.model.Showtime;
import cinema.model.Ticket;
import cinema.service.BookingException;
import cinema.service.BookingService;
import cinema.service.Statistics;

import java.util.List;
import java.util.Scanner;

/**
 * Giao diện console: chỉ NHẬN dữ liệu và HIỂN THỊ kết quả.
 * Mọi quy tắc nghiệp vụ nằm ở BookingService.
 *
 * Phân công:
 *   [Thành viên 1] showShowtimes, showSeatMap
 *   [Thành viên 2] bookTickets (bước kiểm tra, báo giá, xác nhận)
 *   [Thành viên 3] bookTickets (bước tạo vé), lookupTicket
 *   [Thành viên 4] cancelTicket, showStatistics
 *   [Thành viên 5] run (vòng lặp menu), saveData, lưu khi thoát
 */
public class Menu {

    private final BookingService service;
    private final DataStore store;
    private final Scanner scanner;
    private final boolean saveEnabled;
    private boolean endOfInput = false;

    public Menu(BookingService service, DataStore store, Scanner scanner, boolean saveEnabled) {
        this.service = service;
        this.store = store;
        this.scanner = scanner;
        this.saveEnabled = saveEnabled;
    }

    // =====================================================================
    // [Thành viên 5] Vòng lặp menu
    // =====================================================================

    public void run() {
        boolean running = true;
        while (running) {
            this.printMenu();
            int choice = this.readMenuChoice();
            System.out.println();
            switch (choice) {
                case 1:
                    this.showShowtimes();
                    break;
                case 2:
                    this.showSeatMap();
                    break;
                case 3:
                    this.bookTickets();
                    break;
                case 4:
                    this.lookupTicket();
                    break;
                case 5:
                    this.cancelTicket();
                    break;
                case 6:
                    this.showStatistics();
                    break;
                case 7:
                    this.saveData();
                    break;
                case 0:
                    running = !this.exitProgram();
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập số từ 0 đến 7.");
                    break;
            }
        }
    }

    private void printMenu() {
        System.out.println();
        System.out.println("========== ĐẶT VÉ XEM PHIM ==========");
        System.out.println(" 1. Tra cứu suất chiếu");
        System.out.println(" 2. Xem sơ đồ ghế");
        System.out.println(" 3. Đặt vé");
        System.out.println(" 4. Tra cứu vé");
        System.out.println(" 5. Hủy vé");
        System.out.println(" 6. Thống kê theo suất");
        System.out.println(" 7. Lưu dữ liệu");
        System.out.println(" 0. Thoát (tự động lưu)");
        System.out.println("=====================================");
    }

    /** Đọc lựa chọn menu; nhập sai (chữ, rỗng, ngoài miền) thì trả về -1 để báo lỗi. */
    private int readMenuChoice() {
        String line = this.readLine("Chọn chức năng: ");
        if (this.endOfInput) {
            return 0;
        }
        try {
            int value = Integer.parseInt(line.trim());
            if (value < 0 || value > 7) {
                return -1;
            }
            return value;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /** Thoát chương trình. @return true nếu thực sự thoát. */
    private boolean exitProgram() {
        if (!this.saveEnabled) {
            System.out.println("Việc lưu file đang TẮT (do file dữ liệu lỗi) nên dữ liệu lần này không được ghi.");
            System.out.println("Tạm biệt!");
            return true;
        }
        while (true) {
            try {
                this.store.save(this.service);
                System.out.println("Đã lưu dữ liệu vào '" + this.store.getDirectory() + "'. Tạm biệt!");
                return true;
            } catch (DataStoreException e) {
                System.out.println("CHƯA LƯU được dữ liệu: " + e.getMessage());
                if (this.endOfInput) {
                    return true;
                }
                if (!this.confirm("Thử lưu lại? (y = thử lại, n = thoát không lưu)")) {
                    System.out.println("Thoát mà không lưu. Dữ liệu lần này sẽ mất.");
                    return true;
                }
            }
        }
    }

    private void saveData() {
        if (!this.saveEnabled) {
            System.out.println("Việc lưu file đang TẮT vì file dữ liệu lỗi khi khởi động.");
            return;
        }
        try {
            this.store.save(this.service);
            System.out.println("Đã lưu dữ liệu vào '" + this.store.getDirectory() + "'.");
        } catch (DataStoreException e) {
            System.out.println("CHƯA LƯU được dữ liệu: " + e.getMessage());
            System.out.println("Bạn có thể chọn lại chức năng 7 để thử lại.");
        }
    }

    // =====================================================================
    // [Thành viên 1] Suất chiếu và sơ đồ ghế
    // =====================================================================

    private void showShowtimes() {
        System.out.println("---------- DANH SÁCH SUẤT CHIẾU ----------");
        System.out.printf("%-8s %-24s %-8s %s%n", "Mã suất", "Tên phim", "Giờ", "Ghế trống");
        for (Showtime showtime : this.service.getShowtimes()) {
            int free = this.service.countFreeSeats(showtime.getShowtimeId());
            System.out.printf("%-8s %-24s %-8s %d/%d%n", showtime.getShowtimeId(), showtime.getMovieTitle(),
                    showtime.getStartTimeText(), free, Room.TOTAL_SEATS);
        }
    }

    private void showSeatMap() {
        String showtimeId = this.readLine("Nhập mã suất (ví dụ SC01): ");
        try {
            this.printSeatMap(this.service.getShowtime(showtimeId));
        } catch (BookingException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    private void printSeatMap(Showtime showtime) {
        System.out.println("---------- SƠ ĐỒ GHẾ: " + showtime + " ----------");
        System.out.println("                 MÀN HÌNH");
        System.out.println("          ---------------------------");
        StringBuilder header = new StringBuilder("       ");
        for (int n = 1; n <= Room.SEATS_PER_ROW; n++) {
            header.append("  ").append(n).append("   ");
        }
        System.out.println(header);
        for (char row = Room.FIRST_ROW; row <= Room.LAST_ROW; row++) {
            StringBuilder line = new StringBuilder("   " + row + "   ");
            Seat sample = null;
            for (int n = 1; n <= Room.SEATS_PER_ROW; n++) {
                String id = "" + row + n;
                Seat seat = showtime.findSeat(id);
                sample = seat;
                if (this.service.isSeatBooked(showtime.getShowtimeId(), id)) {
                    line.append("[XX] ");
                } else {
                    line.append("[").append(id).append("] ");
                }
            }
            if (sample != null) {
                line.append("  ").append(sample.getTypeName()).append(" ").append(money(sample.calculatePrice()));
            }
            System.out.println(line);
        }
        System.out.println("Chú thích: [A1] = ghế trống | [XX] = ghế đã đặt");
        System.out.println("Loại ghế : hàng A, B, C = Standard | hàng D = VIP");
        System.out.println("Ghế trống: " + this.service.countFreeSeats(showtime.getShowtimeId()) + "/" + Room.TOTAL_SEATS);
    }

    // =====================================================================
    // [Thành viên 2, 3] Đặt vé
    // =====================================================================

    private void bookTickets() {
        try {
            String showtimeId = this.readLine("Nhập mã suất: ");
            Showtime showtime = this.service.getShowtime(showtimeId);
            this.printSeatMap(showtime);

            String name = this.readLine("Tên người đặt: ");
            if (name.trim().isEmpty()) {
                throw new BookingException("Tên người đặt không được rỗng.");
            }
            String seatInput = this.readLine("Nhập danh sách ghế (ví dụ A1, D1): ");
            List<String> seatIds = BookingService.parseSeatInput(seatInput);

            // [Thành viên 2] Kiểm tra TOÀN BỘ danh sách, rồi báo giá (đa hình trong service)
            List<Seat> seats = this.service.validateSeats(showtime.getShowtimeId(), seatIds);
            System.out.println("---------- BÁO GIÁ ----------");
            for (Seat seat : seats) {
                System.out.printf("  %-4s %-9s %s%n", seat.getSeatId(), seat.getTypeName(), money(seat.calculatePrice()));
            }
            System.out.println("  Tổng tiền: " + money(this.service.calculateTotal(seats)));

            if (!this.confirm("Xác nhận đặt vé? (y/n)")) {
                System.out.println("Đã hủy thao tác đặt vé. Không tạo vé và không giữ chỗ.");
                return;
            }

            // [Thành viên 3] Tạo lần đặt và vé
            Booking booking = this.service.createBooking(name, showtime.getShowtimeId(), seatIds);
            System.out.println("ĐẶT VÉ THÀNH CÔNG. Mã lần đặt: " + booking.getBookingId()
                    + " | Người đặt: " + booking.getCustomerName());
            for (Ticket ticket : booking.getTickets()) {
                System.out.println("  Vé " + ticket.getTicketId() + " | Ghế " + ticket.getSeatId()
                        + " | " + money(ticket.getPriceAtBooking()));
            }
            System.out.println("  Tổng tiền: " + money(booking.getTotalAmount()));
        } catch (BookingException e) {
            System.out.println("KHÔNG ĐẶT ĐƯỢC: " + e.getMessage());
            System.out.println("Không có vé nào được tạo, dữ liệu không thay đổi.");
        }
    }

    // =====================================================================
    // [Thành viên 3] Tra cứu vé
    // =====================================================================

    private void lookupTicket() {
        String ticketId = this.readLine("Nhập mã vé (ví dụ V1): ");
        try {
            this.printTicket(this.service.findTicket(ticketId));
        } catch (BookingException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    private void printTicket(Ticket ticket) {
        Booking booking = this.service.findBookingOf(ticket);
        Showtime showtime = this.service.getShowtime(ticket.getShowtimeId());
        Seat seat = showtime.findSeat(ticket.getSeatId());
        System.out.println("---------- THÔNG TIN VÉ ----------");
        System.out.println("  Mã vé          : " + ticket.getTicketId());
        System.out.println("  Người đặt      : " + booking.getCustomerName());
        System.out.println("  Mã lần đặt     : " + booking.getBookingId());
        System.out.println("  Suất chiếu     : " + showtime);
        System.out.println("  Ghế            : " + ticket.getSeatId() + " (" + seat.getTypeName() + ")");
        System.out.println("  Đơn giá lúc đặt: " + money(ticket.getPriceAtBooking()));
        System.out.println("  Trạng thái     : " + ticket.getStatus().getLabel());
    }

    // =====================================================================
    // [Thành viên 4] Hủy vé và thống kê
    // =====================================================================

    private void cancelTicket() {
        String ticketId = this.readLine("Nhập mã vé cần hủy: ");
        try {
            Ticket ticket = this.service.findTicket(ticketId);
            this.printTicket(ticket);
            if (!ticket.isActive()) {
                System.out.println("Vé này đã hủy trước đó, không thể hủy lại.");
                return;
            }
            if (!this.confirm("Xác nhận hủy vé " + ticket.getTicketId() + "? Sẽ hoàn mô phỏng "
                    + money(ticket.getPriceAtBooking()) + " (y/n)")) {
                System.out.println("Đã bỏ qua, vé giữ nguyên.");
                return;
            }
            long refund = this.service.cancelTicket(ticket.getTicketId());
            System.out.println("ĐÃ HỦY vé " + ticket.getTicketId() + ". Hoàn mô phỏng: " + money(refund));
            System.out.println("Ghế " + ticket.getSeatId() + " ở suất " + ticket.getShowtimeId() + " đã được giải phóng.");
        } catch (BookingException e) {
            System.out.println("KHÔNG HỦY ĐƯỢC: " + e.getMessage());
        }
    }

    private void showStatistics() {
        String showtimeId = this.readLine("Nhập mã suất cần thống kê: ");
        try {
            Statistics stats = this.service.getStatistics(showtimeId);
            Showtime showtime = this.service.getShowtime(showtimeId);
            System.out.println("---------- THỐNG KÊ " + showtime + " ----------");
            System.out.println("  Số vé còn hiệu lực : " + stats.getActiveTickets());
            System.out.println("  Số ghế trống       : " + stats.getFreeSeats() + "/" + Room.TOTAL_SEATS);
            System.out.println("  Tổng tiền vé gốc   : " + money(stats.getTotalAmount()));
            System.out.println("  Tổng tiền đã hoàn  : " + money(stats.getRefundedAmount()));
            System.out.println("  Còn lại sau hoàn   : " + money(stats.getRemainingAmount()));
        } catch (BookingException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    // =====================================================================
    // Tiện ích nhập/xuất
    // =====================================================================

    private String readLine(String prompt) {
        System.out.print(prompt);
        if (!this.scanner.hasNextLine()) {
            this.endOfInput = true;
            System.out.println();
            return "";
        }
        return this.scanner.nextLine();
    }

    /** Hỏi xác nhận y/n; hết dữ liệu nhập thì coi như KHÔNG xác nhận. */
    private boolean confirm(String question) {
        while (true) {
            String answer = this.readLine(question + ": ").trim().toLowerCase();
            if (this.endOfInput) {
                return false;
            }
            if (answer.equals("y") || answer.equals("yes") || answer.equals("c") || answer.equals("có")) {
                return true;
            }
            if (answer.equals("n") || answer.equals("no") || answer.equals("k") || answer.equals("không")) {
                return false;
            }
            System.out.println("Vui lòng nhập y (đồng ý) hoặc n (không).");
        }
    }

    /** Định dạng tiền: 170000 -> "170.000 đ". */
    static String money(long amount) {
        String digits = Long.toString(Math.abs(amount));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < digits.length(); i++) {
            if (i > 0 && (digits.length() - i) % 3 == 0) {
                sb.append('.');
            }
            sb.append(digits.charAt(i));
        }
        return (amount < 0 ? "-" : "") + sb + " đ";
    }
}
