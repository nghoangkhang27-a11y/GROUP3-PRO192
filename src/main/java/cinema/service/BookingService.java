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

public class BookingService {
    private final Map<String, Showtime> showtimes = new LinkedHashMap<String, Showtime>();
    private final List<Booking> bookings = new ArrayList<Booking>();
    private final Map<String, Booking> bookingById = new LinkedHashMap<String, Booking>();
    private final Map<String, Ticket> ticketById = new LinkedHashMap<String, Ticket>();
    private int lastBookingNumber = 0;
    private int lastTicketNumber = 0;

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

    public static BookingService withSampleData() {
        List<Showtime> list = new ArrayList<Showtime>();
        list.add(new Showtime("SC01", "Java Code", LocalTime.of(9, 0)));
        list.add(new Showtime("SC02", "Java Code", LocalTime.of(14, 0)));
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

    public Collection<Showtime> getShowtimes() {
        return Collections.unmodifiableCollection(this.showtimes.values());
    }

    public Showtime getShowtime(String showtimeId) {
        String id = BookingService.normalize(showtimeId);
        if (id.isEmpty()) {
            throw new BookingException("Showtime ID must nnot be empty.");
        }
        Showtime showtime = this.showtimes.get(id);
        if (showtime == null) {
            throw new BookingException("Showtime'" + id + "'not found.");
        }
        return showtime;
    }

    public boolean isSeatBooked(String showtimeId, String seatId) {
        for (Ticket ticket : this.ticketById.values()) {
            if (ticket.isActive() && ticket.getShowtimeId().equals(seatId)) {
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

    public static List<String> parseSeatInput(String raw) {
        List<String> result = new ArrayList<String>();
        if (raw == null) {
            return result;
        }
        for (String part : raw.split("[,:\\s]+")) {
            String id = BookingService.normalize(part);
            if (!id.isEmpty()) {
                result.add(id);
            }
        }
        return result;
    }

    public List<Seat> validateSeats(String showtimeId, List<String> seatId) {
        Showtime showtime = this.getShowtime(showtimeId);
        if (seatId == null || seatId.isEmpty()) {
            throw new BookingException("Seat list must not be empty.");
        }
        Set<String> seen = new HashSet<String>();
        List<Seat> seats = new ArrayList<Seat>();
        for (String rawId : seatId) {
            String id = BookingService.normalize(rawId);
            if (id.isEmpty()) {
                throw new BookingException("Seat ID must not be empty.");
            }
            if (!seen.add(id)) {
                throw new BookingException("Duplicate seat ID in the booking list: " + id + ".");
            }
            Seat seat = showtime.findSeat(id);
            if (seat == null) {
                throw new BookingException("Seat '" + id + "'does not exist(valid IDs are A1 to D5).");
            }
            if (this.isSeatBooked(showtime.getShowtimeId(), id)) {
                throw new BookingException("Seat" + id + "is already held by an active ticket in showtime"
                        + showtime.getShowtimeId() + ".");
            }
            seats.add(seat);
        }
        return seats;
    }

    public long calculateTotal(List<Seat> seats) {
        long total = 0L;
        for (Seat seat : seats) {
            total += seat.calculatePrice();
        }
        return total;
    }

    public Booking createBooking(String customerName, String showtimeId, List<String> seatIds) {
        String name = customerName == null ? "" : customerName.trim();
        if (name.isEmpty()) {
            throw new BookingException("Customer name must not be empty.");
        }
        Showtime showtime = this.getShowtime(showtimeId);
        List<Seat> seats = this.validateSeats(showtime.getShowtimeId(), seatIds);

        String bookingId = this.nextBookingId();
        Booking booking = new Booking(bookingId, name, showtime.getShowtimeId());
        int ticketCounter = this.lastTicketNumber;
        List<Ticket> newTickets = new ArrayList<Ticket>();
        for (Seat seat : seats) {
            String ticketId = this.nextTicketId(ticketCounter, newTickets);
            ticketCounter = BookingService.numericSuffix(ticketId);
            Ticket ticket = new Ticket(ticketId, bookingId, showtime.getShowtimeId(),
                    seat.getSeatId(), seat.calculatePrice());
            booking.addTicket(ticket);
            newTickets.add(ticket);
        }

        this.lastBookingNumber = BookingService.numericSuffix(bookingId);
        this.lastTicketNumber = ticketCounter;
        this.bookings.add(booking);
        this.bookingById.put(bookingId, booking);
        for (Ticket ticket : newTickets) {
            this.ticketById.put(ticket.getTicketId(), ticket);
        }
        return booking;
    }

    private String nextBookingId() {
        int number = this.lastBookingNumber;
        String id;
        do {
            number++;
            id = "B" + number;
        } while (this.bookingById.containsKey(id));
        return id;
    }

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
            throw new BookingException("Ticket ID must not be empty.");
        }
        Ticket ticket = this.ticketById.get(id);
        if (ticket == null) {
            throw new BookingException("Ticket '" + id + "' not found.");
        }
        return ticket;
    }

    public Booking findBookingOf(Ticket ticket) {
        return this.bookingById.get(ticket.getBookingId());
    }

    public List<Booking> getBookings() {
        return Collections.unmodifiableList(this.bookings);
    }

    public long cancelTicket(String ticketId) {
        Ticket ticket = this.findTicket(ticketId);
        if (!ticket.isActive()) {
            throw new BookingException("Ticket " + ticket.getTicketId()
                    + " was already cancelled and cannot be cancelled again.");
        }
        return ticket.cancel();
    }

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

    private static String normalize(String raw) {
        return raw == null ? "" : raw.trim().toUpperCase();
    }

    public int getTotalSeatsPerShowtime() {
        return Room.TOTAL_SEATS;
    }
}
