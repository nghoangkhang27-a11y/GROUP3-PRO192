package cinema.data;

import cinema.model.Booking;
import cinema.model.Seat;
import cinema.model.Showtime;
import cinema.model.Ticket;
import cinema.model.TicketStatus;
import cinema.service.BookingService;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * [Thành viên 5] Đọc/ghi file văn bản (UTF-8), tách riêng khỏi menu và nghiệp
 * vụ.
 *
 * Thư mục dữ liệu (đường dẫn tương đối, mặc định "data"):
 * showtimes.txt : mã suất | tên phim | giờ (HH:mm)
 * bookings.txt : mã đặt | tên người đặt | mã suất
 * tickets.txt : mã vé | mã đặt | mã suất | mã ghế | đơn giá lúc đặt |
 * ACTIVE/CANCELLED
 *
 * Quy tắc an toàn:
 * - Chưa có file nào -> load() trả về null (lần chạy đầu, để nơi gọi nạp dữ
 * liệu mẫu).
 * - File thiếu/hỏng -> ném DataStoreException, KHÔNG ghi đè gì cả.
 * - save() ghi ra file tạm rồi mới thay file thật, tránh hỏng file cũ khi ghi
 * lỗi.
 */
public class DataStore {

    private static final String SHOWTIMES_FILE = "showtimes.txt";
    private static final String BOOKINGS_FILE = "bookings.txt";
    private static final String TICKETS_FILE = "tickets.txt";
    private static final String SEPARATOR = "|";

    private final Path directory;

    public DataStore(String directoryPath) {
        this.directory = Paths.get(directoryPath);
    }

    public Path getDirectory() {
        return this.directory;
    }

    private Path file(String name) {
        return this.directory.resolve(name);
    }

    // =====================================================================
    // Nạp
    // =====================================================================

    /**
     * @return BookingService khôi phục từ file, hoặc null nếu CHƯA có file nào (lần
     *         chạy đầu)
     * @throws DataStoreException nếu file thiếu một phần, hỏng hoặc không đọc được
     */
    public BookingService load() throws DataStoreException {
        boolean hasShowtimes = Files.exists(this.file(SHOWTIMES_FILE));
        boolean hasBookings = Files.exists(this.file(BOOKINGS_FILE));
        boolean hasTickets = Files.exists(this.file(TICKETS_FILE));

        if (!hasShowtimes && !hasBookings && !hasTickets) {
            return null;
        }
        if (!hasShowtimes || !hasBookings || !hasTickets) {
            throw new DataStoreException("Thư mục dữ liệu '" + this.directory
                    + "' bị thiếu file (cần đủ " + SHOWTIMES_FILE + ", " + BOOKINGS_FILE
                    + ", " + TICKETS_FILE + ").");
        }

        Map<String, Showtime> showtimes = this.readShowtimes();
        Map<String, Booking> bookings = this.readBookings(showtimes);
        this.readTickets(showtimes, bookings);
        return new BookingService(new ArrayList<Showtime>(showtimes.values()),
                new ArrayList<Booking>(bookings.values()));
    }

    private Map<String, Showtime> readShowtimes() throws DataStoreException {
        Map<String, Showtime> result = new LinkedHashMap<String, Showtime>();
        List<String[]> rows = this.readRows(SHOWTIMES_FILE, 3);
        int lineNo = 0;
        for (String[] f : rows) {
            lineNo++;
            String id = this.field(f[0], SHOWTIMES_FILE, lineNo).trim().toUpperCase();
            String title = this.field(f[1], SHOWTIMES_FILE, lineNo);
            String timeText = this.field(f[2], SHOWTIMES_FILE, lineNo).trim();
            if (id.isEmpty() || title.trim().isEmpty()) {
                throw this.bad(SHOWTIMES_FILE, lineNo, "mã suất hoặc tên phim rỗng");
            }
            LocalTime time;
            try {
                time = LocalTime.parse(timeText);
            } catch (DateTimeParseException e) {
                throw this.bad(SHOWTIMES_FILE, lineNo, "giờ chiếu '" + timeText + "' sai định dạng HH:mm");
            }
            if (result.containsKey(id)) {
                throw this.bad(SHOWTIMES_FILE, lineNo, "trùng mã suất " + id);
            }
            result.put(id, new Showtime(id, title, time));
        }
        if (result.isEmpty()) {
            throw new DataStoreException("File " + SHOWTIMES_FILE + " không có suất chiếu nào.");
        }
        return result;
    }

