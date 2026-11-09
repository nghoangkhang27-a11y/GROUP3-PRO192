package cinema.model;

public class StandardSeat extends Seat {
        private long price = 70000;

    public StandardSeat(char row, int number, long price) {
        super(row, number);
        this.price = price;
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