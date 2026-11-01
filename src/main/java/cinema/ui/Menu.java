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
                    System.out.println("Invalid choice. Please enter a number from 0 to 7.");
                    break;
            }
        }
    }

    private void printMenu() {
        System.out.println();
        System.out.println("========== MOVIE TICKET BOOKING ==========");
        System.out.println(" 1. Show showtimes");
        System.out.println(" 2. View seat map");
        System.out.println(" 3. Book tickets");
        System.out.println(" 4. Look up ticket");
        System.out.println(" 5. Cancel ticket");
        System.out.println(" 6. Showtime statistics");
        System.out.println(" 7. Save data");
        System.out.println(" 0. Exit (auto-save)");
        System.out.println("==========================================");
    }

    private int readMenuChoice() {
        String line = this.readLine("Choose an option: ");
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

    private boolean exitProgram() {
        if (!this.saveEnabled) {
            System.out
                    .println("Saving is DISABLED (the data file was invalid), so this session's data was not written.");
            System.out.println("Goodbye!");
            return true;
        }
        while (true) {
            try {
                this.store.save(this.service);
                System.out.println("Data saved to '" + this.store.getDirectory() + "'. Goodbye!");
                return true;
            } catch (DataStoreException e) {
                System.out.println("NOT SAVED: " + e.getMessage());
                if (this.endOfInput) {
                    return true;
                }
                if (!this.confirm("Retry saving? (y = retry, n = exit without saving)")) {
                    System.out.println("Exiting without saving. This session's data will be lost.");
                    return true;
                }
            }
        }
    }

    private void saveData() {
        if (!this.saveEnabled) {
            System.out.println("Saving is DISABLED because the data file was invalid at startup.");
            return;
        }
        try {
            this.store.save(this.service);
            System.out.println("Data saved to '" + this.store.getDirectory() + "'.");
        } catch (DataStoreException e) {
            System.out.println("NOT SAVED: " + e.getMessage());
            System.out.println("You can choose option 7 again to retry.");
        }
    }

    private void showShowtimes() {
        System.out.println("---------- SHOWTIMES ----------");
        System.out.printf("%-10s %-24s %-8s %s%n", "Showtime", "Movie", "Time", "Free seats");
        for (Showtime showtime : this.service.getShowtimes()) {
            int free = this.service.countFreeSeats(showtime.getShowtimeId());
            System.out.printf("%-10s %-24s %-8s %d/%d%n", showtime.getShowtimeId(), showtime.getMovieTitle(),
                    showtime.getStartTimeText(), free, Room.TOTAL_SEATS);
        }
    }

    private void showSeatMap() {
        String showtimeId = this.readLine("Enter showtime ID (e.g. SC01): ");
        try {
            this.printSeatMap(this.service.getShowtime(showtimeId));
        } catch (BookingException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void printSeatMap(Showtime showtime) {
        System.out.println("---------- SEAT MAP: " + showtime + " ----------");
        System.out.println("                 SCREEN");
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
        System.out.println("Legend    : [A1] = free seat | [XX] = booked seat");
        System.out.println("Seat types: rows A, B, C = Standard | row D = VIP");
        System.out.println(
                "Free seats: " + this.service.countFreeSeats(showtime.getShowtimeId()) + "/" + Room.TOTAL_SEATS);
    }

    private void bookTickets() {
        try {
            String showtimeId = this.readLine("Enter showtime ID: ");
            Showtime showtime = this.service.getShowtime(showtimeId);
            this.printSeatMap(showtime);

            String name = this.readLine("Customer name: ");
            if (name.trim().isEmpty()) {
                throw new BookingException("Customer name must not be empty.");
            }
            String seatInput = this.readLine("Enter seats (e.g. A1, D1): ");
            List<String> seatIds = BookingService.parseSeatInput(seatInput);

            List<Seat> seats = this.service.validateSeats(showtime.getShowtimeId(), seatIds);
            System.out.println("---------- PRICE QUOTE ----------");
            for (Seat seat : seats) {
                System.out.printf("  %-4s %-9s %s%n", seat.getSeatId(), seat.getTypeName(),
                        money(seat.calculatePrice()));
            }
            System.out.println("  Total: " + money(this.service.calculateTotal(seats)));

            if (!this.confirm("Confirm booking? (y/n)")) {
                System.out.println("Booking cancelled. No ticket was created and no seat was held.");
                return;
            }

            Booking booking = this.service.createBooking(name, showtime.getShowtimeId(), seatIds);
            System.out.println("BOOKING SUCCESSFUL. Booking ID: " + booking.getBookingId()
                    + " | Customer: " + booking.getCustomerName());
            for (Ticket ticket : booking.getTickets()) {
                System.out.println("  Ticket " + ticket.getTicketId() + " | Seat " + ticket.getSeatId()
                        + " | " + money(ticket.getPriceAtBooking()));
            }
            System.out.println("  Total: " + money(booking.getTotalAmount()));
        } catch (BookingException e) {
            System.out.println("BOOKING FAILED: " + e.getMessage());
            System.out.println("No ticket was created. Data is unchanged.");
        }
    }

    private void lookupTicket() {
        String ticketId = this.readLine("Enter ticket ID (e.g. V1): ");
        try {
            this.printTicket(this.service.findTicket(ticketId));
        } catch (BookingException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void printTicket(Ticket ticket) {
        Booking booking = this.service.findBookingOf(ticket);
        Showtime showtime = this.service.getShowtime(ticket.getShowtimeId());
        Seat seat = showtime.findSeat(ticket.getSeatId());
        System.out.println("---------- TICKET INFORMATION ----------");
        System.out.println("  Ticket ID        : " + ticket.getTicketId());
        System.out.println("  Customer         : " + booking.getCustomerName());
        System.out.println("  Booking ID       : " + booking.getBookingId());
        System.out.println("  Showtime         : " + showtime);
        System.out.println("  Seat             : " + ticket.getSeatId() + " (" + seat.getTypeName() + ")");
        System.out.println("  Price at booking : " + money(ticket.getPriceAtBooking()));
        System.out.println("  Status           : " + ticket.getStatus().name());
    }

    private void cancelTicket() {
        String ticketId = this.readLine("Enter ticket ID to cancel: ");
        try {
            Ticket ticket = this.service.findTicket(ticketId);
            this.printTicket(ticket);
            if (!ticket.isActive()) {
                System.out.println("This ticket was already cancelled and cannot be cancelled again.");
                return;
            }
            if (!this.confirm("Cancel ticket " + ticket.getTicketId() + "? A simulated refund of "
                    + money(ticket.getPriceAtBooking()) + " will be issued (y/n)")) {
                System.out.println("Skipped. The ticket is unchanged.");
                return;
            }
            long refund = this.service.cancelTicket(ticket.getTicketId());
            System.out.println("CANCELLED ticket " + ticket.getTicketId() + ". Simulated refund: " + money(refund));
            System.out.println(
                    "Seat " + ticket.getSeatId() + " in showtime " + ticket.getShowtimeId() + " has been released.");
        } catch (BookingException e) {
            System.out.println("CANCEL FAILED: " + e.getMessage());
        }
    }

    private void showStatistics() {
        String showtimeId = this.readLine("Enter showtime ID for statistics: ");
        try {
            Statistics stats = this.service.getStatistics(showtimeId);
            Showtime showtime = this.service.getShowtime(showtimeId);
            System.out.println("---------- STATISTICS " + showtime + " ----------");
            System.out.println("  Active tickets    : " + stats.getActiveTickets());
            System.out.println("  Free seats        : " + stats.getFreeSeats() + "/" + Room.TOTAL_SEATS);
            System.out.println("  Total (original)  : " + money(stats.getTotalAmount()));
            System.out.println("  Total refunded    : " + money(stats.getRefundedAmount()));
            System.out.println("  Remaining         : " + money(stats.getRemainingAmount()));
        } catch (BookingException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private String readLine(String prompt) {
        System.out.print(prompt);
        if (!this.scanner.hasNextLine()) {
            this.endOfInput = true;
            System.out.println();
            return "";
        }
        return this.scanner.nextLine();
    }

    private boolean confirm(String question) {
        while (true) {
            String answer = this.readLine(question + ": ").trim().toLowerCase();
            if (this.endOfInput) {
                return false;
            }
            if (answer.equals("y") || answer.equals("yes")) {
                return true;
            }
            if (answer.equals("n") || answer.equals("no")) {
                return false;
            }
            System.out.println("Please enter y (yes) or n (no).");
        }
    }

    static String money(long amount) {
        String digits = Long.toString(Math.abs(amount));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < digits.length(); i++) {
            if (i > 0 && (digits.length() - i) % 3 == 0) {
                sb.append('.');
            }
            sb.append(digits.charAt(i));
        }
        return (amount < 0 ? "-" : "") + sb + " VND";
    }
}