    private Map<String, Booking> readBookings(Map<String, Showtime> showtimes) throws DataStoreException {
        Map<String, Booking> result = new LinkedHashMap<String, Booking>();
        List<String[]> rows = this.readRows(BOOKINGS_FILE, 3);
        int lineNo = 0;
        for (String[] f : rows) {
            lineNo++;
            String id = this.field(f[0], BOOKINGS_FILE, lineNo).trim();
            String name = this.field(f[1], BOOKINGS_FILE, lineNo).trim();
            String showtimeId = this.field(f[2], BOOKINGS_FILE, lineNo).trim().toUpperCase();
            if (id.isEmpty() || name.isEmpty()) {
                throw this.bad(BOOKINGS_FILE, lineNo, "mã lần đặt hoặc tên người đặt rỗng");
            }
            if (result.containsKey(id)) {
                throw this.bad(BOOKINGS_FILE, lineNo, "trùng mã lần đặt " + id);
            }
            if (!showtimes.containsKey(showtimeId)) {
                throw this.bad(BOOKINGS_FILE, lineNo, "suất chiếu " + showtimeId + " không tồn tại");
            }
            result.put(id, new Booking(id, name, showtimeId));
        }
        return result;
    }

    private void readTickets(Map<String, Showtime> showtimes, Map<String, Booking> bookings)
            throws DataStoreException {
        Set<String> ticketIds = new HashSet<String>();
        Set<String> activeSeatKeys = new HashSet<String>();
        List<String[]> rows = this.readRows(TICKETS_FILE, 6);
        int lineNo = 0;
        for (String[] f : rows) {
            lineNo++;
            String ticketId = this.field(f[0], TICKETS_FILE, lineNo).trim();
            String bookingId = this.field(f[1], TICKETS_FILE, lineNo).trim();
            String showtimeId = this.field(f[2], TICKETS_FILE, lineNo).trim().toUpperCase();
            String seatId = this.field(f[3], TICKETS_FILE, lineNo).trim().toUpperCase();
            String priceText = this.field(f[4], TICKETS_FILE, lineNo).trim();
            String statusText = this.field(f[5], TICKETS_FILE, lineNo).trim();

            if (ticketId.isEmpty()) {
                throw this.bad(TICKETS_FILE, lineNo, "mã vé rỗng");
            }
            if (!ticketIds.add(ticketId)) {
                throw this.bad(TICKETS_FILE, lineNo, "trùng mã vé " + ticketId);
            }
            Booking booking = bookings.get(bookingId);
            if (booking == null) {
                throw this.bad(TICKETS_FILE, lineNo, "lần đặt " + bookingId + " không tồn tại");
            }
            Showtime showtime = showtimes.get(showtimeId);
            if (showtime == null) {
                throw this.bad(TICKETS_FILE, lineNo, "suất chiếu " + showtimeId + " không tồn tại");
            }
            if (!booking.getShowtimeId().equals(showtimeId)) {
                throw this.bad(TICKETS_FILE, lineNo, "vé và lần đặt " + bookingId + " khác suất chiếu");
            }
            Seat seat = showtime.findSeat(seatId);
            if (seat == null) {
                throw this.bad(TICKETS_FILE, lineNo, "ghế " + seatId + " không tồn tại");
            }
            long price;
            try {
                price = Long.parseLong(priceText);
            } catch (NumberFormatException e) {
                throw this.bad(TICKETS_FILE, lineNo, "đơn giá '" + priceText + "' không phải số");
            }
            if (price < 0) {
                throw this.bad(TICKETS_FILE, lineNo, "đơn giá âm");
            }
            TicketStatus status;
            try {
                status = TicketStatus.valueOf(statusText);
            } catch (IllegalArgumentException e) {
                throw this.bad(TICKETS_FILE, lineNo, "trạng thái '" + statusText + "' không hợp lệ");
            }
            if (status == TicketStatus.ACTIVE && !activeSeatKeys.add(showtimeId + "/" + seatId)) {
                throw this.bad(TICKETS_FILE, lineNo, "ghế " + seatId + " ở suất " + showtimeId
                        + " bị hai vé còn hiệu lực cùng giữ");
            }
            booking.addTicket(new Ticket(ticketId, bookingId, showtimeId, seatId, price, status));
        }
    }

