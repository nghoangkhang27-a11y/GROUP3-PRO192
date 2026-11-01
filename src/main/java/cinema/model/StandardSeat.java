package cinema.model;

public class StandardSeat extends Seat {
    public static final long price = 70000;

    public StandardSeat(char row, int number) {
        super(row, number);
    }

    @Override
    public long calculatePrice() {
        return price;
    }

    @Override
    public String getTypeName() {
        return "Standard";
    }
}