package cinema.model;

public abstract class Seat {
    private String seatId;
    private char row;
    private int number;

    public Seat(char row, int number) {
        this.row = row;
        this.number = number;
        this.seatId = "" + row + number;
    }

    public abstract long calculatePrice();
    public abstract String getTypeName();

    public String getSeatId() {
        return this.seatId;
    }

    public char getRow() {
        return row;
    }

    public int getNumber() {
        return this.number;
    }
}