    /**
     * Đọc các dòng dữ liệu (bỏ dòng trống và dòng chú thích '#'), kiểm tra đủ số
     * trường.
     */
    private List<String[]> readRows(String fileName, int expectedFields) throws DataStoreException {
        List<String> lines;
        try {
            lines = Files.readAllLines(this.file(fileName), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new DataStoreException("Không đọc được file " + fileName + ": " + e.getMessage(), e);
        }
        List<String[]> rows = new ArrayList<String[]>();
        int lineNo = 0;
        for (String line : lines) {
            lineNo++;
            if (line.trim().isEmpty() || line.startsWith("#")) {
                continue;
            }
            String[] parts = line.split("\\|", -1);
            if (parts.length != expectedFields) {
                throw this.bad(fileName, lineNo, "cần " + expectedFields + " trường nhưng có " + parts.length);
            }
            rows.add(parts);
        }
        return rows;
    }

    private DataStoreException bad(String fileName, int line, String reason) {
        return new DataStoreException("File " + fileName + " bị lỗi (dòng dữ liệu thứ " + line + "): " + reason + ".");
    }

    private String field(String raw, String fileName, int line) throws DataStoreException {
        try {
            return DataStore.unescape(raw);
        } catch (IllegalArgumentException e) {
            throw this.bad(fileName, line, e.getMessage());
        }
    }

    // =====================================================================
    // Ghi
    // =====================================================================

    /**
     * Ghi toàn bộ dữ liệu. Ghi ra file tạm trước, thành công cả ba file mới thay
     * file thật.
     * 
     * @throws DataStoreException nếu không ghi được (nơi gọi báo "chưa lưu" và cho
     *                            thử lại)
     */
    public void save(BookingService service) throws DataStoreException {
        Path tmpShowtimes = this.file(SHOWTIMES_FILE + ".tmp");
        Path tmpBookings = this.file(BOOKINGS_FILE + ".tmp");
        Path tmpTickets = this.file(TICKETS_FILE + ".tmp");
        try {
            Files.createDirectories(this.directory);
            this.writeShowtimes(tmpShowtimes, service);
            this.writeBookings(tmpBookings, service);
            this.writeTickets(tmpTickets, service);
            Files.move(tmpShowtimes, this.file(SHOWTIMES_FILE), StandardCopyOption.REPLACE_EXISTING);
            Files.move(tmpBookings, this.file(BOOKINGS_FILE), StandardCopyOption.REPLACE_EXISTING);
            Files.move(tmpTickets, this.file(TICKETS_FILE), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            DataStore.deleteQuietly(tmpShowtimes);
            DataStore.deleteQuietly(tmpBookings);
            DataStore.deleteQuietly(tmpTickets);
            throw new DataStoreException("Không ghi được dữ liệu vào '" + this.directory + "': " + e.getMessage(), e);
        }
    }

    private void writeShowtimes(Path path, BookingService service) throws IOException {
        BufferedWriter w = Files.newBufferedWriter(path, StandardCharsets.UTF_8);
        try {
            w.write("# showtimeId|movieTitle|startTime(HH:mm)");
            w.newLine();
            for (Showtime s : service.getShowtimes()) {
                w.write(DataStore.escape(s.getShowtimeId()) + SEPARATOR + DataStore.escape(s.getMovieTitle())
                        + SEPARATOR + s.getStartTimeText());
                w.newLine();
            }
        } finally {
            w.close();
        }
    }

    private void writeBookings(Path path, BookingService service) throws IOException {
        BufferedWriter w = Files.newBufferedWriter(path, StandardCharsets.UTF_8);
        try {
            w.write("# bookingId|customerName|showtimeId");
            w.newLine();
            for (Booking b : service.getBookings()) {
                w.write(DataStore.escape(b.getBookingId()) + SEPARATOR + DataStore.escape(b.getCustomerName())
                        + SEPARATOR + DataStore.escape(b.getShowtimeId()));
                w.newLine();
            }
        } finally {
            w.close();
        }
    }

    private void writeTickets(Path path, BookingService service) throws IOException {
        BufferedWriter w = Files.newBufferedWriter(path, StandardCharsets.UTF_8);
        try {
            w.write("# ticketId|bookingId|showtimeId|seatId|priceAtBooking|status");
            w.newLine();
            for (Booking b : service.getBookings()) {
                for (Ticket t : b.getTickets()) {
                    w.write(DataStore.escape(t.getTicketId()) + SEPARATOR + DataStore.escape(t.getBookingId())
                            + SEPARATOR + DataStore.escape(t.getShowtimeId()) + SEPARATOR
                            + DataStore.escape(t.getSeatId())
                            + SEPARATOR + t.getPriceAtBooking() + SEPARATOR + t.getStatus().name());
                    w.newLine();
                }
            }
        } finally {
            w.close();
        }
    }

    private static void deleteQuietly(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException ignored) {
            // bỏ qua: chỉ dọn file tạm
        }
    }

    // =====================================================================
    // Mã hóa ký tự đặc biệt để tên có dấu '|' hoặc xuống dòng vẫn lưu đúng
    // =====================================================================

    static String escape(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '\\') {
                sb.append("\\\\");
            } else if (c == '|') {
                sb.append("\\p");
            } else if (c == '\n') {
                sb.append("\\n");
            } else if (c == '\r') {
                sb.append("\\r");
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    static String unescape(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c != '\\') {
                sb.append(c);
                continue;
            }
            if (i + 1 >= s.length()) {
                throw new IllegalArgumentException("ký tự '\\' đứng cuối trường");
            }
            char next = s.charAt(++i);
            if (next == '\\') {
                sb.append('\\');
            } else if (next == 'p') {
                sb.append('|');
            } else if (next == 'n') {
                sb.append('\n');
            } else if (next == 'r') {
                sb.append('\r');
            } else {
                throw new IllegalArgumentException("chuỗi thoát '\\" + next + "' không hợp lệ");
            }
        }
        return sb.toString();
    }
}
