package cinema.model;

public abstract class Seat {
    private String seatId;
    private char row;
    private int number;

    public Seat(char row, int number) {
        this.row = row;
        this.number = number;
        this.seatId = this.row + this.number;
    }

    public abstract long calculatePrice();
    public abstract String getTypeName();

    public String getSeatId() {
        return this.seatId;
    }

    public char getRow() {
        return this.row;
    }

    public int getNumber() {
        return this.number;
    }
}